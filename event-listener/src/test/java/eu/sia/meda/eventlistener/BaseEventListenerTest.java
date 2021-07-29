package eu.sia.meda.eventlistener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.event.service.ErrorPublisherService;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import eu.sia.meda.util.ColoredPrinters;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import javax.management.*;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.util.*;

@Import({ArchEventListenerConfigurationService.class, PropertiesManager.class, KafkaAutoConfiguration.class})
@EmbeddedKafka(partitions = 1, count = 1, controlledShutdown = true)
@TestPropertySource(
        properties = {
                "logging.level.org.apache.zookeeper=WARN",
                "logging.level.org.apache.kafka=WARN",
                "logging.level.kafka=WARN",
                "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
                "spring.cloud.stream.kafka.binder.zkNodes=${spring.embedded.zookeeper.connect}",
                "meda.core.sessioncontext.enabled:false"
        })
public abstract class BaseEventListenerTest extends BaseSpringTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> template;

    @BeforeClass
    public static void configLevelLogs() {
        ((Logger) LoggerFactory.getLogger("org.apache.zookeeper")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("org.apache.kafka")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("kafka")).setLevel(Level.WARN);
    }

    @BeforeClass
    public static void unregisterPreviouslyKafkaServers() throws MalformedObjectNameException, MBeanRegistrationException, InstanceNotFoundException {
        ObjectName kafkaServerMbeanName = new ObjectName("kafka.server:type=app-info,id=0");
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        if(mBeanServer.isRegistered(kafkaServerMbeanName)){
            mBeanServer.unregisterMBean(kafkaServerMbeanName);
        }
    }

    @Autowired
    private EmbeddedKafkaBroker kafkaBroker;
    @Value( "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.cloud.stream.kafka.binder.zkNodes}")
    private String zkNodes;

    @SuppressWarnings("OptionalAssignedToNull")
    @Test
    public void test() throws JsonProcessingException, InterruptedException, UnsupportedEncodingException {
        ColoredPrinters.PRINT_CYAN.println(kafkaBroker.getKafkaServers().get(0).config().zkConnect());
        ColoredPrinters.PRINT_PURPLE.println(String.format("Bootstrap %s - ZooKeeper %s", bootstrapServers, zkNodes));
        ColoredPrinters.PRINT_GREEN.println("Waiting to allow kafka starting");
        Thread.sleep(2000);

        List<String> jsons=new LinkedList<>();
        Optional<Headers> defaultHeaders;
        if(getRequestHeadersList()==null || getRequestHeadersList().size()==1){
            defaultHeaders=Optional.ofNullable(getRequestHeadersList()==null?null:getRequestHeadersList().get(0));
        } else {
            defaultHeaders=null;
        }
        int i=0;
        List<ProducerRecord<String, String>> records = new LinkedList<>();
        for (Object o : getRequestObjects()) {
            String json = objectMapper.writeValueAsString(o);
            Headers headers;
            if(defaultHeaders!=null){
                headers=defaultHeaders.orElse(null);
            } else {
                headers=getRequestHeadersList().get(i);
            }
            records.add(new ProducerRecord<String,String>(getTopic(), null, null, json, headers));
            jsons.add(json);
            i++;
        }
        beforeSendData();
        records.parallelStream().forEach(template::send);

        beforeSleep();

        Thread.sleep(getSleepMillis());

        if(getGroupId()!=null){
            verifyCommittedOffsets(i);

            ColoredPrinters.PRINT_GREEN.println("Offset commit checked, verifying invocations");
        }

        verifyInvocations(jsons);

        ErrorPublisherService errorPublisherService = getErrorPublisherService();
        if(errorPublisherService != null){
            BDDMockito.verify(errorPublisherService, Mockito.never()).publishErrorEvent(Mockito.any(), Mockito.any(), Mockito.any());
        }
    }

    protected int getSleepMillis() {
        return 10000;
    }

    /** to override in order to perform some logic before sending data */
    protected void beforeSendData(){
    }

    /** to override in order to perform some logic before to wait consumes */
    protected void beforeSleep() {
    }

    private void verifyCommittedOffsets(int expectedPublishedMessages) {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(getGroupId(), "true", kafkaBroker);
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String,String> consumer = cf.createConsumer();
        int publishedMessages=0;
        for(int i=0; i<consumer.partitionsFor(getTopic()).size(); i++) {
            TopicPartition partition = new TopicPartition(getTopic(), i);
            long lastOffset = consumer.endOffsets(Collections.singletonList(partition)).values().iterator().next();
            Assert.assertEquals(String.format("Unexpected committed offsets on partition %s", partition),
                    lastOffset,
                    consumer.committed(partition).offset()
            );
            publishedMessages+=lastOffset;
        }
        Assert.assertEquals(expectedPublishedMessages, publishedMessages);
    }

    protected List<Headers> getRequestHeadersList(){
        return Collections.singletonList(getRequestHeaders());
    }

    protected Headers getRequestHeaders(){
        return null;
    }

    /** The objects to be serialized and sent */
    protected List<Object> getRequestObjects(){
        return Collections.singletonList(getRequestObject());
    }

    /** The object to be serialized and sent */
    protected abstract Object getRequestObject();

    /** The topic */
    protected abstract String getTopic();

    /** To override in order to perform check on committed offsets */
    protected String getGroupId() {
        return null;
    }

    protected void verifyInvocations(List<String> jsons) {
        jsons.forEach(this::verifyInvocation);
    }
    /** To check if the invocation occurred */
    protected abstract void verifyInvocation(String json);

    /** If not null, it will check if the {@link ErrorPublisherService#publishErrorEvent(byte[], org.apache.kafka.common.header.Headers, String)} has not been invoked */
    protected abstract ErrorPublisherService getErrorPublisherService();
}

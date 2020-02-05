package eu.sia.meda.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringIntegrationTest;
import eu.sia.meda.event.service.ErrorPublisherService;
import eu.sia.meda.util.ColoredPrinters;
import io.jsonwebtoken.lang.Collections;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

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
public abstract class BaseEventListenerIntegrationTest extends BaseSpringIntegrationTest {

    @Autowired
    private EmbeddedKafkaBroker kafkaBroker;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> template;

    @Value( "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.cloud.stream.kafka.binder.zkNodes}")
    private String zkNodes;

    @Test
    public void test() throws JsonProcessingException {
        ColoredPrinters.PRINT_CYAN.println(kafkaBroker.getKafkaServers().get(0).config().zkConnect());
        ColoredPrinters.PRINT_PURPLE.println(String.format("Bootstrap %s - ZooKeeper %s", bootstrapServers, zkNodes));

        ColoredPrinters.PRINT_GREEN.println("Configuring embedded Kafka...");
        kafkaBroker.addTopics(getTopicPublished());
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(getConsumerGroupId(), "true", kafkaBroker);
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String,String> consumer = cf.createConsumer();
        kafkaBroker.consumeFromAnEmbeddedTopic(consumer, getTopicPublished());

        ColoredPrinters.PRINT_GREEN.println("Publishing the request...");
        Object request = getRequestObject();
        String payload;
        if(request instanceof String){
            payload = (String)request;
        } else {
            payload = objectMapper.writeValueAsString(getRequestObject());
        }
        Iterable<Header> headers = null;
        Map<String, byte[]> headersMap = getHeaders();
        if(!Collections.isEmpty(headersMap)){
            headers = headersMap.entrySet().stream().map(e->new RecordHeader(e.getKey(), e.getValue())).collect(Collectors.toList());
        }
        ProducerRecord<String, String> record = new ProducerRecord<String,String>(getTopicSubscription(), null, null, payload, headers);
        template.send(record);

        ColoredPrinters.PRINT_GREEN.println("Waiting for a response...");
        consumer.seekToBeginning(consumer.assignment());
        ConsumerRecords<String,String> published = consumer.poll(getTimeout());

        ColoredPrinters.PRINT_GREEN.println("Checking results...");
        Assert.assertEquals(1, getExpectedPublishedMessagesCount(published));
        verifyPublishedMessages(published.iterator());

        ErrorPublisherService errorPublisherService = getErrorPublisherService();
        if(errorPublisherService != null){
            BDDMockito.verify(errorPublisherService, Mockito.never()).publishErrorEvent(Mockito.any(), Mockito.any(), Mockito.any());
        }
    }

    protected Map<String, byte[]> getHeaders() {
        return null;
    }

    protected abstract ErrorPublisherService getErrorPublisherService();

    /** The object to be serialized and sent */
    protected abstract Object getRequestObject();

    /** The topic */
    protected abstract String getTopicSubscription();
    /** The group id */
    protected String getConsumerGroupId(){
        return "groupId-" + getTopicPublished();
    }
    /** The topic */
    protected abstract String getTopicPublished();
    /** The {@link Duration} to wait for the result to be ready in the {@link #getTopicPublished()} */
    protected Duration getTimeout() {
        return Duration.ofMillis(5000);
    }
    /** The expected number of topic published */
    protected int getExpectedPublishedMessagesCount(ConsumerRecords<String,String> published) {
        return published.count();
    }
    /** To verify the content of the published message */
    protected abstract void verifyPublishedMessages(Iterator<? extends ConsumerRecord<String,String>> iterator);
}

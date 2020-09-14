package eu.sia.meda.event;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.event.configuration.ArchEventConfigurationService;
import eu.sia.meda.event.request.EventRequest;
import eu.sia.meda.event.transformer.IEventRequestTransformer;
import eu.sia.meda.event.transformer.IEventResponseTransformer;
import eu.sia.meda.event.transformer.SimpleEventRequestTransformer;
import eu.sia.meda.event.transformer.SimpleEventResponseTransformer;
import eu.sia.meda.util.ColoredPrinters;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

@Import({ArchEventConfigurationService.class, PropertiesManager.class, KafkaAutoConfiguration.class, SimpleEventRequestTransformer.class, SimpleEventResponseTransformer.class})
@EmbeddedKafka(partitions = 1, count = 1, controlledShutdown = true)
@TestPropertySource(
        properties = {
                "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
                "spring.cloud.stream.kafka.binder.zkNodes=${spring.embedded.zookeeper.connect}"
        })
public abstract class BaseEventConnectorTest<INPUT, OUTPUT, DTO, RESOURCE, CONNECTOR extends BaseEventConnector<INPUT, OUTPUT, DTO, RESOURCE>> extends BaseSpringTest {

    @BeforeClass
    public static void configLevelLogs() {
        ((Logger) LoggerFactory.getLogger("org.apache.zookeeper")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("org.apache.kafka")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("kafka")).setLevel(Level.WARN);
    }

    @Autowired
    private EmbeddedKafkaBroker kafkaBroker;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SimpleEventRequestTransformer<INPUT> simpleEventRequestTransformer;
    @Autowired
    private SimpleEventResponseTransformer simpleEventResponseTransformer;

    @Value( "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.cloud.stream.kafka.binder.zkNodes}")
    private String zkNodes;

    @Test
    public void test() throws InterruptedException {
        ColoredPrinters.PRINT_CYAN.println(kafkaBroker.getKafkaServers().get(0).config().zkConnect());
        ColoredPrinters.PRINT_PURPLE.println(String.format("Bootstrap %s - ZooKeeper %s", bootstrapServers, zkNodes));

        CONNECTOR eventConnector = getEventConnector();
        INPUT request = getRequestObject();
        IEventRequestTransformer<INPUT,DTO> requestTransformer = getRequestTransformer();
        IEventResponseTransformer<RESOURCE, OUTPUT> responseTransformer = getResponseTransformer();

        EventRequest<DTO> dto = requestTransformer.transform(request);
        Assert.assertNotNull(dto);
        checkDto(request, dto);

        kafkaBroker.addTopics(getTopic());

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(getGroupId(), "true", kafkaBroker);
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String,String> consumer = cf.createConsumer();
        kafkaBroker.consumeFromAnEmbeddedTopic(consumer, getTopic());

        OUTPUT result = invokeCall(eventConnector, request, requestTransformer, responseTransformer, null);
        consumer.seekToBeginning(consumer.assignment());
        ColoredPrinters.PRINT_GREEN.println("Start polling");
        ConsumerRecords<String,String> published = consumer.poll(Duration.ofMillis(10000));

        checkResult(result);
        Assert.assertNotNull(result);

        Assert.assertEquals(1, published.count());
        checkPublishedMessage(dto, published.iterator().next());

        consumer.close();
    }

    public OUTPUT invokeCall(CONNECTOR eventConnector, INPUT request, IEventRequestTransformer<INPUT, DTO> requestTransformer, IEventResponseTransformer<RESOURCE, OUTPUT> responsetransformer, String... args){
        return eventConnector.call(request, requestTransformer, responsetransformer, args);
    }

    protected void checkPublishedMessage(EventRequest<DTO> dto, ConsumerRecord<String, String> published) {
        Assert.assertEquals(new String(dto.getPayload(), StandardCharsets.UTF_8), published.value());
    }

    /** The connector used to publish the event */
    protected abstract CONNECTOR getEventConnector();

    /** The object to be serialized and sent */
    protected abstract INPUT getRequestObject();

    /** The request transformer */
    protected IEventRequestTransformer<INPUT,DTO> getRequestTransformer(){
        return (IEventRequestTransformer<INPUT,DTO>)simpleEventRequestTransformer;
    }

    /** The response transfomer */
    protected IEventResponseTransformer<RESOURCE, OUTPUT> getResponseTransformer(){
        return (IEventResponseTransformer<RESOURCE, OUTPUT>)simpleEventResponseTransformer;
    }

    /** To check if the DTO is correctly generated */
    @SneakyThrows
    protected void checkDto(INPUT request, EventRequest<DTO> dto) {
        if(getRequestTransformer() instanceof SimpleEventRequestTransformer){
            String expected;
            if(request instanceof byte[]){
                expected = new String((byte[])request, StandardCharsets.UTF_8);
            } else {
                expected = objectMapper.writeValueAsString(request);
            }
            Assert.assertEquals(expected, new String(dto.getPayload(), StandardCharsets.UTF_8));
        }
    }
    /** To check the result */
    protected void checkResult(OUTPUT result) {
        if(result instanceof Boolean) {
            Assert.assertEquals(Boolean.TRUE, result);
        }
    }
    /** The group id */
    protected String getGroupId(){
        return "groupId";
    }
    /** The topic */
    protected abstract String getTopic();
}

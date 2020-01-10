package eu.sia.meda.eventlistener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringIntegrationTest;
import eu.sia.meda.util.ColoredPrinters;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

@EmbeddedKafka
@TestPropertySource(
        properties = {
                "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
                "spring.cloud.stream.kafka.binder.zkNodes=${spring.embedded.zookeeper.connect}",
                "meda.core.sessioncontext.enabled:false"
        })
public abstract class BaseEventListenerIntegrationTest extends BaseSpringIntegrationTest {

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
    private KafkaTemplate<String, String> template;

    @Test
    public void test() throws JsonProcessingException, InterruptedException {
        ColoredPrinters.PRINT_GREEN.println("Configuring embedded Kafka...");
        kafkaBroker.addTopics(getTopicPublished());
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(getConsumerGroupId(), "true", kafkaBroker);
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String,String> consumer = cf.createConsumer();
        kafkaBroker.consumeFromAnEmbeddedTopic(consumer, getTopicPublished());

        ColoredPrinters.PRINT_GREEN.println("Publishing the request...");
        String json = objectMapper.writeValueAsString(getRequestObject());
        template.send(getTopicSubscription(), json);

        ColoredPrinters.PRINT_GREEN.println("Waiting for a response...");
        ConsumerRecords<String,String> published = consumer.poll(getTimeout());

        ColoredPrinters.PRINT_GREEN.println("Checking results...");
        Assert.assertEquals(1, getExpectedPublishedMessagesCount(published));
        verifyPublishedMessages(published.iterator());
    }

    /** The object to be serialized and sent */
    protected abstract Object getRequestObject();

    /** The topic */
    protected abstract String getTopicSubscription();
    /** The group id */
    protected String getConsumerGroupId(){
        return "groupId";
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

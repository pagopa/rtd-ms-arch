package eu.sia.meda.eventlistener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import eu.sia.meda.service.SessionContextRetriever;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import java.io.UnsupportedEncodingException;

@Import({ArchEventListenerConfigurationService.class, PropertiesManager.class, KafkaAutoConfiguration.class})
@EmbeddedKafka
@TestPropertySource(
        properties = {
                "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
                "spring.cloud.stream.kafka.binder.zkNodes=${spring.embedded.zookeeper.connect}",
                "meda.core.sessioncontext.enabled:false"
        })
public abstract class BaseEventListenerTest extends BaseSpringTest {

    @MockBean
    private SessionContextRetriever sessionContextRetriever;

    @BeforeClass
    public static void configLevelLogs() {
        ((Logger) LoggerFactory.getLogger("org.apache.zookeeper")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("org.apache.kafka")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("kafka")).setLevel(Level.WARN);
    }


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> template;

    @Test
    public void test() throws JsonProcessingException, InterruptedException, UnsupportedEncodingException {
        String json = objectMapper.writeValueAsString(getRequestObject());
        template.send(getTopic(), json);

        Thread.sleep(5000);

        verifyInvocation(json);
    }

    /** The object to be serialized and sent */
    protected abstract Object getRequestObject();
    /** The topic */
    protected abstract String getTopic();
    /** To check if the invocation occurred */
    protected abstract void verifyInvocation(String json);
}

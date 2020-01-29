package eu.sia.meda.eventlistener;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.event.service.ErrorPublisherService;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import eu.sia.meda.service.SessionContextRetriever;
import eu.sia.meda.util.ColoredPrinters;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import javax.management.*;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;

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

    @MockBean
    private SessionContextRetriever sessionContextRetriever;

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

    @Test
    public void test() throws JsonProcessingException, InterruptedException, UnsupportedEncodingException {
        ColoredPrinters.PRINT_CYAN.println(kafkaBroker.getKafkaServers().get(0).config().zkConnect());
        ColoredPrinters.PRINT_PURPLE.println(String.format("%s - %s", bootstrapServers, zkNodes));
        ColoredPrinters.PRINT_GREEN.println("Waiting to allow kafka starting");
        Thread.sleep(2000);

        String json = objectMapper.writeValueAsString(getRequestObject());
        template.send(getTopic(), json);

        Thread.sleep(10000);

        verifyInvocation(json);

        ErrorPublisherService errorPublisherService = getErrorPublisherService();
        if(errorPublisherService != null){
            BDDMockito.verify(errorPublisherService, Mockito.never()).publishErrorEvent(Mockito.any(), Mockito.any(), Mockito.any());
        }
    }

    /** The object to be serialized and sent */
    protected abstract Object getRequestObject();

    /** The topic */
    protected abstract String getTopic();
    /** To check if the invocation occurred */
    protected abstract void verifyInvocation(String json);

    /** If not null, it will check if the {@link ErrorPublisherService#publishErrorEvent(byte[], org.apache.kafka.common.header.Headers, String)} has not been invoked */
    protected abstract ErrorPublisherService getErrorPublisherService();
}

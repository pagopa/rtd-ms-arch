package eu.sia.meda.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jmx.mbeanserver.JmxMBeanServer;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import eu.sia.meda.service.SessionContextRetriever;
import org.apache.kafka.common.utils.Sanitizer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
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
    public static void unregisterPreviouslyKafkaServers() throws MalformedObjectNameException, MBeanRegistrationException {
        try {
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(new ObjectName("kafka.server:type=app-info,id=0"));
        } catch (InstanceNotFoundException e) {
            //Nothing to do
        }
    }

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

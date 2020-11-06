package eu.sia.meda.eventlistener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.event.BaseEventConnector;
import eu.sia.meda.event.configuration.ArchEventConfigurationService;
import eu.sia.meda.event.transformer.IEventRequestTransformer;
import eu.sia.meda.event.transformer.SimpleEventRequestTransformer;
import eu.sia.meda.event.transformer.SimpleEventResponseTransformer;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import eu.sia.meda.util.ColoredPrinters;
import eu.sia.meda.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;
import wiremock.org.eclipse.jetty.util.ConcurrentHashSet;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This test has been designed to be executed alone (it makes use of static variables, that should not be written by more than one test at once
 * Remember to set the following mandatory Environment variables:
 * <ol>
 *     <li>KAFKA_SERVERS</li>
 *     <li>KAFKA_REQUEST_TOPIC</li>
 *     <li>KAFKA_RESPONSE_TOPIC</li>
 * </ol>
 *
 * You can personalize N, maxRetry and maxParallelPublish in @PostConstruct method of your tests
 * */
@TestPropertySource(
        properties = {
        "event-test-deploy=true",
//<editor-fold desc="Input event configuration">
//<editor-fold desc="topic configuration">
        "connectors.eventConfigurations.items.KafkaRequestConnector.topic: ${KAFKA_REQUEST_TOPIC:Loyalty_CARDAuthorizationEvents_TO_MaCE}",
        "connectors.eventConfigurations.items.KafkaRequestConnector.sasl.jaas.config:${KAFKA_REQUEST_SASL_JAAS_CONFIG:}",
//</editor-fold>

//<editor-fold desc="server configuration">
        "connectors.eventConfigurations.items.KafkaRequestConnector.bootstrapServers: ${KAFKA_SERVERS:}",
        "connectors.eventConfigurations.items.KafkaRequestConnector.security.protocol:${KAFKA_SECURITY_PROTOCOL:}",
        "connectors.eventConfigurations.items.KafkaRequestConnector.sasl.mechanism:${KAFKA_SASL_MECHANISM:}",
//</editor-fold">

        "connectors.eventConfigurations.items.KafkaRequestConnector.sasl.kerberos.service.name:${KAFKA_SASL_KERBEROS_SERVICE_NAME:}",
        "connectors.eventConfigurations.items.KafkaRequestConnector.enable.auto.commit: true",
        "connectors.eventConfigurations.items.KafkaRequestConnector.ackOnError: true",
        "connectors.eventConfigurations.items.KafkaRequestConnector.maxFailures: 1",
        "connectors.eventConfigurations.items.KafkaRequestConnector.retry.backoff.ms: ${KAFKA_RETRY_MS:10000}",
        "connectors.eventConfigurations.items.KafkaRequestConnector.retries: ${KAFKA_RETRIES:1}",
        "connectors.eventConfigurations.items.KafkaRequestConnector.session.timeout.ms: ${KAFKA_SESSION_TIMEOUT:30000}",
        "connectors.eventConfigurations.items.KafkaRequestConnector.connections.max.idle.ms: ${KAFKA_CONNECTION_MAX_IDLE_TIME:180000}",
//</editor-fold>

//<editor-fold desc="Output event configuration">
//<editor-fold desc="topic configuration">
        "listeners.eventConfigurations.items.KafkaResponseListener.groupId: ${KAFKA_RESPONSE_GROUP_ID:}",
        "listeners.eventConfigurations.items.KafkaResponseListener.topic: ${KAFKA_RESPONSE_TOPIC:}",
        "listeners.eventConfigurations.items.KafkaResponseListener.sasl.jaas.config:${KAFKA_RESPONSE_SASL_JAAS_CONFIG:}",
//</editor-fold>

//<editor-fold desc="server configuration">
        "listeners.eventConfigurations.items.KafkaResponseListener.bootstrapServers: ${KAFKA_SERVERS:}",
        "listeners.eventConfigurations.items.KafkaResponseListener.security.protocol:${KAFKA_SECURITY_PROTOCOL:}",
        "listeners.eventConfigurations.items.KafkaResponseListener.sasl.mechanism:${KAFKA_SASL_MECHANISM:}",
//</editor-fold>

        "listeners.eventConfigurations.items.KafkaResponseListener.sasl.kerberos.service.name:${KAFKA_SASL_KERBEROS_SERVICE_NAME:}",
        "listeners.eventConfigurations.items.KafkaResponseListener.concurrency: ${KAFKA_RESPONSE_CONCURRENCY:2}",
        "listeners.eventConfigurations.items.KafkaResponseListener.enable.auto.commit: true",
        "listeners.eventConfigurations.items.KafkaResponseListener.ackOnError: true",
        "listeners.eventConfigurations.items.KafkaResponseListener.maxFailures: 1",
        "listeners.eventConfigurations.items.KafkaResponseListener.max.poll.interval.ms: ${KAFKA_POLL_INTERVAL:60000}",
        "listeners.eventConfigurations.items.KafkaResponseListener.session.timeout.ms: ${KAFKA_SESSION_TIMEOUT:30000}",
        "listeners.eventConfigurations.items.KafkaResponseListener.request.timeout.ms: ${KAFKA_REQUEST_TIMEOUT:60000}",
        "listeners.eventConfigurations.items.KafkaResponseListener.connections.max.idle.ms: ${KAFKA_CONNECTION_MAX_IDLE_TIME:180000}",
//</editor-fold>

        "meda.core.sessioncontext.enabled:false"
})
@Import({ArchEventListenerConfigurationService.class, PropertiesManager.class, KafkaAutoConfiguration.class
        , ArchEventConfigurationService.class, PropertiesManager.class, KafkaAutoConfiguration.class, SimpleEventRequestTransformer.class, SimpleEventResponseTransformer.class
        , BaseEventListenerTestDeploy.KafkaRequestConnector.class, BaseEventListenerTestDeploy.KafkaResponseListener.class})
public abstract class BaseEventListenerTestDeploy<REQUEST, DTO, RESPONSE> extends BaseSpringTest {
    protected static int N = 10;
    protected static int maxRetry = 6;
    protected static int maxParallelPublish = 1;

    static {
        setEnvIfNotExists("KAFKA_RESPONSE_GROUP_ID", "BaseEventListenerTestDeploy_ResponseGroup");
    }

    public static void setEnvIfNotExists(String key, String value){
        if(Strings.isNullOrEmpty(System.getProperty(key))){
            System.setProperty(key, value);
        }
    }

    @Autowired
    protected KafkaRequestConnector<REQUEST, DTO> publisher;

    protected IEventRequestTransformer<REQUEST, DTO> requestTransformer;
    @Autowired
    protected SimpleEventResponseTransformer responseTransformer;

    protected static Class<?> responseClass;

    protected static Function<Object,Object> response2IdFunction;

    protected final HashSet<Object> idsSent = new HashSet<>(N);

    @PostConstruct
    public void init(){
        requestTransformer = getRequestTransformer();
        responseClass = getResponseClass();
        //noinspection unchecked
        response2IdFunction = (Function<Object,Object>)getResponse2IdFunction();
    }

    protected abstract IEventRequestTransformer<REQUEST, DTO> getRequestTransformer();

    protected abstract REQUEST buildRequest(int i);

    protected abstract Object getRequestId(REQUEST request);

    protected abstract Function<RESPONSE,Object> getResponse2IdFunction();

    protected Class<RESPONSE> getResponseClass() {
        //noinspection unchecked
        return (Class<RESPONSE>) ReflectionUtils.getGenericTypeClass(getClass(), 2);
    }

    @Test
    public void test() throws InterruptedException {
        final Consumer<REQUEST> publisherInvoke = request -> publisher.call(request, requestTransformer, responseTransformer);
        Consumer<REQUEST> publisherFunc;
        ExecutorService executor;

        if (maxParallelPublish > 1) {
            executor= Executors.newFixedThreadPool(maxParallelPublish);
            publisherFunc = request -> executor.submit(()->publisherInvoke.accept(request));
        } else {
            executor = null;
            publisherFunc = publisherInvoke;
        }

        try {
            Instant before = Instant.now();
            for (int i = 0; i < N; i++) {
                REQUEST request = buildRequest(i);

                publisherFunc.accept(request);
                ColoredPrinters.PRINT_BLUE.println(String.format("[%s] Sending %s event having id %s", LocalTime.now(), i, getRequestId(request)));

                idsSent.add(getRequestId(request));
            }

            Instant after = Instant.now();
            ColoredPrinters.PRINT_CYAN.println(String.format("Sent in seconds: %d", Duration.between(before, after).toMillis() / 100));

            int retry = 0;
            int previousRead = 0;
            while (idsSent.size() != idsRead.size() && retry < maxRetry) {
                Thread.sleep(5000);
                if(previousRead==idsRead.size()){
                    retry++;
                } else {
                    previousRead=idsRead.size();
                }
            }

            ColoredPrinters.PRINT_BLUE.println("Checking");
            Assert.assertEquals(idsSent, idsRead);
        } finally {
            if(executor!=null){
                executor.shutdown();
            }
        }
    }

    @ConditionalOnProperty(value = "event-test-deploy", havingValue = "true")
    @Service
    protected static class KafkaRequestConnector<REQUEST,DTO> extends BaseEventConnector<REQUEST, Boolean, DTO, Void> {}

    private static final AtomicInteger responseAnswer = new AtomicInteger(0);
    private static final Set<Object> idsRead = new ConcurrentHashSet<>();

    @ConditionalOnProperty(value = "event-test-deploy", havingValue = "true")
    @Service
    @Slf4j
    protected static class KafkaResponseListener extends BaseEventListener {
        @Autowired
        private ObjectMapper objectMapper;

        @Override
        public void onReceived(byte[] payload, Headers headers) {
            Object id="Cannot be read";
            try{
                id = response2IdFunction.apply(objectMapper.readValue(new String(payload, StandardCharsets.UTF_8), responseClass));
                idsRead.add(id);
            } catch (Exception e){
                log.error(String.format("Something gone wrong reading the payload as %s", responseClass), e);
            }
            ColoredPrinters.PRINT_PURPLE.println(String.format("[%s] Deployed application read %d having id %s", LocalTime.now(), responseAnswer.getAndIncrement(), id));
        }
    }
}


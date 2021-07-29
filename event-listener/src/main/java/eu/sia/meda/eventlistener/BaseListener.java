package eu.sia.meda.eventlistener;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import eu.sia.meda.eventlistener.utils.MedaRecordHeaders;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.backoff.FixedBackOff;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The listener interface for receiving baseEvent events.
 * The class that is interested in processing a baseEvent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addBaseEventListener<code> method. When
 * the baseEvent event occurs, that object's appropriate
 * method is invoked.
 */
@Getter
public abstract class BaseListener {

    /**
     * The logger.
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The bootstrap servers.
     */
    private String bootstrapServers;

    /**
     * The group id.
     */
    private String groupId;

    /**
     * The topic.
     */
    private String topic;

    /**
     * The security protocol.
     */
    private String securityProtocol;

    /**
     * The sasl service name.
     */
    private String saslServiceName;

    /**
     * The sasl jaas conf.
     */
    private String saslJaasConf;

    /**
     * The sasl mechanism.
     */
    private String saslMechanism;

    /**
     * The max poll records.
     */
    private Integer maxPollRecords;

    /**
     * The max poll interval ms.
     */
    private Integer maxPollIntervalMs;

    /**
     * The enable auto commit.
     */
    private Boolean enableAutoCommit;

    /**
     * The auto commit interval ms.
     */
    private Integer autoCommitIntervalMs;

    /**
     * The auto offset reset.
     */
    private String autoOffsetReset;

    /**
     * The fetch min bytes.
     */
    private Integer fetchMinBytes;

    /**
     * The fetch max bytes.
     */
    private Integer fetchMaxBytes;

    /**
     * The fetch max wait ms.
     */
    private Integer fetchMaxWaitMs;

    /**
     * The metadata max age ms.
     */
    private Long metadataMaxAgeMs;

    /**
     * The max partition fetch bytes.
     */
    private Integer maxPartitionFetchBytes;

    /**
     * The client id.
     */
    private String clientId;

    /**
     * The reconnect backoff ms.
     */
    private Long reconnectBackoffMs;

    /**
     * The reconnect backoff max ms.
     */
    private Long reconnectBackoffMaxMs;

    /**
     * The retry backoff ms.
     */
    private Long retryBackoffMs;

    /**
     * The check crcs.
     */
    private Boolean checkCrcs;

    /**
     * The connections max idle ms.
     */
    private Long connectionsMaxIdleMs;

    /**
     * The request timeout ms.
     */
    private Integer requestTimeoutMs;

    /**
     * The exclude internal topics.
     */
    private Boolean excludeInternalTopics;

    /**
     * The isolation level.
     */
    private String isolationLevel;

    /**
     * The ack on error.
     */
    private Boolean ackOnError;

    /**
     * The max failures.
     */
    private Integer maxFailures;

    /**
     * The concurrency.
     */
    private Integer concurrency;

    /**
     * The session timeout ms.
     */
    private Integer sessionTimeoutMs;

    /**
     * The ackMode.
     */
    private String ackMode;

    /**
     * The ackCount.
     */
    private Integer ackCount;

    /**
     * The ackTime (ms).
     */
    private Integer ackTime;

    /**
     * The configuration.
     */
    @Autowired
    @Qualifier("EventListenerConfiguration")
    private ArchEventListenerConfigurationService configuration;

    /**
     * The load session context.
     */
    @Value("${meda.core.sessioncontext.enabled:true}")
    private boolean loadSessionContext;

    private static final String SINGLE_POOL = "singlePool";
    private boolean singlePool;
    private int consumerThreadNumber;
    protected boolean asyncExecution;

    private final Map<String, ExecutorService> executorServicesMap = new ConcurrentHashMap<>();

    protected final Consumer<ConsumerRecord<String, byte[]>> defaultMessageConsumer = r -> this.onReceived(r.value(), r.headers());

    protected ConcurrentMessageListenerContainer concurrentMessageListenerContainer;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        ArchEventListenerConfigurationService.EventListenerConfiguration eventConfiguration = this.configuration.retrieveEventConfiguration(this.getClass().getSimpleName());
        if (eventConfiguration == null) {
            throw new ExceptionInInitializerError();
        } else if (eventConfiguration.getTopic() == null) {
            throw new ExceptionInInitializerError();
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for connector: {}"), this.getClass().getSimpleName());
            }
            this.configure(eventConfiguration);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LoggerUtils.formatArchRow("Configured to listen on topic '{}' of the bootstrap server '{}'"), this.topic, this.bootstrapServers);
            }
        }
    }

    /**
     * Configure.
     *
     * @param eventConfiguration the event configuration
     */
    protected void configure(ArchEventListenerConfigurationService.EventListenerConfiguration eventConfiguration) {
        this.bootstrapServers = eventConfiguration.getBootstrapServers();
        this.groupId = eventConfiguration.getGroupId();
        this.topic = eventConfiguration.getTopic();
        this.securityProtocol = eventConfiguration.getSecurityProtocol();
        this.saslServiceName = eventConfiguration.getSaslServiceName();
        this.saslJaasConf = eventConfiguration.getSaslJaasConf();
        this.saslMechanism = eventConfiguration.getSaslMechanism();
        this.maxPollRecords = eventConfiguration.getMaxPollRecords();
        this.maxPollIntervalMs = eventConfiguration.getMaxPollIntervalMs();
        this.enableAutoCommit = eventConfiguration.getEnableAutoCommit();
        this.autoCommitIntervalMs = eventConfiguration.getAutoCommitIntervalMs();
        this.autoOffsetReset = eventConfiguration.getAutoOffsetReset();
        this.fetchMinBytes = eventConfiguration.getFetchMinBytes();
        this.fetchMaxBytes = eventConfiguration.getFetchMaxBytes();
        this.fetchMaxWaitMs = eventConfiguration.getFetchMaxWaitMs();
        this.metadataMaxAgeMs = eventConfiguration.getMetadataMaxAgeMs();
        this.maxPartitionFetchBytes = eventConfiguration.getMaxPartitionFetchBytes();
        this.clientId = eventConfiguration.getClientId();
        this.reconnectBackoffMs = eventConfiguration.getReconnectBackoffMs();
        this.reconnectBackoffMaxMs = eventConfiguration.getReconnectBackoffMaxMs();
        this.retryBackoffMs = eventConfiguration.getRetryBackoffMs();
        this.checkCrcs = eventConfiguration.getCheckCrcs();
        this.connectionsMaxIdleMs = eventConfiguration.getConnectionsMaxIdleMs();
        this.requestTimeoutMs = eventConfiguration.getRequestTimeoutMs();
        this.excludeInternalTopics = eventConfiguration.getExcludeInternalTopics();
        this.isolationLevel = eventConfiguration.getIsolationLevel();
        this.ackOnError = eventConfiguration.getAckOnError();
        this.maxFailures = eventConfiguration.getMaxFailures();
        this.concurrency = eventConfiguration.getConcurrency();
        this.sessionTimeoutMs = eventConfiguration.getSessionTimeoutMs();
        this.ackMode = eventConfiguration.getAckMode();
        this.ackCount = eventConfiguration.getAckCount();
        this.ackTime = eventConfiguration.getAckTime();
        this.concurrentMessageListenerContainer = this.getListenerContainer();

        this.singlePool = eventConfiguration.isSinglePool();

        if (singlePool) {
            this.consumerThreadNumber = eventConfiguration.getPoolSize();
            executorServicesMap.put(SINGLE_POOL, configExecutor(SINGLE_POOL));
        } else {
            this.consumerThreadNumber = (int) Math.ceil((double) eventConfiguration.getPoolSize() / eventConfiguration.getConcurrency());
        }

        asyncExecution = consumerThreadNumber > 1;

        try {
            this.concurrentMessageListenerContainer.start();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LoggerUtils.formatArchRow("Starting a listener on topic={} with groupId={} with consumer={}"), this.topic, this.groupId, this.getClass().getSimpleName());
            }
        } catch (KafkaException ke) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error(LoggerUtils.formatArchRow("Error initializing listener: {}"), ke);
            }
        } catch (Exception e) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error(LoggerUtils.formatArchRow("Listener initialization exception: {}"), e);
            }
        }
    }

    /**
     * Gets the listener container.
     *
     * @return the listener container
     */
    private ConcurrentMessageListenerContainer getListenerContainer() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put("bootstrap.servers", this.bootstrapServers);
        configProps.put("group.id", this.groupId);
        configProps.put("key.deserializer", StringDeserializer.class);
        configProps.put("value.deserializer", ByteArrayDeserializer.class);
        if (Strings.isNotEmpty(this.securityProtocol)) {
            configProps.put("security.protocol", this.securityProtocol);
        }

        if (Strings.isNotEmpty(this.saslServiceName)) {
            configProps.put("sasl.kerberos.service.name", this.saslServiceName);
        }

        if (Strings.isNotEmpty(this.saslJaasConf)) {
            configProps.put("sasl.jaas.config", this.saslJaasConf);
        }

        if (Strings.isNotBlank(this.saslMechanism)) {
            configProps.put("sasl.mechanism", this.saslMechanism);
        }

        if (this.maxPollRecords != null) {
            configProps.put("max.poll.records", this.maxPollRecords);
        }

        if (this.maxPollIntervalMs != null) {
            configProps.put("max.poll.interval.ms", this.maxPollIntervalMs);
        }

        if (this.enableAutoCommit != null) {
            configProps.put("enable.auto.commit", this.enableAutoCommit);
        }

        if (this.autoCommitIntervalMs != null) {
            configProps.put("auto.commit.interval.ms", this.autoCommitIntervalMs);
        }

        if (this.autoOffsetReset != null) {
            configProps.put("auto.offset.reset", this.autoOffsetReset);
        }

        if (this.fetchMinBytes != null) {
            configProps.put("fetch.min.bytes", this.fetchMinBytes);
        }

        if (this.fetchMaxBytes != null) {
            configProps.put("fetch.max.bytes", this.fetchMaxBytes);
        }

        if (this.fetchMaxWaitMs != null) {
            configProps.put("fetch.max.wait.ms", this.fetchMaxWaitMs);
        }

        if (this.metadataMaxAgeMs != null) {
            configProps.put("metadata.max.age.ms", this.metadataMaxAgeMs);
        }

        if (this.maxPartitionFetchBytes != null) {
            configProps.put("max.partition.fetch.bytes", this.maxPartitionFetchBytes);
        }

        if (this.clientId != null) {
            configProps.put("client.id", this.clientId);
        }

        if (this.reconnectBackoffMs != null) {
            configProps.put("reconnect.backoff.ms", this.reconnectBackoffMs);
        }

        if (this.reconnectBackoffMaxMs != null) {
            configProps.put("reconnect.backoff.max.ms", this.reconnectBackoffMaxMs);
        }

        if (this.retryBackoffMs != null) {
            configProps.put("retry.backoff.ms", this.retryBackoffMs);
        }

        if (this.checkCrcs != null) {
            configProps.put("check.crcs", this.checkCrcs);
        }

        if (this.connectionsMaxIdleMs != null) {
            configProps.put("connections.max.idle.ms", this.connectionsMaxIdleMs);
        }

        if (this.requestTimeoutMs != null) {
            configProps.put("request.timeout.ms", this.requestTimeoutMs);
        }

        if (this.excludeInternalTopics != null) {
            configProps.put("exclude.internal.topics", this.excludeInternalTopics);
        }

        if (this.isolationLevel != null) {
            configProps.put("isolation.level", this.isolationLevel);
        }

        if (this.sessionTimeoutMs != null) {
            configProps.put("session.timeout.ms", this.sessionTimeoutMs);
        }

        DefaultKafkaConsumerFactory consumerFactory = new DefaultKafkaConsumerFactory<>(configProps);
        ContainerProperties containerProperties = new ContainerProperties(this.topic);
        containerProperties.setMessageListener(this);
        if (Boolean.FALSE.equals(this.enableAutoCommit)) {
            //default
            containerProperties.setAckMode(AckMode.MANUAL_IMMEDIATE);
            try {
                if (StringUtils.isNotBlank(this.ackMode)) {
                    containerProperties.setAckMode(AckMode.valueOf(this.ackMode));
                    if (this.ackCount > 0) {
                        containerProperties.setAckCount(this.ackCount);
                    }
                    if (this.ackTime > 0) {
                        containerProperties.setAckTime(this.ackTime);
                    }
                }
            } catch (Exception e) {
                logger.info("ackMode value no allowed, set default to MANUAL_IMMEDIATE", e);
                //NO SONAR//
            }

        } else if (Boolean.FALSE.equals(this.ackOnError)) {
            if(!(this instanceof BatchMessageListener)){
                containerProperties.setAckMode(AckMode.RECORD);
            }
        }

        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        if (this.concurrency != null) {
            container.setConcurrency(this.concurrency);
        }

        if (Boolean.FALSE.equals(this.enableAutoCommit) || Boolean.FALSE.equals(this.ackOnError)) {
            if (this instanceof MessageListener) {
                container.setErrorHandler(new SeekToCurrentErrorHandler((r, t) -> {
                    this.recover(r, t);
                }, new FixedBackOff(0, this.maxFailures == null ? 1 : this.maxFailures)));
            } else if (this instanceof BatchMessageListener) {
                container.setBatchErrorHandler(getBatchErrorHandler());
            }
        }

        return container;
    }

    protected SeekToCurrentBatchErrorHandler getBatchErrorHandler() {
        return new SeekToCurrentBatchErrorHandler();
    }

    /**
     * MultiThreaded consumer feature: to configure the executor for the input thread
     */
    private ExecutorService configExecutor(String threadName) {
        return Executors.newFixedThreadPool(consumerThreadNumber, buildThreadFactory(threadName));
    }

    /**
     * MultiThreaded consumer feature: to configure the thread factory
     */
    private ThreadFactory buildThreadFactory(String threadName) {
        return new CustomizableThreadFactory(String.format("%s-pool-", threadName));
    }

    /**
     * MultiThreaded consumer feature: to get the executor config of the current thread
     */
    protected ExecutorService getExecutorConfig() {
        if (singlePool) {
            return executorServicesMap.get(SINGLE_POOL);
        } else {
            return executorServicesMap.computeIfAbsent(getConsumerId(), this::configExecutor);
        }
    }

    private String getConsumerId() {
        return String.format("%s-%s", Thread.currentThread().getName(), getGroupId());
    }

    protected void unregisterAndShutdownCurrentThreadPool(){
        ExecutorService executorService = executorServicesMap.remove(getConsumerId());
        if(executorService!=null){
            executorService.shutdown();
        }
    }

    @PreDestroy
    public void destroy() {
        executorServicesMap.values().forEach(ExecutorService::shutdown);
    }

    /**
     * Recover.
     *
     * @param r the r
     * @param t the t
     */
    protected void recover(ConsumerRecord<?, ?> r, Exception t) {
        if (this.logger.isErrorEnabled()) {
            this.logger.error("Max failures (" + (this.maxFailures == null ? 1 : this.maxFailures) + ") reached for: " + r, t);
        }
    }

    /**
     * Pre received.
     *
     * @param record the record
     */
    void preReceived(ConsumerRecord<String, byte[]> record) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LoggerUtils.formatArchRow("Executing preEventReceived"));
        }
        EventContextHolder.setRecord(record);
        BaseContextHolder.setApplicationContext(this.loadApplicationContext(record));
    }

    /**
     * Post received.
     *
     * @param record the record
     */
    void postReceived(ConsumerRecord<String, byte[]> record) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LoggerUtils.formatArchRow("Executing postEventReceived"));
        }
    }

    public Future<?> onMessage(ConsumerRecord<String, byte[]> record, Consumer<ConsumerRecord<String, byte[]>> messageConsumer, Consumer<ConsumerRecord<String, byte[]>> syncToRecordcallback, Consumer<ConsumerRecord<String, byte[]>> syncToConsumerCallback) {
        return onMessage(record, messageConsumer, syncToRecordcallback, syncToConsumerCallback, null);
    }

    public Future<?> onMessage(ConsumerRecord<String, byte[]> record, Consumer<ConsumerRecord<String, byte[]>> messageConsumer, Consumer<ConsumerRecord<String, byte[]>> syncToRecordcallback, Consumer<ConsumerRecord<String, byte[]>> syncToConsumerCallback, Supplier<Boolean> abortingPollCheck) {
        try {
            if (asyncExecution) {
                ExecutorService executor = getExecutorConfig();
                ApplicationContext applicationContext = EventContextHolder.getApplicationContext();
                BaseContext<?, ?> sessionContext = EventContextHolder.getSessionContext();
                Map<String, String> contextMap = MDC.getCopyOfContextMap();
                return executor.submit(() -> {
                    try {
                        if(abortingPollCheck!=null && abortingPollCheck.get()){
                            return null;
                        }
                        EventContextHolder.clear();
                        BaseContextHolder.forceSetApplicationContext(applicationContext);
                        if (sessionContext != null) {
                            BaseContextHolder.forceSetSessionContext(sessionContext);
                        }
                        EventContextHolder.setRecord(record);
                        MDC.setContextMap(contextMap);
                        messageConsumer.accept(record);
                        if(syncToRecordcallback != null){
                            syncToRecordcallback.accept(record);
                        }
                        if (syncToConsumerCallback != null) {
                            return (Runnable)()->syncToConsumerCallback.accept(record);
                        } else {
                            return null;
                        }
                    } catch (Throwable e) {
                        logger.error(String.format("Something gone wrong handling record: topic:%s, partition:%d, offset:%d", record.topic(), record.partition(), record.offset()), e);
                        return null;
                    } finally {
                        EventContextHolder.clear();
                    }
                });
            } else {
                messageConsumer.accept(record);
                if (syncToRecordcallback != null) {
                    syncToRecordcallback.accept(record);
                }
                if (syncToConsumerCallback != null) {
                    syncToConsumerCallback.accept(record);
                }
                return null;
            }
        } finally {
            EventContextHolder.clear();
        }
    }

    /**
     * On received.
     *
     * @param payload the payload
     * @param headers the headers
     */
    public abstract void onReceived(byte[] payload, Headers headers);

    /**
     * Load applciation context.
     *
     * @param record the record
     * @return the application context
     */
    private ApplicationContext loadApplicationContext(ConsumerRecord<String, byte[]> record) {
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.setRequestId(MedaRecordHeaders.getRequestId(record));
        applicationContext.setTransactionId(MedaRecordHeaders.getTransactionId(record));
        applicationContext.setOriginApp(MedaRecordHeaders.getOriginApp(record));
        applicationContext.setUserId(MedaRecordHeaders.getUserId(record));

        applicationContext.buildDefaultCopyHeader();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LoggerUtils.formatArchRow("Populating MDC"));
        }
        MDC.put("request-id", applicationContext.getRequestId());
        MDC.put("apim-request-id", applicationContext.getApimRequestId());
        MDC.put("user-id", applicationContext.getUserId());

        return applicationContext;
    }
}

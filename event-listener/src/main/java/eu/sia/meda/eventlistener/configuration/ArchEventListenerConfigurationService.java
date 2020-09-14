package eu.sia.meda.eventlistener.configuration;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.properties.PropertiesManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ArchEventListenerConfigurationService.
 */
@Service("EventListenerConfiguration")
public class ArchEventListenerConfigurationService {

    /**
     * The Constant CONNECTOR_TYPE.
     */
    public static final String CONNECTOR_TYPE = "listeners.eventConfigurations";

    /**
     * The Constant log.
     */
    private static final Logger log = LoggerUtils.getLogger(ArchEventListenerConfigurationService.class);

    /**
     * The properties manager.
     */
    @Autowired
    private PropertiesManager propertiesManager;

    /**
     * Retrieve event configuration.
     *
     * @param className the class name
     * @return the arch event listener configuration service. event listener configuration
     */
    public EventListenerConfiguration retrieveEventConfiguration(String className) {
        EventListenerConfiguration config = new EventListenerConfiguration();
        if (this.propertiesManager.containsConnectorProperty(CONNECTOR_TYPE, className, "topic")) {
            config.setTopic((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "topic", String.class));
            config.setGroupId((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "groupId", String.class));
            config.setBootstrapServers((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "bootstrapServers", String.class));
            config.setSecurityProtocol((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "security.protocol", String.class));
            config.setSaslServiceName((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "sasl.kerberos.service.name", String.class));
            config.setSaslJaasConf((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "sasl.jaas.config", String.class));
            config.setSaslMechanism((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "sasl.mechanism", String.class));
            config.setMaxPollRecords((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "max.poll.records", Integer.class));
            config.setMaxPollIntervalMs((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "max.poll.interval.ms", Integer.class));
            config.setEnableAutoCommit((Boolean) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "enable.auto.commit", Boolean.class));
            config.setAutoCommitIntervalMs((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "auto.commit.interval.ms", Integer.class));
            config.setAutoOffsetReset((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "auto.offset.reset", String.class));
            config.setFetchMinBytes((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "fetch.min.bytes", Integer.class));
            config.setFetchMaxBytes((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "fetch.max.bytes", Integer.class));
            config.setFetchMaxWaitMs((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "fetch.max.wait.ms", Integer.class));
            config.setMetadataMaxAgeMs((Long) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "metadata.max.age.ms", Long.class));
            config.setMaxPartitionFetchBytes((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "max.partition.fetch.bytes", Integer.class));
            config.setClientId((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "client.id", String.class));
            config.setReconnectBackoffMs((Long) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "reconnect.backoff.ms", Long.class));
            config.setReconnectBackoffMaxMs((Long) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "reconnect.backoff.max.ms", Long.class));
            config.setRetryBackoffMs((Long) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "retry.backoff.ms", Long.class));
            config.setRetries((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "retries", Integer.class));
            config.setCheckCrcs((Boolean) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "check.crcs", Boolean.class));
            config.setConnectionsMaxIdleMs((Long) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "connections.max.idle.ms", Long.class));
            config.setRequestTimeoutMs((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "request.timeout.ms", Integer.class));
            config.setExcludeInternalTopics((Boolean) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "exclude.internal.topics", Boolean.class));
            config.setIsolationLevel((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "isolation.level", String.class));
            config.setAckOnError((Boolean) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "ackOnError", Boolean.class));
            config.setMaxFailures((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "maxFailures", Integer.class));
            config.setConcurrency((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "concurrency", Integer.class, 1));
            config.setSessionTimeoutMs((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "session.timeout.ms", Integer.class));
            config.setAckMode((String) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "ackMode", String.class));
            config.setAckCount((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "ackCount", Integer.class));
            config.setAckTime((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "ackTime", Integer.class));
            config.setSinglePool((Boolean) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "parallelPoll.singlePool", Boolean.class, false));
            config.setPoolSize((Integer) this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "parallelPoll.poolSize", Integer.class, 0));
            return config;
        } else {
            if (log.isWarnEnabled()) {
                log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
            }
            return null;
        }
    }

    /**
     * The Class EventListenerConfiguration.
     */
    public static class EventListenerConfiguration {

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
         * The sasl jaas mechanism.
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
         * The retries
         */
        private Integer retries;

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

        /** if >0 it will allocate a pool of thread in order to parallelize the message execution */
        private Integer poolSize;
        /** if true, it will allocate a pool of thread for each of the {@link #concurrency} consumer used */
        private boolean singlePool;

        /**
         * Gets the bootstrap servers.
         *
         * @return the bootstrap servers
         */
        public String getBootstrapServers() {
            return this.bootstrapServers;
        }

        /**
         * Sets the bootstrap servers.
         *
         * @param bootstrapServers the new bootstrap servers
         */
        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        /**
         * Gets the group id.
         *
         * @return the group id
         */
        public String getGroupId() {
            return this.groupId;
        }

        /**
         * Sets the group id.
         *
         * @param groupId the new group id
         */
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        /**
         * Gets the topic.
         *
         * @return the topic
         */
        public String getTopic() {
            return this.topic;
        }

        /**
         * Sets the topic.
         *
         * @param topic the new topic
         */
        public void setTopic(String topic) {
            this.topic = topic;
        }

        /**
         * Gets the security protocol.
         *
         * @return the security protocol
         */
        public String getSecurityProtocol() {
            return this.securityProtocol;
        }

        /**
         * Sets the security protocol.
         *
         * @param securityProtocol the new security protocol
         */
        public void setSecurityProtocol(String securityProtocol) {
            this.securityProtocol = securityProtocol;
        }

        /**
         * Gets the sasl service name.
         *
         * @return the sasl service name
         */
        public String getSaslServiceName() {
            return this.saslServiceName;
        }

        /**
         * Sets the sasl service name.
         *
         * @param saslServiceName the new sasl service name
         */
        public void setSaslServiceName(String saslServiceName) {
            this.saslServiceName = saslServiceName;
        }

        /**
         * Gets the sasl jaas conf.
         *
         * @return the sasl jaas conf
         */
        public String getSaslJaasConf() {
            return this.saslJaasConf;
        }

        /**
         * Sets the sasl jaas conf.
         *
         * @param saslJaasConf the new sasl jaas conf
         */
        public void setSaslJaasConf(String saslJaasConf) {
            this.saslJaasConf = saslJaasConf;
        }

        /**
         * Gets the max poll records.
         *
         * @return the max poll records
         */
        public Integer getMaxPollRecords() {
            return this.maxPollRecords;
        }

        /**
         * Sets the max poll records.
         *
         * @param maxPollRecords the new max poll records
         */
        public void setMaxPollRecords(Integer maxPollRecords) {
            this.maxPollRecords = maxPollRecords;
        }

        /**
         * Gets the max poll interval ms.
         *
         * @return the max poll interval ms
         */
        public Integer getMaxPollIntervalMs() {
            return this.maxPollIntervalMs;
        }

        /**
         * Sets the max poll interval ms.
         *
         * @param maxPollIntervalMs the new max poll interval ms
         */
        public void setMaxPollIntervalMs(Integer maxPollIntervalMs) {
            this.maxPollIntervalMs = maxPollIntervalMs;
        }

        /**
         * Gets the enable auto commit.
         *
         * @return the enable auto commit
         */
        public Boolean getEnableAutoCommit() {
            return this.enableAutoCommit;
        }

        /**
         * Sets the enable auto commit.
         *
         * @param enableAutoCommit the new enable auto commit
         */
        public void setEnableAutoCommit(Boolean enableAutoCommit) {
            this.enableAutoCommit = enableAutoCommit;
        }

        /**
         * Gets the auto commit interval ms.
         *
         * @return the auto commit interval ms
         */
        public Integer getAutoCommitIntervalMs() {
            return this.autoCommitIntervalMs;
        }

        /**
         * Sets the auto commit interval ms.
         *
         * @param autoCommitIntervalMs the new auto commit interval ms
         */
        public void setAutoCommitIntervalMs(Integer autoCommitIntervalMs) {
            this.autoCommitIntervalMs = autoCommitIntervalMs;
        }

        /**
         * Gets the auto offset reset.
         *
         * @return the auto offset reset
         */
        public String getAutoOffsetReset() {
            return this.autoOffsetReset;
        }

        /**
         * Sets the auto offset reset.
         *
         * @param autoOffsetReset the new auto offset reset
         */
        public void setAutoOffsetReset(String autoOffsetReset) {
            this.autoOffsetReset = autoOffsetReset;
        }

        /**
         * Gets the fetch min bytes.
         *
         * @return the fetch min bytes
         */
        public Integer getFetchMinBytes() {
            return this.fetchMinBytes;
        }

        /**
         * Sets the fetch min bytes.
         *
         * @param fetchMinBytes the new fetch min bytes
         */
        public void setFetchMinBytes(Integer fetchMinBytes) {
            this.fetchMinBytes = fetchMinBytes;
        }

        /**
         * Gets the fetch max bytes.
         *
         * @return the fetch max bytes
         */
        public Integer getFetchMaxBytes() {
            return this.fetchMaxBytes;
        }

        /**
         * Sets the fetch max bytes.
         *
         * @param fetchMaxBytes the new fetch max bytes
         */
        public void setFetchMaxBytes(Integer fetchMaxBytes) {
            this.fetchMaxBytes = fetchMaxBytes;
        }

        /**
         * Gets the fetch max wait ms.
         *
         * @return the fetch max wait ms
         */
        public Integer getFetchMaxWaitMs() {
            return this.fetchMaxWaitMs;
        }

        /**
         * Sets the fetch max wait ms.
         *
         * @param fetchMaxWaitMs the new fetch max wait ms
         */
        public void setFetchMaxWaitMs(Integer fetchMaxWaitMs) {
            this.fetchMaxWaitMs = fetchMaxWaitMs;
        }

        /**
         * Gets the metadata max age ms.
         *
         * @return the metadata max age ms
         */
        public Long getMetadataMaxAgeMs() {
            return this.metadataMaxAgeMs;
        }

        /**
         * Sets the metadata max age ms.
         *
         * @param metadataMaxAgeMs the new metadata max age ms
         */
        public void setMetadataMaxAgeMs(Long metadataMaxAgeMs) {
            this.metadataMaxAgeMs = metadataMaxAgeMs;
        }

        /**
         * Gets the max partition fetch bytes.
         *
         * @return the max partition fetch bytes
         */
        public Integer getMaxPartitionFetchBytes() {
            return this.maxPartitionFetchBytes;
        }

        /**
         * Sets the max partition fetch bytes.
         *
         * @param maxPartitionFetchBytes the new max partition fetch bytes
         */
        public void setMaxPartitionFetchBytes(Integer maxPartitionFetchBytes) {
            this.maxPartitionFetchBytes = maxPartitionFetchBytes;
        }

        /**
         * Gets the client id.
         *
         * @return the client id
         */
        public String getClientId() {
            return this.clientId;
        }

        /**
         * Sets the client id.
         *
         * @param clientId the new client id
         */
        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        /**
         * Gets the reconnect backoff ms.
         *
         * @return the reconnect backoff ms
         */
        public Long getReconnectBackoffMs() {
            return this.reconnectBackoffMs;
        }

        /**
         * Sets the reconnect backoff ms.
         *
         * @param reconnectBackoffMs the new reconnect backoff ms
         */
        public void setReconnectBackoffMs(Long reconnectBackoffMs) {
            this.reconnectBackoffMs = reconnectBackoffMs;
        }

        /**
         * Gets the reconnect backoff max ms.
         *
         * @return the reconnect backoff max ms
         */
        public Long getReconnectBackoffMaxMs() {
            return this.reconnectBackoffMaxMs;
        }

        /**
         * Sets the reconnect backoff max ms.
         *
         * @param reconnectBackoffMaxMs the new reconnect backoff max ms
         */
        public void setReconnectBackoffMaxMs(Long reconnectBackoffMaxMs) {
            this.reconnectBackoffMaxMs = reconnectBackoffMaxMs;
        }

        /**
         * Gets the retry backoff ms.
         *
         * @return the retry backoff ms
         */
        public Long getRetryBackoffMs() {
            return this.retryBackoffMs;
        }

        /**
         * Sets the retry backoff ms.
         *
         * @param retryBackoffMs the new retry backoff ms
         */
        public void setRetryBackoffMs(Long retryBackoffMs) {
            this.retryBackoffMs = retryBackoffMs;
        }

        public Integer getRetries() {
            return retries;
        }

        public void setRetries(Integer retries) {
            this.retries = retries;
        }

        /**
         * Gets the check crcs.
         *
         * @return the check crcs
         */
        public Boolean getCheckCrcs() {
            return this.checkCrcs;
        }

        /**
         * Sets the check crcs.
         *
         * @param checkCrcs the new check crcs
         */
        public void setCheckCrcs(Boolean checkCrcs) {
            this.checkCrcs = checkCrcs;
        }

        /**
         * Gets the connections max idle ms.
         *
         * @return the connections max idle ms
         */
        public Long getConnectionsMaxIdleMs() {
            return this.connectionsMaxIdleMs;
        }

        /**
         * Sets the connections max idle ms.
         *
         * @param connectionsMaxIdleMs the new connections max idle ms
         */
        public void setConnectionsMaxIdleMs(Long connectionsMaxIdleMs) {
            this.connectionsMaxIdleMs = connectionsMaxIdleMs;
        }

        /**
         * Gets the request timeout ms.
         *
         * @return the request timeout ms
         */
        public Integer getRequestTimeoutMs() {
            return this.requestTimeoutMs;
        }

        /**
         * Sets the request timeout ms.
         *
         * @param requestTimeoutMs the new request timeout ms
         */
        public void setRequestTimeoutMs(Integer requestTimeoutMs) {
            this.requestTimeoutMs = requestTimeoutMs;
        }

        /**
         * Gets the exclude internal topics.
         *
         * @return the exclude internal topics
         */
        public Boolean getExcludeInternalTopics() {
            return this.excludeInternalTopics;
        }

        /**
         * Sets the exclude internal topics.
         *
         * @param excludeInternalTopics the new exclude internal topics
         */
        public void setExcludeInternalTopics(Boolean excludeInternalTopics) {
            this.excludeInternalTopics = excludeInternalTopics;
        }

        /**
         * Gets the isolation level.
         *
         * @return the isolation level
         */
        public String getIsolationLevel() {
            return this.isolationLevel;
        }

        /**
         * Sets the isolation level.
         *
         * @param isolationLevel the new isolation level
         */
        public void setIsolationLevel(String isolationLevel) {
            this.isolationLevel = isolationLevel;
        }

        /**
         * Gets the ack on error.
         *
         * @return the ack on error
         */
        public Boolean getAckOnError() {
            return this.ackOnError;
        }

        /**
         * Sets the ack on error.
         *
         * @param ackOnError the new ack on error
         */
        public void setAckOnError(Boolean ackOnError) {
            this.ackOnError = ackOnError;
        }

        /**
         * Gets the max failures.
         *
         * @return the max failures
         */
        public Integer getMaxFailures() {
            return this.maxFailures;
        }

        /**
         * Sets the max failures.
         *
         * @param maxFailures the new max failures
         */
        public void setMaxFailures(Integer maxFailures) {
            this.maxFailures = maxFailures;
        }

        /**
         * Gets the concurrency.
         *
         * @return the concurrency
         */
        public Integer getConcurrency() {
            return this.concurrency;
        }

        /**
         * Sets the concurrency.
         *
         * @param concurrency the new concurrency
         */
        public void setConcurrency(Integer concurrency) {
            this.concurrency = concurrency;
        }

        /**
         * Gets the session timeout ms.
         *
         * @return the session timeout ms
         */
        public Integer getSessionTimeoutMs() {
            return this.sessionTimeoutMs;
        }

        /**
         * Sets the session timeout ms.
         *
         * @param sessionTimeoutMs the new session timeout ms
         */
        public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
        }

        public String getSaslMechanism() {
            return saslMechanism;
        }

        public void setSaslMechanism(String saslMechanism) {
            this.saslMechanism = saslMechanism;
        }

        public String getAckMode() {
            return ackMode;
        }

        public void setAckMode(String ackMode) {
            this.ackMode = ackMode;
        }

        public Integer getAckCount() { return ackCount; }

        public void setAckCount(Integer ackCount) {
            this.ackCount = ackCount;
        }

        public Integer getAckTime() {
            return ackTime;
        }

        public void setAckTime(Integer ackTime) {
            this.ackTime = ackTime;
        }

        public Integer getPoolSize() {
            return poolSize;
        }

        public void setPoolSize(Integer poolSize) {
            if(poolSize==null){
                poolSize=0;
            }
            this.poolSize = poolSize;
        }

        public boolean isSinglePool() {
            return singlePool;
        }

        public void setSinglePool(Boolean singlePool) {
            if(singlePool==null){
                singlePool=false;
            }
            this.singlePool = singlePool;
        }
    }
}

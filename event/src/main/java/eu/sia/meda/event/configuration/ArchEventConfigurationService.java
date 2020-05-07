package eu.sia.meda.event.configuration;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.properties.PropertiesManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ArchEventConfigurationService.
 */
@Service("EventConfiguration")
public class ArchEventConfigurationService {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(ArchEventConfigurationService.class);
   
   /** The Constant CONNECTOR_TYPE. */
   public static final String CONNECTOR_TYPE = "connectors.eventConfigurations";
   
   /** The properties manager. */
   @Autowired
   private PropertiesManager propertiesManager;

   /**
    * Retrieve event configuration.
    *
    * @param className the class name
    * @return the arch event configuration service. event configuration
    */
   public ArchEventConfigurationService.EventConfiguration retrieveEventConfiguration(String className) {
      ArchEventConfigurationService.EventConfiguration config = new ArchEventConfigurationService.EventConfiguration();
      if (this.propertiesManager.containsConnectorProperty(CONNECTOR_TYPE, className, "topic")) {
         config.setTopic((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "topic", String.class));
         config.setBootstrapServers((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "bootstrapServers", String.class));
         config.setSecurityProtocol((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "security.protocol", String.class));
         config.setSaslServiceName((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "sasl.kerberos.service.name", String.class));
         config.setSaslJaasConf((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "sasl.jaas.config", String.class));
         config.setSaslMechanism((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "sasl.mechanism", String.class));
         config.setBatchSize((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "batch.size", Integer.class));
         config.setBufferMemory((Long)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "buffer.memory", Long.class));
         config.setClientId((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "client.id", String.class));
         config.setCompressionType((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "compression.type", String.class));
         config.setLingerMs((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "linger.ms", Integer.class));
         config.setMaxBlockMs((Long)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "max.block.ms", Long.class));
         config.setMaxRequestSize((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "max.request.size", Integer.class));
         config.setMetadataMaxAgeMs((Long)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "metadata.max.age.ms", Long.class));
         config.setReconnectBackoffMaxMs((Long)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "reconnect.backoff.max.ms", Long.class));
         config.setReconnectBackoffMs((Long)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "reconnect.backoff.ms", Long.class));
         config.setRequestTimeoutMs((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "request.timeout.ms", Integer.class));
         config.setRetryBackoffMs((Long)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "retry.backoff.ms", Long.class));
         config.setTransactionTimeoutMs((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "transaction.timeout.ms", Integer.class));
         config.setTransactionalId((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "transactional.id", String.class));
         return config;
      } else {
    	 if(log.isWarnEnabled()) {
    		 log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
    	 }
         return null;
      }
   }

   /**
    * The Class EventConfiguration.
    */
   public static class EventConfiguration {
      
      /** The bootstrap servers. */
      private String bootstrapServers;
      
      /** The topic. */
      private String topic;
      
      /** The security protocol. */
      private String securityProtocol;
      
      /** The sasl service name. */
      private String saslServiceName;
      
      /** The sasl jaas conf. */
      private String saslJaasConf;
      
      /** The sasl jaas mechanism. */
      private String saslMechanism;
      
      /** The metadata max age ms. */
      private Long metadataMaxAgeMs;
      
      /** The batch size. */
      private Integer batchSize;
      
      /** The linger ms. */
      private Integer lingerMs;
      
      /** The client id. */
      private String clientId;
      
      /** The max request size. */
      private Integer maxRequestSize;
      
      /** The reconnect backoff ms. */
      private Long reconnectBackoffMs;
      
      /** The reconnect backoff max ms. */
      private Long reconnectBackoffMaxMs;
      
      /** The max block ms. */
      private Long maxBlockMs;
      
      /** The buffer memory. */
      private Long bufferMemory;
      
      /** The retry backoff ms. */
      private Long retryBackoffMs;
      
      /** The compression type. */
      private String compressionType;
      
      /** The request timeout ms. */
      private Integer requestTimeoutMs;
      
      /** The transaction timeout ms. */
      private Integer transactionTimeoutMs;
      
      /** The transactional id. */
      private String transactionalId;

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
       * Gets the batch size.
       *
       * @return the batch size
       */
      public Integer getBatchSize() {
         return this.batchSize;
      }

      /**
       * Sets the batch size.
       *
       * @param batchSize the new batch size
       */
      public void setBatchSize(Integer batchSize) {
         this.batchSize = batchSize;
      }

      /**
       * Gets the linger ms.
       *
       * @return the linger ms
       */
      public Integer getLingerMs() {
         return this.lingerMs;
      }

      /**
       * Sets the linger ms.
       *
       * @param lingerMs the new linger ms
       */
      public void setLingerMs(Integer lingerMs) {
         this.lingerMs = lingerMs;
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
       * Gets the max request size.
       *
       * @return the max request size
       */
      public Integer getMaxRequestSize() {
         return this.maxRequestSize;
      }

      /**
       * Sets the max request size.
       *
       * @param maxRequestSize the new max request size
       */
      public void setMaxRequestSize(Integer maxRequestSize) {
         this.maxRequestSize = maxRequestSize;
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
       * Gets the max block ms.
       *
       * @return the max block ms
       */
      public Long getMaxBlockMs() {
         return this.maxBlockMs;
      }

      /**
       * Sets the max block ms.
       *
       * @param maxBlockMs the new max block ms
       */
      public void setMaxBlockMs(Long maxBlockMs) {
         this.maxBlockMs = maxBlockMs;
      }

      /**
       * Gets the buffer memory.
       *
       * @return the buffer memory
       */
      public Long getBufferMemory() {
         return this.bufferMemory;
      }

      /**
       * Sets the buffer memory.
       *
       * @param bufferMemory the new buffer memory
       */
      public void setBufferMemory(Long bufferMemory) {
         this.bufferMemory = bufferMemory;
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

      /**
       * Gets the compression type.
       *
       * @return the compression type
       */
      public String getCompressionType() {
         return this.compressionType;
      }

      /**
       * Sets the compression type.
       *
       * @param compressionType the new compression type
       */
      public void setCompressionType(String compressionType) {
         this.compressionType = compressionType;
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
       * Gets the transaction timeout ms.
       *
       * @return the transaction timeout ms
       */
      public Integer getTransactionTimeoutMs() {
         return this.transactionTimeoutMs;
      }

      /**
       * Sets the transaction timeout ms.
       *
       * @param transactionTimeoutMs the new transaction timeout ms
       */
      public void setTransactionTimeoutMs(Integer transactionTimeoutMs) {
         this.transactionTimeoutMs = transactionTimeoutMs;
      }

      /**
       * Gets the transactional id.
       *
       * @return the transactional id
       */
      public String getTransactionalId() {
         return this.transactionalId;
      }

      /**
       * Sets the transactional id.
       *
       * @param transactionalId the new transactional id
       */
      public void setTransactionalId(String transactionalId) {
         this.transactionalId = transactionalId;
      }

		public String getSaslMechanism() {
			return saslMechanism;
		}
	
		public void setSaslMechanism(String saslMechanism) {
			this.saslMechanism = saslMechanism;
		}
   }
}

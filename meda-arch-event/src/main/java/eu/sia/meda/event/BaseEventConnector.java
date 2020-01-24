package eu.sia.meda.event;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.RequestContextHolder;
import eu.sia.meda.event.configuration.ArchEventConfigurationService;
import eu.sia.meda.event.producer.EventProducer;
import eu.sia.meda.event.producer.EventProducerImpl;
import eu.sia.meda.event.request.EventRequest;
import eu.sia.meda.event.response.EventResponse;
import eu.sia.meda.event.transformer.IEventRequestTransformer;
import eu.sia.meda.event.transformer.IEventResponseTransformer;
import eu.sia.meda.layers.connector.BaseConnector;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * The Class BaseEventConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 * @param <DTO> the generic type
 * @param <RESOURCE> the generic type
 */
public abstract class BaseEventConnector<INPUT, OUTPUT, DTO, RESOURCE> extends BaseConnector<EventRequest<DTO>, EventResponse<RESOURCE>> {
   
   /** The producer. */
   private EventProducer producer;
   
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
   
   /** The configuration. */
   @Autowired
   @Qualifier("EventConfiguration")
   private ArchEventConfigurationService configuration;

   /**
    * Inits the.
    */
   @PostConstruct
   private void init() {
      ArchEventConfigurationService.EventConfiguration eventConfiguration = this.configuration.retrieveEventConfiguration(this.getClass().getSimpleName());
      if (eventConfiguration == null) {
         throw new ExceptionInInitializerError();
      } else if (eventConfiguration.getTopic() == null) {
         throw new ExceptionInInitializerError();
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for connector: {}"),
						this.getClass().getSimpleName());

			}
			this.setBootstrapServers(eventConfiguration.getBootstrapServers());
			this.setTopic(eventConfiguration.getTopic());this.setSecurityProtocol(eventConfiguration.getSecurityProtocol());
			this.setSaslServiceName(eventConfiguration.getSaslServiceName());
			this.setSaslJaasConf(eventConfiguration.getSaslJaasConf());
			this.setBatchSize(eventConfiguration.getBatchSize());
			this.setBufferMemory(eventConfiguration.getBufferMemory());
			this.setClientId(eventConfiguration.getClientId());
			this.setCompressionType(eventConfiguration.getCompressionType());
			this.setLingerMs(eventConfiguration.getLingerMs());
			this.setMaxBlockMs(eventConfiguration.getMaxBlockMs());
			this.setMaxRequestSize(eventConfiguration.getMaxRequestSize());
			this.setMetadataMaxAgeMs(eventConfiguration.getMetadataMaxAgeMs());
			this.setReconnectBackoffMaxMs(eventConfiguration.getReconnectBackoffMaxMs());
			this.setReconnectBackoffMs(eventConfiguration.getReconnectBackoffMs());
			this.setRequestTimeoutMs(eventConfiguration.getRequestTimeoutMs());
			this.setRetryBackoffMs(eventConfiguration.getRetryBackoffMs());
			this.setTransactionTimeoutMs(eventConfiguration.getTransactionTimeoutMs());
			this.setTransactionalId(eventConfiguration.getTransactionalId());
			this.producer = this.getProducer();
		}
   }

   /**
    * Do execute.
    *
    * @param request the request
    * @return the event response
    */
   @Override
   protected EventResponse doExecute(EventRequest<DTO> request) {
      if (request.getPayload() == null) {
         return new EventResponse(false, "Payload cannot be null");
      } else {
         Headers headers = request.getHeaders();
         if(headers==null){
            headers = new RecordHeaders();
            request.setHeaders(headers);
         }
         headers.add("x-request-id", RequestContextHolder.getApplicationContext().getRequestId().getBytes(StandardCharsets.UTF_8));
         headers.add("x-originapp", RequestContextHolder.getApplicationContext().getOriginApp().getBytes(StandardCharsets.UTF_8));

         String topicTmp;
         if (request.getTopic() != null) {
        	 topicTmp = request.getTopic();
         } else {
        	 topicTmp = this.topic;
         }

         return new EventResponse(true, "Success", this.producer.send(topicTmp, request.getKey(), request.getPayload(), request.getHeaders()));
      }
   }

   /**
    * Call.
    *
    * @param input the input
    * @param requestTransformer the request transformer
    * @param responseTransformer the response transformer
    * @param args the args
    * @return the output
    */
   public final OUTPUT call(INPUT input, IEventRequestTransformer<INPUT, DTO> requestTransformer, IEventResponseTransformer<RESOURCE, OUTPUT> responseTransformer, Object... args) {
      EventRequest<DTO> eventRequest = requestTransformer.transform(input, args);
      EventResponse<RESOURCE> eventResponse = (EventResponse)this.execute(eventRequest);
      return responseTransformer.transform(eventResponse);
   }

   /**
    * Gets the producer.
    *
    * @return the producer
    */
   private EventProducer getProducer() {
      Map<String, Object> configProps = new HashMap();
      configProps.put("acks", "all");
      configProps.put("bootstrap.servers", this.bootstrapServers);
      configProps.put("key.serializer", StringSerializer.class);
      configProps.put("value.serializer", ByteArraySerializer.class);
      if (Strings.isNotEmpty(this.securityProtocol)) {
         configProps.put("security.protocol", this.securityProtocol);
      }

      if (Strings.isNotEmpty(this.saslServiceName)) {
         configProps.put("sasl.kerberos.service.name", this.saslServiceName);
      }

      if (Strings.isNotEmpty(this.saslJaasConf)) {
         configProps.put("sasl.jaas.config", this.saslJaasConf);
      }

      if (this.metadataMaxAgeMs != null) {
         configProps.put("metadata.max.age.ms", this.metadataMaxAgeMs);
      }

      if (this.batchSize != null) {
         configProps.put("batch.size", this.batchSize);
      }

      if (this.lingerMs != null) {
         configProps.put("linger.ms", this.lingerMs);
      }

      if (this.clientId != null) {
         configProps.put("client.id", this.clientId);
      }

      if (this.maxRequestSize != null) {
         configProps.put("max.request.size", this.maxRequestSize);
      }

      if (this.reconnectBackoffMs != null) {
         configProps.put("reconnect.backoff.ms", this.reconnectBackoffMs);
      }

      if (this.reconnectBackoffMaxMs != null) {
         configProps.put("reconnect.backoff.max.ms", this.reconnectBackoffMaxMs);
      }

      if (this.maxBlockMs != null) {
         configProps.put("max.block.ms", this.maxBlockMs);
      }

      if (this.bufferMemory != null) {
         configProps.put("buffer.memory", this.bufferMemory);
      }

      if (this.retryBackoffMs != null) {
         configProps.put("retry.backoff.ms", this.retryBackoffMs);
      }

      if (this.compressionType != null) {
         configProps.put("compression.type", this.compressionType);
      }

      if (this.requestTimeoutMs != null) {
         configProps.put("request.timeout.ms", this.requestTimeoutMs);
      }

      if (this.transactionTimeoutMs != null) {
         configProps.put("transaction.timeout.ms", this.transactionTimeoutMs);
      }

      if (this.transactionalId != null) {
         configProps.put("transactional.id", this.transactionalId);
      }

      ProducerFactory<String, byte[]> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
      KafkaTemplate<String, byte[]> kafkaTemplate = new KafkaTemplate<>(producerFactory);
      return new EventProducerImpl(kafkaTemplate);
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
    * Sets the topic.
    *
    * @param topic the new topic
    */
   public void setTopic(String topic) {
      this.topic = topic;
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
    * Sets the sasl service name.
    *
    * @param saslServiceName the new sasl service name
    */
   public void setSaslServiceName(String saslServiceName) {
      this.saslServiceName = saslServiceName;
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
    * Sets the metadata max age ms.
    *
    * @param metadataMaxAgeMs the new metadata max age ms
    */
   public void setMetadataMaxAgeMs(Long metadataMaxAgeMs) {
      this.metadataMaxAgeMs = metadataMaxAgeMs;
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
    * Sets the linger ms.
    *
    * @param lingerMs the new linger ms
    */
   public void setLingerMs(Integer lingerMs) {
      this.lingerMs = lingerMs;
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
    * Sets the max request size.
    *
    * @param maxRequestSize the new max request size
    */
   public void setMaxRequestSize(Integer maxRequestSize) {
      this.maxRequestSize = maxRequestSize;
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
    * Sets the reconnect backoff max ms.
    *
    * @param reconnectBackoffMaxMs the new reconnect backoff max ms
    */
   public void setReconnectBackoffMaxMs(Long reconnectBackoffMaxMs) {
      this.reconnectBackoffMaxMs = reconnectBackoffMaxMs;
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
    * Sets the buffer memory.
    *
    * @param bufferMemory the new buffer memory
    */
   public void setBufferMemory(Long bufferMemory) {
      this.bufferMemory = bufferMemory;
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
    * Sets the compression type.
    *
    * @param compressionType the new compression type
    */
   public void setCompressionType(String compressionType) {
      this.compressionType = compressionType;
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
    * Sets the transaction timeout ms.
    *
    * @param transactionTimeoutMs the new transaction timeout ms
    */
   public void setTransactionTimeoutMs(Integer transactionTimeoutMs) {
      this.transactionTimeoutMs = transactionTimeoutMs;
   }

   /**
    * Sets the transactional id.
    *
    * @param transactionalId the new transactional id
    */
   public void setTransactionalId(String transactionalId) {
      this.transactionalId = transactionalId;
   }
}

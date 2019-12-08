package eu.sia.meda.event.request;

import eu.sia.meda.layers.connector.request.BaseConnectorRequest;
import org.apache.kafka.common.header.Headers;

/**
 * The Class EventRequest.
 *
 * @param <INPUT> the generic type
 */
public class EventRequest<INPUT> extends BaseConnectorRequest {
   
   /** The key. */
   private String key;
   
   /** The payload. */
   private byte[] payload;
   
   /** The headers. */
   private Headers headers;
   
   /** The topic. */
   private String topic;

   /**
    * Gets the key.
    *
    * @return the key
    */
   public String getKey() {
      return this.key;
   }

   /**
    * Sets the key.
    *
    * @param key the new key
    */
   public void setKey(String key) {
      this.key = key;
   }

   /**
    * Gets the payload.
    *
    * @return the payload
    */
   public byte[] getPayload() {
      return this.payload;
   }

   /**
    * Sets the payload.
    *
    * @param payload the new payload
    */
   public void setPayload(byte[] payload) {
      this.payload = payload;
   }

   /**
    * Gets the headers.
    *
    * @return the headers
    */
   public Headers getHeaders() {
      return this.headers;
   }

   /**
    * Sets the headers.
    *
    * @param headers the new headers
    */
   public void setHeaders(Headers headers) {
      this.headers = headers;
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
}

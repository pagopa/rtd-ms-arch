package eu.sia.meda.event.response;

import eu.sia.meda.layers.connector.response.BaseConnectorResponse;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * The Class EventResponse.
 *
 * @param <OUTPUT> the generic type
 */
public class EventResponse<OUTPUT> extends BaseConnectorResponse<Boolean> {
   
   /** The success. */
   private boolean success = false;
   
   /** The message. */
   private String message;
   
   /** The send result future. */
   ListenableFuture<SendResult> sendResultFuture;

   /**
    * Instantiates a new event response.
    *
    * @param success the success
    * @param message the message
    */
   public EventResponse(boolean success, String message) {
      this.success = success;
      this.message = message;
   }

   /**
    * Instantiates a new event response.
    *
    * @param success the success
    * @param message the message
    * @param sendResultFuture the send result future
    */
   public EventResponse(boolean success, String message, ListenableFuture<SendResult> sendResultFuture) {
      this.success = success;
      this.message = message;
      this.sendResultFuture = sendResultFuture;
   }

   /**
    * Gets the result.
    *
    * @return the result
    */
   public Boolean getResult() {
      return this.success;
   }

   /**
    * Gets the message.
    *
    * @return the message
    */
   public String getMessage() {
      return this.message;
   }

   /**
    * Gets the send result future.
    *
    * @return the send result future
    */
   public ListenableFuture<SendResult> getSendResultFuture() {
      return this.sendResultFuture;
   }
}

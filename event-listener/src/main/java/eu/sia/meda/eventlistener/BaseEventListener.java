package eu.sia.meda.eventlistener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

/**
 * The listener interface for receiving baseEvent events.
 * The class that is interested in processing a baseEvent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addBaseEventListener<code> method. When
 * the baseEvent event occurs, that object's appropriate
 * method is invoked.
 *
 */
public abstract class BaseEventListener extends BaseListener implements AcknowledgingMessageListener<String, byte[]> {

   /**
    * On message.
    *
    * @param record the record
    */
   @Override
   public void onMessage(ConsumerRecord<String, byte[]> record) {
      try {
         this.preReceived(record);
         this.onMessage(record, defaultMessageConsumer, this::postReceived);
      } catch (Exception e) {
         EventContextHolder.clear();
         if(logger.isErrorEnabled()) {
        	 this.logger.error("exception in onMessage method: ", e);
         }
         throw e;
      }
   }

   /**
    * On message.
    *
    * @param record the record
    * @param ack the ack
    */
   public void onMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
      try {
         this.preReceived(record);
         if (Boolean.FALSE.equals(this.getEnableAutoCommit())) {
            this.onMessage(record, r->this.onReceived(r.value(), r.headers(), ack), this::postReceived);
         } else {
            this.onMessage(record, defaultMessageConsumer, this::postReceived);
         }

      } catch (Exception e) {
         EventContextHolder.clear();
         if(this.logger.isErrorEnabled()) {
        	 this.logger.error("exception in onMessage method: ", e);
         } 
         throw e;
      }
   }

   /**
    * On received.
    *
    * @param payload the payload
    * @param headers the headers
    * @param ack the ack
    */
   public void onReceived(byte[] payload, Headers headers, Acknowledgment ack) {
      this.onReceived(payload, headers);
      if (ack != null) {
         ack.acknowledge();
      }
   }
}

package eu.sia.meda.eventlistener;

import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import lombok.Data;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.BatchAcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
public abstract class BaseConsumerAwareEventListener extends BaseListener implements BatchAcknowledgingConsumerAwareMessageListener<String, byte[]> {

   private boolean isCountBasedCommit;
   private boolean isTimeBasedCommit;

   @Data
   private static class ConsumerContext {
      private int recordCount;
      private long nextCommitMillis;
      private Map<TopicPartition, OffsetAndMetadata> partition2MaxOffsetPartition = new HashMap<>();
   }

   @Override
   protected void configure(ArchEventListenerConfigurationService.EventListenerConfiguration eventConfiguration) {
      boolean isManualAck = Boolean.FALSE.equals(eventConfiguration.getEnableAutoCommit()) && "MANUAL".equals(eventConfiguration.getAckMode());
      isCountBasedCommit = isManualAck && eventConfiguration.getAckCount()!=null && eventConfiguration.getAckCount()>0;
      isTimeBasedCommit = isManualAck && eventConfiguration.getAckTime()!=null && eventConfiguration.getAckTime()>0;
      super.configure(eventConfiguration);

   }

   /**
    * On message.
    *
    * @param records the records
    */
   @Override
   public void onMessage(List<ConsumerRecord<String, byte[]>> records) {
      try {
         for (ConsumerRecord<String, byte[]> record : records) {
            this.preReceived(record);
            this.onMessage(record, defaultMessageConsumer, this::postReceived);
         }
      } catch (Exception e) {
         EventContextHolder.clear();
         if(logger.isErrorEnabled()) {
        	 this.logger.error("exception in onMessage method: ", e);
         }
         throw e;
      }
   }

   @Override
   public void onMessage(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack, final Consumer<?, ?> consumer) {
      try {
         if(records.size()>0){
            ConsumerContext consumerContext = new ConsumerContext();

            resetCommitDetails(consumerContext);

            List<Future<?>> tasks = new LinkedList<>();

            for (ConsumerRecord<String, byte[]> record : records) {
               this.preReceived(record);
               if (Boolean.FALSE.equals(this.getEnableAutoCommit())) {
                  tasks.add(this.onReceived(record, consumer, consumerContext));
               } else {
                  tasks.add(this.onMessage(record, defaultMessageConsumer, this::postReceived));
               }
            }

            if (ack != null) {
               ack.acknowledge();
            }

            if(logger.isDebugEnabled()){
               logger.debug("Waiting for the end of the tasks...");
            }
            for (Future<?> task : tasks) {
               try{
                  if(task!=null){
                     task.get();
                  }
               } catch (InterruptedException | ExecutionException e){
                  logger.error("Something gone wrong handling one record", e);
               }
            }

            if(isCountBasedCommit || isTimeBasedCommit){
               commit(consumer, consumerContext);
            }
         }
      } catch (Exception e) {
         EventContextHolder.clear();
         if(this.logger.isErrorEnabled()) {
            this.logger.error("exception in onMessage method: ", e);
         }
         throw e;
      }
   }

   protected void resetCommitDetails(ConsumerContext consumerContext) {
      consumerContext.setPartition2MaxOffsetPartition(new HashMap<>());
      consumerContext.setRecordCount(0);
      if(isTimeBasedCommit){
         consumerContext.setNextCommitMillis(System.currentTimeMillis()+getAckTime());
      }
   }

   /**
    * On received.
    *
    * @param record the record
    */
   public Future<?> onReceived(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer, ConsumerContext consumerContext) {
      return this.onMessage(record, defaultMessageConsumer, r -> {
         this.postReceived(r);

         if(isCountBasedCommit || isTimeBasedCommit) {
            TopicPartition partition = new TopicPartition(r.topic(), r.partition());
            /* not more synchronized because at the end of the poll set we will commit everything... in this way we are obtaining more speed
            synchronized (consumer) {*/
               OffsetAndMetadata maxOffset = consumerContext.getPartition2MaxOffsetPartition().get(partition);
               if (maxOffset == null || maxOffset.offset() < r.offset()) {
                  consumerContext.getPartition2MaxOffsetPartition().put(partition, new OffsetAndMetadata(r.offset()));
               }

               boolean countBasedCommit = false;
               if (isCountBasedCommit) {
                  int count = consumerContext.getRecordCount();

                  if (count >= getAckCount()) {
                     commit(consumer, consumerContext);
                     countBasedCommit = true;
                  } else {
                     consumerContext.setRecordCount(count + 1);
                  }
               }
               if (isTimeBasedCommit && !countBasedCommit && consumerContext.getNextCommitMillis() <= System.currentTimeMillis()) {
                  commit(consumer, consumerContext);
               }
//          }
         }
      });
   }

   protected void commit(Consumer<?, ?> consumer, ConsumerContext consumerContext){
      if(consumerContext.getPartition2MaxOffsetPartition()!=null && consumerContext.getPartition2MaxOffsetPartition().size()>0){
         consumer.commitAsync(consumerContext.getPartition2MaxOffsetPartition(), null);
      }
      resetCommitDetails(consumerContext);
   }

}

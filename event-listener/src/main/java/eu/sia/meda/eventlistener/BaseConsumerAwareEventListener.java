package eu.sia.meda.eventlistener;

import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.listener.BatchAcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentBatchErrorHandler;
import org.springframework.kafka.support.Acknowledgment;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
   private boolean ackManualAsyncCommit;
   private Duration ackManualSyncCommitTimeoutMs;

   private ThreadLocal<ConsumerContext> consumerContextThreadLocal;

   @Data
   protected static class ConsumerContext {
      private int recordCount;
      private long nextCommitMillis;
      private Map<TopicPartition, OffsetAndMetadata> partition2MaxOffsetPartition = new HashMap<>();
      private Map<TopicPartition, OffsetAndMetadata> partition2LastCommit = new HashMap<>();

      /** A context created for each consumer and provided as input into {@link #onReceived(byte[], Headers, Map, int, int)} method in order to provide a place where to store current poll records' common data.<br />
       * This object will be provided inside the methods {@link #beforeCommit(Map)} and {@link #onCompletedPoll(Map)} in order to have the chance to eventually process stored data.<br />
       * If {@link #isAsyncExecution()}, it will be a {@link ConcurrentHashMap} and each object stored inside of it should be thread safe otherwise it will be a {@link HashMap}
       */
      private final Map<String, Object> context;

      @Getter(AccessLevel.NONE)
      private AtomicBoolean abortingPollAtomic;

      public void setAbortingPoll(boolean abortingPoll){
         if(this.abortingPollAtomic != null){
            this.abortingPollAtomic.set(abortingPoll);
         }
      }

      public boolean isAbortingPoll(){
         return abortingPollAtomic!=null && abortingPollAtomic.get();
      }
   }

   @Override
   protected void configure(ArchEventListenerConfigurationService.EventListenerConfiguration eventConfiguration) {
      boolean isManualAck = Boolean.FALSE.equals(eventConfiguration.getEnableAutoCommit()) && "MANUAL".equals(eventConfiguration.getAckMode());
      isCountBasedCommit = isManualAck && eventConfiguration.getAckCount()!=null && eventConfiguration.getAckCount()>0;
      isTimeBasedCommit = isManualAck && eventConfiguration.getAckTime()!=null && eventConfiguration.getAckTime()>0;
      ackManualAsyncCommit=eventConfiguration.isAckManualAsyncCommit();
      if(!ackManualAsyncCommit){
         ackManualSyncCommitTimeoutMs = Duration.ofMillis(eventConfiguration.getAckManualSyncCommitTimeoutMs());
      }

      if(isCountBasedCommit||isTimeBasedCommit){
         consumerContextThreadLocal=new ThreadLocal<>();
      }

      if(isCountBasedCommit){
         logger.info(String.format("Configuring commit at fixed size %d for listener %s", eventConfiguration.getAckCount(), getClass()));
      }
      if(isTimeBasedCommit){
         logger.info(String.format("Configuring commit at ms %d for listener %s", eventConfiguration.getAckTime(), getClass()));
      }

      super.configure(eventConfiguration);
   }

   @Override
   protected SeekToCurrentBatchErrorHandler getBatchErrorHandler() {
      return new SeekToCurrentBatchErrorHandler() {
         @Override
         public void handle(Exception thrownException, ConsumerRecords<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container) {
            if (consumerContextThreadLocal != null) {
               ConsumerContext consumerContext = consumerContextThreadLocal.get();
               if (consumerContext != null) {
                  consumerContext.setAbortingPoll(true);

                  if (isCountBasedCommit || isTimeBasedCommit) {
                     consumerContext.getPartition2LastCommit().forEach((partition, offset) -> consumer.seek(partition, offset.offset()));
                     data.partitions().stream()
                             .filter(p -> !consumerContext.getPartition2LastCommit().containsKey(p))
                             .collect(
                                     Collectors.toMap(tp -> tp,
                                             tp -> data.records(tp).get(0).offset(),
                                             (u, v) -> v,
                                             LinkedHashMap::new))
                             .forEach(consumer::seek);
                     throw new KafkaException(String.format("Seek to last commit after exception: %s", consumerContext.getPartition2LastCommit()), thrownException);
                  }
               }
            }
            super.handle(thrownException, data, consumer, container);
         }
      };
   }

   /**
    * On message.
    *
    * @param records the records
    */
   @Override
   public void onMessage(List<ConsumerRecord<String, byte[]>> records) {
      try {
         ConsumerContext consumerContext = new ConsumerContext(new HashMap<>());
         int index=0;
         int pollSize = records.size();
         for (ConsumerRecord<String, byte[]> record : records) {
            this.preReceived(record);
            this.onMessage(record, buildConsumerAwareMessageConsumer(record, consumerContext, index, pollSize), this::postReceived, null);
            index++;
         }
         onCompletedPoll(consumerContext.getContext());
         beforeCommit(consumerContext.getContext());
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
      int[] currentIndexPoll={0};

      List<Future<?>> tasks = null;
      try {
         int pollSize = records.size();
         if(pollSize >0){
            if (logger.isInfoEnabled()) {
               logger.info(String.format("Starting poll of size %d...", pollSize));
            }

            if(logger.isTraceEnabled()){
               logger.trace(String.format("Polled:%s", records.stream().collect(Collectors.groupingBy(
                       ConsumerRecord::partition,
                       Collectors.reducing(
                               Pair.of(Long.MAX_VALUE, -1L),
                               r -> Pair.of(r.offset(), r.offset()),
                               (agg, r) -> Pair.of(Math.min(agg.getLeft(), r.getLeft()), Math.max(agg.getRight(), r.getRight()))
                       )))));
            }

            ConsumerContext consumerContext = new ConsumerContext(asyncExecution?new ConcurrentHashMap<>():new HashMap<>());
            if(isAsyncExecution()){
               consumerContext.setAbortingPollAtomic(new AtomicBoolean(false));
            }
            if(consumerContextThreadLocal!=null){
               consumerContextThreadLocal.set(consumerContext);
            }

            resetCommitDetails(consumerContext, true);

            onStartingPoll(pollSize, consumerContext.getContext());

            tasks = new LinkedList<>();

            int index=0;
            for (ConsumerRecord<String, byte[]> record : records) {
               this.preReceived(record);
               Future<?> task;
               if (Boolean.FALSE.equals(this.getEnableAutoCommit())) {
                  task = this.onReceived(record, consumer, consumerContext, index, pollSize);
               } else {
                  task = this.onMessage(record, buildConsumerAwareMessageConsumer(record, consumerContext, index, pollSize), this::postReceived, null, consumerContext::isAbortingPoll);
                  if("RECORD".equals(getAckMode())){
                     beforeCommit(consumerContext.getContext());
                  }
               }
               if(task!=null){
                  tasks.add(task);
               } else {
                  currentIndexPoll[0]++;
               }

               index++;
            }

            awaitTasks(tasks, currentIndexPoll, false);

            onCompletedPoll(consumerContext.getContext());

            if(isCountBasedCommit || isTimeBasedCommit){
               commit(consumer, consumerContext, true);
            } else if(Boolean.FALSE.equals(getEnableAutoCommit()) || !"RECORD".equals(getAckMode())) {
               beforeCommit(consumerContext.getContext());
            }

            if (ack != null) {
               ack.acknowledge();
            }

            if (logger.isInfoEnabled()) {
               logger.info(String.format("Current poll of size %d completed...", pollSize));
            }
         }
      } catch (Exception e) {
         if(this.logger.isErrorEnabled()) {
            this.logger.error(String.format("exception in onMessage method at pollIndex: %d", currentIndexPoll[0]), e);
         }
         try {
            if (tasks != null) {
               awaitTasks(tasks, currentIndexPoll, true);
            }
         } catch (RuntimeException ex2){
            this.logger.error(String.format("Something gone wrong while cleaning thread pool from consumer requests: %d", currentIndexPoll[0]), ex2);
         }
         EventContextHolder.clear();
         throw e;
      }
   }

   private void awaitTasks(List<Future<?>> tasks, int[] currentIndexPoll, boolean aborting) {
      if(tasks.size()>0) {
         if (logger.isDebugEnabled()) {
            logger.debug("Waiting for the end of the tasks...");
         }
         int toSkip = aborting?currentIndexPoll[0]+1:0;
         tasks.stream().skip(toSkip).forEach(task -> {
            try {
               if (task != null) {
                  Object result = task.get();
                  if(result instanceof Runnable){
                     ((Runnable) result).run();
                  }
                  currentIndexPoll[0]++;
               }
            } catch (InterruptedException | ExecutionException e) {
               logger.error("Something gone wrong handling one record", e);
            } catch (RuntimeException e){
               if(aborting){
                  logger.error(String.format("Something gone wrong handling one record at pollIndex %d, exception not re-thrown", currentIndexPoll[0]), e);
               } else {
                  throw e;
               }
            }
         });
         if (logger.isDebugEnabled()) {
            logger.debug("Tasks completed...");
         }
      }
   }

   protected void resetCommitDetails(ConsumerContext consumerContext, boolean isNotAbortingPoll) {
      if(isNotAbortingPoll){
         consumerContext.setPartition2MaxOffsetPartition(new HashMap<>());
      }
      consumerContext.setRecordCount(0);
      if(isTimeBasedCommit){
         consumerContext.setNextCommitMillis(System.currentTimeMillis()+getAckTime());
      }
   }

   /** On received */
   private Future<?> onReceived(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer, ConsumerContext consumerContext, int index, int pollSize) {
      return this.onMessage(record, buildConsumerAwareMessageConsumer(record, consumerContext, index, pollSize), this::postReceived, r -> {
         boolean isNotAbortingPoll = !consumerContext.isAbortingPoll();
         if(isNotAbortingPoll) {
            if (isCountBasedCommit || isTimeBasedCommit) {
               TopicPartition partition = new TopicPartition(r.topic(), r.partition());
               OffsetAndMetadata maxOffset = consumerContext.getPartition2MaxOffsetPartition().get(partition);
               if (maxOffset == null || maxOffset.offset() <= r.offset()) {
                  consumerContext.getPartition2MaxOffsetPartition().put(partition, new OffsetAndMetadata(r.offset() + 1));
               }

               boolean countBasedCommit = false;
               if (isCountBasedCommit) {
                  int count = consumerContext.getRecordCount() + 1;

                  if (count >= getAckCount()) {
                     commit(consumer, consumerContext, true);
                     countBasedCommit = true;
                  } else {
                     consumerContext.setRecordCount(count);
                  }
               }
               if (isTimeBasedCommit && !countBasedCommit && consumerContext.getNextCommitMillis() <= System.currentTimeMillis()) {
                  commit(consumer, consumerContext, true);
               }
            }
         }
      }, consumerContext::isAbortingPoll);
   }

   protected void commit(Consumer<?, ?> consumer, ConsumerContext consumerContext, boolean isNotAbortingPoll){
      try {
         if (isNotAbortingPoll) {
            beforeCommit(consumerContext.getContext());
            if (consumerContext.getPartition2MaxOffsetPartition() != null && consumerContext.getPartition2MaxOffsetPartition().size() > 0) {
               final HashMap<TopicPartition, OffsetAndMetadata> commit = new HashMap<>(consumerContext.getPartition2MaxOffsetPartition());
               logger.info(String.format("Committing: %s", commit));
               if (ackManualAsyncCommit) {
                  consumer.commitAsync(commit, (offsets, exception) -> {
                     if (exception != null) {
                        throw new IllegalStateException(String.format("Offset commit with offsets %s failed", offsets), exception);
                     }
                  });
               } else {
                  consumer.commitSync(commit, ackManualSyncCommitTimeoutMs);
               }
               consumerContext.setPartition2LastCommit(commit);
            }
         }
      } catch (RuntimeException e){
         consumerContext.setAbortingPoll(true);
         resetCommitDetails(consumerContext, false);
         throw e;
      }

      resetCommitDetails(consumerContext, isNotAbortingPoll);
   }

   private java.util.function.Consumer<ConsumerRecord<String, byte[]>> buildConsumerAwareMessageConsumer(ConsumerRecord<String, byte[]> record, ConsumerContext consumerContext, int index, int pollSize){
      return r -> this.onReceived(record, consumerContext.getContext(), index, pollSize);
   }

   /** To be overridden in order to access to {@link ConsumerRecord} and {@link ConsumerContext#context}. The default behavior will call the {@link #onReceived(byte[], Headers, Map, int, int)} */
   public void onReceived(ConsumerRecord<String, byte[]> record
           , Map<String, Object> consumerContext
           , int index
           , int pollSize){
      onReceived(record.value(), record.headers(), consumerContext, index, pollSize);
   }

   /** To be overridden in order to use {@link ConsumerContext#context}. The default behavior will call the {@link #onReceived(byte[], Headers)}<BR />
    * If {@link #isAsyncExecution()} the accesses on param consumerContext could be not thread safe!
    */
   public void onReceived(byte[] payload, Headers headers
           , @SuppressWarnings("unused") Map<String, Object> consumerContext
           , @SuppressWarnings("unused") int index
           , @SuppressWarnings("unused") int pollSize){
      onReceived(payload, headers);
   }

   /** method invoked before the current poll is processed. Inside this method, accesses on param consumerContext are thread safe  */
   protected void onStartingPoll(int pollSize, Map<String, Object>  consumerContext) {
   }

   /** method invoked when the current poll completes and before the {@link #beforeCommit(Map)} method. Inside this method, accesses on param consumerContext are thread safe */
   protected void onCompletedPoll(Map<String, Object> consumerContext) {
   }

   /** method invoked before the commit of the offsets. Inside this method, accesses on param consumerContext are thread safe  */
   protected void beforeCommit(Map<String, Object> consumerContext) {
   }
}

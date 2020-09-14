/*
 * 
 */
package eu.sia.meda.event.producer;

import com.fasterxml.jackson.core.Versioned;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Collection;
import java.util.List;

/**
 * The Interface EventProducer.
 */
public interface EventProducer {
   
   /**
    * Send.
    *
    * @param record the record
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(ProducerRecord record);

   /**
    * Send.
    *
    * @param topic the topic
    * @param list the list
    * @return the list
    */
   List<ListenableFuture<SendResult>> send(String topic, Collection list);

   /**
    * Send.
    *
    * @param topic the topic
    * @param o the o
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, Versioned o);

   /**
    * Send.
    *
    * @param topic the topic
    * @param key the key
    * @param o the o
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, String key, Versioned o);

   /**
    * Send.
    *
    * @param topic the topic
    * @param o the o
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, String o);

   /**
    * Send.
    *
    * @param topic the topic
    * @param r the r
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, ConsumerRecord r);

   /**
    * Send DLQ.
    *
    * @param r the r
    * @return the listenable future
    */
   ListenableFuture<SendResult> sendDLQ(ConsumerRecord r);

   /**
    * Send DLQ.
    *
    * @param dlqTopic the dlq topic
    * @param r the r
    * @return the listenable future
    */
   ListenableFuture<SendResult> sendDLQ(String dlqTopic, ConsumerRecord r);

   /**
    * Send.
    *
    * @param topic the topic
    * @param o the o
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, Object o);

   /**
    * Send.
    *
    * @param topic the topic
    * @param key the key
    * @param o the o
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, String key, Object o);

   /**
    * Send.
    *
    * @param topic the topic
    * @param o the o
    * @param headers the headers
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, Object o, Headers headers);

   /**
    * Send.
    *
    * @param topic the topic
    * @param key the key
    * @param o the o
    * @param headers the headers
    * @return the listenable future
    */
   ListenableFuture<SendResult> send(String topic, String key, Object o, Headers headers);
}

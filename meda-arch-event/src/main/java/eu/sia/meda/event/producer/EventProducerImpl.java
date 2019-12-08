package eu.sia.meda.event.producer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.sia.meda.config.LoggerUtils;

/**
 * The Class EventProducerImpl.
 */
public class EventProducerImpl implements EventProducer {

	/** The Constant KAFKA_TRANSACTION. */
	public static final String KAFKA_TRANSACTION = "kafka_transaction";

	/** The Constant log. */
	private static final Logger log = LoggerUtils.getLogger(EventProducerImpl.class);

	/** The kafka template. */
	private KafkaTemplate<String, byte[]> kafkaTemplate;

	/** The object mapper. */
	private final ObjectMapper objectMapper;

	/**
	 * Instantiates a new event producer impl.
	 *
	 * @param kafkaTemplate the kafka template
	 */
	public EventProducerImpl(KafkaTemplate<String, byte[]> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Send a record using kafkaTemplate
	 *
	 * @param r the r
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(ProducerRecord r) {
		if (!TransactionSynchronizationManager.isSynchronizationActive()) {
			try {
				return this.kafkaTemplate.send(r);
			} catch (Exception var3) {
				throw new UnsupportedOperationException(var3);
			}
		} else {
			if (!TransactionSynchronizationManager.hasResource(KAFKA_TRANSACTION)) {
				TransactionSynchronizationManager.bindResource(KAFKA_TRANSACTION, new LinkedList<>());
				TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
					@Override
					public void beforeCommit(boolean readOnly) {
						List<ListenableFuture> transactions = EventProducerImpl.getTransactions();

						try {
							Iterator<ListenableFuture> var3 = transactions.iterator();

							while (var3.hasNext()) {
								ListenableFuture listenableFuture = var3.next();
								listenableFuture.get();
							}
						} catch (Exception var5) {
							EventProducerImpl.log.debug("m=send, status=rollback, records={}", transactions.size());
							throw new UnsupportedOperationException(var5);
						}

						EventProducerImpl.log.debug(LoggerUtils.formatArchRow("m=send, status=committed, records={}"),
								transactions.size());
					}

					@Override
					public void afterCompletion(int status) {
						TransactionSynchronizationManager.unbindResource(KAFKA_TRANSACTION);
					}
				});
			}

			ListenableFuture<SendResult> listenableFuture = this.kafkaTemplate.send(r);
			getTransactions().add(listenableFuture);
			return listenableFuture;
		}
	}

	/**
	 * Send.
	 *
	 * @param topic the topic
	 * @param list  the list
	 * @return the list
	 */
	public List<ListenableFuture<SendResult>> send(String topic, Collection list) {
		List<ListenableFuture<SendResult>> futures = new ArrayList<>();
		Iterator var4 = list.iterator();

		while (var4.hasNext()) {
			Object o = var4.next();
			futures.add(this.send(topic, o));
		}

		return futures;
	}

	/**
	 * Send.
	 *
	 * @param topic the topic
	 * @param o     the o
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, Versioned o) {
		return this.send(topic, (String) null, (Versioned) o);
	}

	/**
	 * Send a message in json format using kafkaTemplate
	 *
	 * @param topic the topic
	 * @param key   the key
	 * @param o     the o
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, String key, Versioned o) {
		try {
			ProducerRecord<String, byte[]> r = new ProducerRecord<>(topic, key, this.objectMapper.writeValueAsBytes(o));
			r.headers().add(new RecordHeader("json.version", o.version().toFullString().getBytes()));
			return this.send(r);
		} catch (JsonProcessingException var5) {
			throw new UnsupportedOperationException(var5);
		}
	}

	/**
	 * Send.
	 *
	 * @param topic the topic
	 * @param v     the v
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, String v) {
		return this.send(new ProducerRecord(topic, v));
	}

	/**
	 * Send.
	 *
	 * @param topic the topic
	 * @param r     the r
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, ConsumerRecord r) {
		return this.send(new ProducerRecord(topic, (Integer) null, r.key(), r.value(), r.headers()));
	}

	/**
	 * Send DLQ.
	 *
	 * @param r the r
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> sendDLQ(ConsumerRecord r) {
		return this.sendDLQ(String.format("%s_DLQ", r.topic()), r);
	}

	/**
	 * Send DLQ.
	 *
	 * @param dlqTopic the dlq topic
	 * @param r        the r
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> sendDLQ(String dlqTopic, ConsumerRecord r) {
		return this.send(new ProducerRecord(dlqTopic, (Integer) null, r.key(), r.value(), r.headers()));
	}

	/**
	 * Send.
	 *
	 * @param topic the topic
	 * @param v     the v
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, Object v) {
		try {
			return this.send(topic, (String) null, (Object) v);
		} catch (Exception var4) {
			throw new UnsupportedOperationException(var4);
		}
	}

	/**
	 * Send.
	 *
	 * @param topic the topic
	 * @param key   the key
	 * @param v     the v
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, String key, Object v) {
		if(log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Sending event on topic={} with key={} with payload={}"), topic, key,
					new String((byte[]) v));			
		}
		return this.send(new ProducerRecord(topic, key, v));
	}

	/**
	 * Send.
	 *
	 * @param topic   the topic
	 * @param v       the v
	 * @param headers the headers
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, Object v, Headers headers) {
		if(log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Sending event on topic={} with headers={} with payload={}"), topic,
					headers, new String((byte[]) v));			
		}
		return this.send(new ProducerRecord(topic, (Integer) null, (Long) null, (Object) null, v, headers));
	}

	/**
	 * Send.
	 *
	 * @param topic   the topic
	 * @param key     the key
	 * @param v       the v
	 * @param headers the headers
	 * @return the listenable future
	 */
	public ListenableFuture<SendResult> send(String topic, String key, Object v, Headers headers) {
		if(log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Sending event on topic={} with key={} with headers={} with payload={}"),
					topic, key, headers, new String((byte[]) v));			
		}
		return this.send(new ProducerRecord(topic, (Integer) null, (Long) null, key, v, headers));
	}

	/**
	 * Gets the transactions.
	 *
	 * @return the transactions
	 */
	static List<ListenableFuture> getTransactions() {
		return (List<ListenableFuture>) TransactionSynchronizationManager.getResource(KAFKA_TRANSACTION);
	}
}

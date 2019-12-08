package eu.sia.meda.event.producer;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

class EventProducerImplTest {

	@Mock
	private KafkaTemplate<String, byte[]> mockKafkaTemplate;

	private EventProducerImpl eventProducerImplUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
		eventProducerImplUnderTest = new EventProducerImpl(mockKafkaTemplate);
	}

	@Test
	void testSend() {
		// Setup
		final ProducerRecord r = new ProducerRecord<>("topic", "value");
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send(r);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSend1() {
		// Setup
		final Collection list = Arrays.asList();
		final List<ListenableFuture<SendResult>> expectedResult = Arrays.asList();
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final List<ListenableFuture<SendResult>> result = eventProducerImplUnderTest.send("topic", list);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSend4() {
		// Setup
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send("topic", "v");

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSend5() {
		// Setup
		final ConsumerRecord r = new ConsumerRecord<>("topic", 0, 0L, "key", "value");
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send("topic", r);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSendDLQ() {
		// Setup
		final ConsumerRecord r = new ConsumerRecord<>("topic", 0, 0L, "key", "value");
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.sendDLQ(r);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSendDLQ1() {
		// Setup
		final ConsumerRecord r = new ConsumerRecord<>("topic", 0, 0L, "key", "value");
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.sendDLQ("dlqTopic", r);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSend6() {
		// Setup
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send("topic", "v");

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSend7() {
		// Setup
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send("topic", "key", "v".getBytes());

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSend8() {
		// Setup
		final Headers headers = null;
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send("topic", "value".getBytes(), headers);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testSend9() {
		// Setup
		final Headers headers = null;
		final ListenableFuture<SendResult> expectedResult = null;
		when(mockKafkaTemplate.send(new ProducerRecord<>("topic", null))).thenReturn(null);

		// Run the test
		final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send("topic", "key", "v".getBytes(), headers);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testGetTransactions() {
		// Run the test
		final List<ListenableFuture> result = EventProducerImpl.getTransactions();

		// Verify the results
		assertNull(result);
	}
}

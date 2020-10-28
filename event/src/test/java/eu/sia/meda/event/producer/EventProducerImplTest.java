package eu.sia.meda.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.concurrent.ListenableFuture;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class EventProducerImplTest {

    public static final String KAFKA_TRANSACTION = "kafka_transaction";
    @Mock
    private KafkaTemplate<String, byte[]> mockKafkaTemplate;
    private EventProducerImpl eventProducerImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        eventProducerImplUnderTest = Mockito.spy(new EventProducerImpl(mockKafkaTemplate));
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

    @Test
    public void testSendException() {
        ProducerRecord r = new ProducerRecord<>("topic", "value");
        Mockito.when(mockKafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenThrow(new RuntimeException());

        try {
            eventProducerImplUnderTest.send(r);
            Assert.fail("expected exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void testVersioned() throws JsonProcessingException {
        Versioned versioned = Mockito.mock(Versioned.class);
        Version version = Mockito.mock(Version.class);

        ListenableFuture<SendResult> expectedResult = null;
        Mockito.doReturn(expectedResult).when(mockKafkaTemplate).send(Mockito.eq(new ProducerRecord<>("topic", null)));
        Mockito.when(versioned.version()).thenReturn(version);
        Mockito.when(version.toFullString()).thenReturn("testVersion");
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapperMock.writeValueAsBytes(Mockito.any(Versioned.class))).thenReturn(new ObjectMapper().writeValueAsBytes(null));
        ReflectionTestUtils.setField(eventProducerImplUnderTest, EventProducerImpl.class, "objectMapper", objectMapperMock, ObjectMapper.class);

        ListenableFuture<SendResult> result = eventProducerImplUnderTest.send("topic", versioned);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testVersionedException() throws JsonProcessingException {
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.doThrow(new JsonProcessingException("error") {
        }).when(objectMapperMock).writeValueAsBytes(Mockito.any());
        ReflectionTestUtils.setField(eventProducerImplUnderTest, EventProducerImpl.class, "objectMapper", objectMapperMock, ObjectMapper.class);

        try {
            eventProducerImplUnderTest.send("topic", (Versioned) null);
            Assert.fail("expected exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void testSendSynchronizationActive() {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.initSynchronization();
        }

        final ProducerRecord<String, byte[]> r = new ProducerRecord<>("topic", "value".getBytes(StandardCharsets.UTF_8));
        ListenableFuture<SendResult> expectedResult = Mockito.mock(ListenableFuture.class);
        Mockito.doReturn(expectedResult).when(mockKafkaTemplate).send(Mockito.any(ProducerRecord.class));

        final ListenableFuture<SendResult> result = eventProducerImplUnderTest.send(r);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testTransactionSynchronizationManager() {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.initSynchronization();
        }

        final ProducerRecord<String, byte[]> r = new ProducerRecord<>("topic", "value".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(null).when(mockKafkaTemplate).send(Mockito.any(ProducerRecord.class));

        ListenableFuture<SendResult> result = eventProducerImplUnderTest.send(r);

        Assert.assertNull(result);

        Assert.assertTrue(TransactionSynchronizationManager.hasResource(KAFKA_TRANSACTION));
        TransactionSynchronization transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        Assert.assertNotNull(transactionSynchronization);
        try {
            transactionSynchronization.beforeCommit(true);
            Assert.fail("exception expected");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
        transactionSynchronization.afterCompletion(0);
        Assert.assertFalse(TransactionSynchronizationManager.hasResource(KAFKA_TRANSACTION));


        ListenableFuture<SendResult> listenableFutureMock = Mockito.mock(ListenableFuture.class);
        Mockito.doReturn(listenableFutureMock).when(mockKafkaTemplate).send(Mockito.any(ProducerRecord.class));

        result = eventProducerImplUnderTest.send(r);

        Assert.assertEquals(listenableFutureMock, result);

        Assert.assertTrue(TransactionSynchronizationManager.hasResource(KAFKA_TRANSACTION));
        transactionSynchronization = TransactionSynchronizationManager.getSynchronizations().get(0);
        Assert.assertNotNull(transactionSynchronization);

        transactionSynchronization.beforeCommit(true);

        transactionSynchronization.afterCompletion(0);
        Assert.assertFalse(TransactionSynchronizationManager.hasResource(KAFKA_TRANSACTION));
    }

    @Test
    void testSendCollection() {
        ListenableFuture listenableFutureMock = Mockito.mock(ListenableFuture.class);

        List<ListenableFuture<SendResult>> expectedResult = Collections.singletonList(listenableFutureMock);
        Mockito.doReturn(listenableFutureMock).when(eventProducerImplUnderTest).send("topic", null, (Object) null);

        List<ListenableFuture<SendResult>> result = eventProducerImplUnderTest.send("topic", Collections.singletonList(null));

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testSendCollectionWithException() {

        Mockito.doThrow(new RuntimeException()).when(eventProducerImplUnderTest).send("topic", null, (Object) null);

        try {
            eventProducerImplUnderTest.send("topic", Collections.singletonList(null));
            Assert.fail("expected exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }
}

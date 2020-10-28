package eu.sia.meda.eventlistener.utils;

import eu.sia.meda.BaseTest;
import eu.sia.meda.util.ColoredPrinters;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;

public class MedaRecordHeadersTest extends BaseTest {

    public static final String REQUEST_ID = "x-request-id";
    public static final String TRANSACTION_ID = "x-transaction-id";
    public static final String ORIGIN_APP = "x-originapp";

    @Test
    public void testReadHeader() {
        ColoredPrinters.PRINT_GREEN.println("Case: headerName is null");

        String result = MedaRecordHeaders.readHeader(null, null);
        Assert.assertNull(result);

        ColoredPrinters.PRINT_GREEN.println("Case: headers null");

        ConsumerRecord<String, byte[]> record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));

        ReflectionTestUtils.setField(record, ConsumerRecord.class, "headers", null, null);

        result = MedaRecordHeaders.readHeader(record, "headerName");
        Assert.assertNull(result);

        ColoredPrinters.PRINT_GREEN.println("Case: headers empty");

        record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));

        result = MedaRecordHeaders.readHeader(record, "headerName");
        Assert.assertNull(result);

        ColoredPrinters.PRINT_GREEN.println("Case: header present");

        record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));
        record.headers().add("headerName", "headerValue".getBytes(StandardCharsets.UTF_8));

        result = MedaRecordHeaders.readHeader(record, "headerName");
        Assert.assertNotNull(result);
        Assert.assertEquals("headerValue", result);
    }

    @Test
    public void testGetRequestId() {
        ColoredPrinters.PRINT_GREEN.println("Case no headers");

        ConsumerRecord<String, byte[]> record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));

        String result = MedaRecordHeaders.getRequestId(record);
        Assert.assertNotNull(result);

        ColoredPrinters.PRINT_GREEN.println("Case header found");

        record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));
        record.headers().add(REQUEST_ID, "requestIdHeader".getBytes(StandardCharsets.UTF_8));

        result = MedaRecordHeaders.getRequestId(record);
        Assert.assertNotNull(result);
        Assert.assertEquals("requestIdHeader", result);
    }

    @Test
    public void testGetOriginApp() {
        ColoredPrinters.PRINT_GREEN.println("Case no headers");

        ConsumerRecord<String, byte[]> record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));

        String result = MedaRecordHeaders.getOriginApp(record);
        Assert.assertNotNull(result);

        ColoredPrinters.PRINT_GREEN.println("Case header found");

        record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));
        record.headers().add(ORIGIN_APP, "originAppHeader".getBytes(StandardCharsets.UTF_8));

        result = MedaRecordHeaders.getOriginApp(record);
        Assert.assertNotNull(result);
        Assert.assertEquals("originAppHeader", result);
    }

    @Test
    public void testGetTransactionId() {
        ColoredPrinters.PRINT_GREEN.println("Case no headers");

        ConsumerRecord<String, byte[]> record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));

        String result = MedaRecordHeaders.getTransactionId(record);
        Assert.assertNotNull(result);

        ColoredPrinters.PRINT_GREEN.println("Case header found");

        record = new ConsumerRecord<>("topic", 0, 0, "topicKey", "topicValue".getBytes(StandardCharsets.UTF_8));
        record.headers().add(TRANSACTION_ID, "transactionIdHeader".getBytes(StandardCharsets.UTF_8));

        result = MedaRecordHeaders.getTransactionId(record);
        Assert.assertNotNull(result);
        Assert.assertEquals("transactionIdHeader", result);
    }
}

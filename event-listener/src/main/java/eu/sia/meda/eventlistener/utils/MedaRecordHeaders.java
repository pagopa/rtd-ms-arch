package eu.sia.meda.eventlistener.utils;

import com.google.common.base.Strings;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MedaRecordHeaders {

    /** The origing application setted as default */
    public static final String DEFAULT_ORIGING_APP = "UNKNOWN";
    public static final String DEFAULT_USER_ID = "anonymousUser";

    /**
     * Avoid instantiation
     */
    private MedaRecordHeaders() {
    }

    public static final String REQUEST_ID = "x-request-id";
    public static final String TRANSACTION_ID = "x-transaction-id";
    public static final String ORIGIN_APP = "x-originapp";
    public static final String USER_ID = "x-user-id";

    public static String readHeader(ConsumerRecord<String, byte[]> record, String headerName) {
        if (!Strings.isNullOrEmpty(headerName)) {
            Headers headers = record.headers();
            if (headers != null) {
                Header header = headers.lastHeader(headerName);
                if (header != null) {
                    return new String(header.value(), StandardCharsets.UTF_8);
                }
            }
        }
        return null;
    }

    /**
     * Gets the request id.
     *
     * @param record the request
     * @return the request id
     */
    public static String getRequestId(ConsumerRecord<String, byte[]> record) {
        String requestId = readHeader(record, REQUEST_ID);
        if (Strings.isNullOrEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        return requestId;
    }

    /**
     * Gets the originApp.
     *
     * @param record the request
     * @return the application that orginated the request
     */
    public static String getOriginApp(ConsumerRecord<String, byte[]> record) {
        String originApp = readHeader(record, ORIGIN_APP);
        if (Strings.isNullOrEmpty(originApp)) {
            originApp = DEFAULT_ORIGING_APP;
        }
        return originApp;
    }

    /**
     * Gets the transaction id.
     *
     * @param record the request
     * @return the transaction id
     */
    public static String getTransactionId(ConsumerRecord<String, byte[]> record) {
        String transactionId = readHeader(record, TRANSACTION_ID);
        if (Strings.isNullOrEmpty(transactionId)) {
            transactionId = UUID.randomUUID().toString();
        }
        return transactionId;
    }

    /**
     * Gets the transaction id.
     *
     * @param record the request
     * @return the transaction id
     */
    public static String getUserId(ConsumerRecord<String, byte[]> record) {
        String userId = readHeader(record, USER_ID);
        if (Strings.isNullOrEmpty(userId)) {
            userId = DEFAULT_USER_ID;
        }
        return userId;
    }
}

package eu.sia.meda.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Class ApplicationContext.
 */
public class ApplicationContext {
    public static final String REQUEST_ID = "x-request-id";
    public static final String APIM_REQUEST_ID = "x-apim-request-id";
    public static final String TRANSACTION_ID = "x-transaction-id";
    public static final String ORIGIN_APP = "x-originapp";
    public static final String USER_ID = "x-user-id";

    /**
     * The name.
     */
    private String name;

    /**
     * The transaction id.
     */
    private String transactionId;

    /**
     * The request id.
     */
    private String requestId;

    /**
     * The APIM request id.
     */
    private String apimRequestId;

    private String originApp;

    private String userId;

    /**
     * Header to be copied from the source (event or rest) to REST endpoints
     */
    private Map<String, String> copyHeader;

    /**
     * Inits the with default.
     *
     * @return the application context
     */
    public static ApplicationContext initWithDefault() {
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.setRequestId(UUID.randomUUID().toString());
        applicationContext.setTransactionId(UUID.randomUUID().toString());
        applicationContext.setOriginApp("UNKNOWN");
        applicationContext.setUserId("anonymousUser");
        applicationContext.setApimRequestId("NONE");

        applicationContext.buildDefaultCopyHeader();
        return applicationContext;
    }

    public void buildDefaultCopyHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put(ApplicationContext.REQUEST_ID, getRequestId());
        headers.put(ApplicationContext.APIM_REQUEST_ID, getApimRequestId());
        headers.put(ApplicationContext.TRANSACTION_ID, getTransactionId());
        headers.put(ApplicationContext.ORIGIN_APP, getOriginApp());
        headers.put(ApplicationContext.USER_ID, getUserId());
        setCopyHeader(headers);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the transaction id.
     *
     * @return the transaction id
     */
    public String getTransactionId() {
        return this.transactionId;
    }

    /**
     * Gets the request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return this.requestId;
    }

    /**
     * Gets the APIM request id.
     *
     * @return the APIM request id
     */
    public String getApimRequestId() {
        return this.apimRequestId;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the transaction id.
     *
     * @param transactionId the new transaction id
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Sets the request id.
     *
     * @param requestId the new request id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Sets the APIM request id.
     *
     * @param apimRequestId the new APIM request id
     */
    public void setApimRequestId(String apimRequestId) {
        this.apimRequestId = apimRequestId;
    }


    public String getOriginApp() {
        return originApp;
    }

    public void setOriginApp(String originApp) {
        this.originApp = originApp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getCopyHeader() {
        return copyHeader;
    }

    public void setCopyHeader(Map<String, String> copyHeader) {
        this.copyHeader = copyHeader;
    }
}

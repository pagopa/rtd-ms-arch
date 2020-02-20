package eu.sia.meda.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Class ApplicationContext.
 */
public class ApplicationContext {
   public static final String REQUEST_ID = "x-request-id";
   public static final String TRANSACTION_ID = "x-transaction-id";
   public static final String ORIGIN_APP = "x-originapp";
   
   /** The name. */
   private String name;
   
   /** The transaction id. */
   private String transactionId;
   
   /** The request id. */
   private String requestId;

   private String originApp;

   /** Header to be copied from the source (event or rest) to REST endpoints */
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

      applicationContext.buildDefaultCopyHeader();
      return applicationContext;
   }

   public void buildDefaultCopyHeader(){
      Map<String, String> headers = new HashMap<>();
      headers.put(ApplicationContext.REQUEST_ID, getRequestId());
      headers.put(ApplicationContext.TRANSACTION_ID, getTransactionId());
      headers.put(ApplicationContext.ORIGIN_APP, getOriginApp());
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


   public String getOriginApp() {
      return originApp;
   }

   public void setOriginApp(String originApp) {
      this.originApp = originApp;
   }

   public Map<String, String> getCopyHeader() {
      return copyHeader;
   }

   public void setCopyHeader(Map<String, String> copyHeader) {
      this.copyHeader = copyHeader;
   }
}

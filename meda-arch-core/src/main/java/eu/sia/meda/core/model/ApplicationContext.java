package eu.sia.meda.core.model;

import java.util.UUID;

/**
 * The Class ApplicationContext.
 */
public class ApplicationContext {
   
   /** The name. */
   private String name;
   
   /** The transaction id. */
   private String transactionId;
   
   /** The request id. */
   private String requestId;

   /**
    * Inits the with default.
    *
    * @return the application context
    */
   public static ApplicationContext initWithDefault() {
      ApplicationContext applicationContext = new ApplicationContext();
      applicationContext.setRequestId(UUID.randomUUID().toString());
      applicationContext.setTransactionId(UUID.randomUUID().toString());
      return applicationContext;
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
}

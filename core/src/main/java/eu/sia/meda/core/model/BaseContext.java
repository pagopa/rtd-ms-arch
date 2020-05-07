package eu.sia.meda.core.model;

import java.io.Serializable;

/**
 * The Class BaseContext.
 *
 * @param <U> the generic type
 * @param <C> the generic type
 */
public class BaseContext<U, C> implements Serializable {
   
   /** The Constant EMPTY_SESSION. */
   public static final BaseContext EMPTY_SESSION = new BaseContext<>();
   
   /** The session id. */
   private String sessionId;
   
   /** The environment. */
   private String environment;
   
   /** The user. */
   private U user;
   
   /** The customer. */
   private C customer;

   /**
    * Gets the session id.
    *
    * @return the session id
    */
   public String getSessionId() {
      return this.sessionId;
   }

   /**
    * Sets the session id.
    *
    * @param sessionId the new session id
    */
   public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
   }

   /**
    * Gets the environment.
    *
    * @return the environment
    */
   public String getEnvironment() {
      return this.environment;
   }

   /**
    * Sets the environment.
    *
    * @param environment the new environment
    */
   public void setEnvironment(String environment) {
      this.environment = environment;
   }

   /**
    * Gets the user.
    *
    * @return the user
    */
   public U getUser() {
      return this.user;
   }

   /**
    * Sets the user.
    *
    * @param user the new user
    */
   public void setUser(U user) {
      this.user = user;
   }

   /**
    * Gets the customer.
    *
    * @return the customer
    */
   public C getCustomer() {
      return this.customer;
   }

   /**
    * Sets the customer.
    *
    * @param customer the new customer
    */
   public void setCustomer(C customer) {
      this.customer = customer;
   }
}

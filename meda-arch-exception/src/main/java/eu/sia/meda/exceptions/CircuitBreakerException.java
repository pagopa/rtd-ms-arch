/*
 * 
 */
package eu.sia.meda.exceptions;

/**
 * The Class CircuitBreakerException.
 */
public class CircuitBreakerException extends Exception {
   
   /**
    * Instantiates a new circuit breaker exception.
    *
    * @param message the message
    */
   public CircuitBreakerException(String message) {
      super(message);
   }

   /**
    * Instantiates a new circuit breaker exception.
    *
    * @param message the message
    * @param cause the cause
    */
   public CircuitBreakerException(String message, Throwable cause) {
      super(message, cause);
   }

   /**
    * Instantiates a new circuit breaker exception.
    *
    * @param cause the cause
    */
   public CircuitBreakerException(Throwable cause) {
      super(cause);
   }
}

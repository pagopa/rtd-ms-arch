/*
 * 
 */
package eu.sia.meda.exceptions;

/**
 * The Class MedaTransactionException.
 */
public class MedaTransactionException extends Exception {
   
   /**
    * Instantiates a new meda transaction exception.
    *
    * @param message the message
    */
   public MedaTransactionException(String message) {
      super(message);
   }

   /**
    * Instantiates a new meda transaction exception.
    *
    * @param message the message
    * @param cause the cause
    */
   public MedaTransactionException(String message, Throwable cause) {
      super(message, cause);
   }

   /**
    * Instantiates a new meda transaction exception.
    *
    * @param cause the cause
    */
   public MedaTransactionException(Throwable cause) {
      super(cause);
   }
}

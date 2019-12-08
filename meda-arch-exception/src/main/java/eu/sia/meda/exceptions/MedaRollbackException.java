/*
 * 
 */
package eu.sia.meda.exceptions;

/**
 * The Class MedaRollbackException.
 */
public class MedaRollbackException extends Exception {
   
   /**
    * Instantiates a new meda rollback exception.
    *
    * @param message the message
    */
   public MedaRollbackException(String message) {
      super(message);
   }

   /**
    * Instantiates a new meda rollback exception.
    *
    * @param message the message
    * @param cause the cause
    */
   public MedaRollbackException(String message, Throwable cause) {
      super(message, cause);
   }

   /**
    * Instantiates a new meda rollback exception.
    *
    * @param cause the cause
    */
   public MedaRollbackException(Throwable cause) {
      super(cause);
   }
}

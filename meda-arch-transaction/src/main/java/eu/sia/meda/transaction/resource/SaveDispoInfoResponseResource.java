/*
 * 
 */
package eu.sia.meda.transaction.resource;

/**
 * The Class SaveDispoInfoResponseResource.
 */
public class SaveDispoInfoResponseResource {
   
   /** The exit code. */
   String exitCode;
   
   /** The message. */
   String message;

   /**
    * Gets the exit code.
    *
    * @return the exit code
    */
   public String getExitCode() {
      return this.exitCode;
   }

   /**
    * Sets the exit code.
    *
    * @param exitCode the new exit code
    */
   public void setExitCode(String exitCode) {
      this.exitCode = exitCode;
   }

   /**
    * Gets the message.
    *
    * @return the message
    */
   public String getMessage() {
      return this.message;
   }

   /**
    * Sets the message.
    *
    * @param message the new message
    */
   public void setMessage(String message) {
      this.message = message;
   }
}

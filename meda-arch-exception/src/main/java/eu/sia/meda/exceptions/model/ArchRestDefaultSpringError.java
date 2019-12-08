/*
 * 
 */
package eu.sia.meda.exceptions.model;

/**
 * The Class ArchRestDefaultSpringError.
 */
public class ArchRestDefaultSpringError {
   
   /** The timestamp. */
   private String timestamp;
   
   /** The status. */
   private String status;
   
   /** The message. */
   private String message;
   
   /** The error. */
   private String error;
   
   /** The exception. */
   private String exception;
   
   /** The path. */
   private String path;

   /**
    * Gets the timestamp.
    *
    * @return the timestamp
    */
   public String getTimestamp() {
      return this.timestamp;
   }

   /**
    * Sets the timestamp.
    *
    * @param timestamp the new timestamp
    */
   public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
   }

   /**
    * Gets the status.
    *
    * @return the status
    */
   public String getStatus() {
      return this.status;
   }

   /**
    * Sets the status.
    *
    * @param status the new status
    */
   public void setStatus(String status) {
      this.status = status;
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

   /**
    * Gets the error.
    *
    * @return the error
    */
   public String getError() {
      return this.error;
   }

   /**
    * Sets the error.
    *
    * @param error the new error
    */
   public void setError(String error) {
      this.error = error;
   }

   /**
    * Gets the exception.
    *
    * @return the exception
    */
   public String getException() {
      return this.exception;
   }

   /**
    * Sets the exception.
    *
    * @param exception the new exception
    */
   public void setException(String exception) {
      this.exception = exception;
   }

   /**
    * Gets the path.
    *
    * @return the path
    */
   public String getPath() {
      return this.path;
   }

   /**
    * Sets the path.
    *
    * @param path the new path
    */
   public void setPath(String path) {
      this.path = path;
   }
}

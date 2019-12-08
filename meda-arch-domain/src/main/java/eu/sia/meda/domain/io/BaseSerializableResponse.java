/*
 * 
 */
package eu.sia.meda.domain.io;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * The Class BaseSerializableResponse.
 */
public class BaseSerializableResponse implements Serializable {
   
   /** The Constant STATUSES_OK. */
   private static final List<String> STATUSES_OK = Arrays.asList("OK", "ok", "Ok", "200");
   
   /** The status code. */
   private String statusCode;
   
   /** The status message. */
   private String statusMessage;

   /**
    * Gets the status code.
    *
    * @return the status code
    */
   public String getStatusCode() {
      return this.statusCode;
   }

   /**
    * Sets the status code.
    *
    * @param statusCode the new status code
    */
   public void setStatusCode(String statusCode) {
      this.statusCode = statusCode;
   }

   /**
    * Gets the status message.
    *
    * @return the status message
    */
   public String getStatusMessage() {
      return this.statusMessage;
   }

   /**
    * Sets the status message.
    *
    * @param statusMessage the new status message
    */
   public void setStatusMessage(String statusMessage) {
      this.statusMessage = statusMessage;
   }

   /**
    * Checks for ok status.
    *
    * @return true, if successful
    */
   public boolean hasOkStatus() {
      return this.statusCode != null && STATUSES_OK.contains(this.statusCode);
   }
}

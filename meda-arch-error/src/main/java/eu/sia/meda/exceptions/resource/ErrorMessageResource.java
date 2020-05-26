package eu.sia.meda.exceptions.resource;

import lombok.Data;


/**
 * The Class ErrorMessageResource.
 */
@Data
public class ErrorMessageResource {
   
   /** The message. */
   private String message;
   
   /** The message title. */
   private String messageTitle;
   
   /** The message key. */
   private String messageKey;
   
   /** The retry. */
   private Boolean retry;
   
   /** The error code. */
   private String errorCode;

}

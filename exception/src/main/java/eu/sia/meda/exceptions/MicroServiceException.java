/*
 * 
 */
package eu.sia.meda.exceptions;

import eu.sia.meda.exceptions.model.ArchRestDefaultSpringError;
import eu.sia.meda.exceptions.model.MedaError;
import eu.sia.meda.exceptions.model.MedaErrorTypeEnum;
import eu.sia.meda.exceptions.response.MicroServiceExceptionResponse;
import java.util.List;

/**
 * The Class MicroServiceException.
 */
public class MicroServiceException extends Exception {
   
   /** The main error. */
   private final MedaError mainError;
   
   /** The errors. */
   private final List<MedaError> errors;

   /**
    * Instantiates a new micro service exception.
    */
   public MicroServiceException() {
      this.mainError = null;
      this.errors = null;
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param errorMessage the error message
    */
   public MicroServiceException(String errorMessage) {
      this.mainError = new MedaError(errorMessage);
      this.errors = null;
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param errorMessage the error message
    * @param errorCode the error code
    */
   public MicroServiceException(String errorMessage, String errorCode) {
      this.mainError = new MedaError(errorMessage, errorCode);
      this.errors = null;
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param errorMessage the error message
    * @param errorCode the error code
    * @param errorType the error type
    */
   public MicroServiceException(String errorMessage, String errorCode, MedaErrorTypeEnum errorType) {
      this.mainError = new MedaError(errorMessage, errorCode, errorType);
      this.errors = null;
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param e the e
    */
   public MicroServiceException(Exception e) {
      this.mainError = new MedaError(e);
      this.errors = null;
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param e the e
    * @param errorCode the error code
    */
   public MicroServiceException(Exception e, String errorCode) {
      this.mainError = new MedaError(e, errorCode);
      this.errors = null;
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param mainError the main error
    */
   public MicroServiceException(MedaError mainError) {
      this.mainError = mainError;
      this.errors = null;
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param microServiceExceptionResponse the micro service exception response
    */
   public MicroServiceException(MicroServiceExceptionResponse microServiceExceptionResponse) {
      this.mainError = microServiceExceptionResponse.getMainError();
      this.errors = microServiceExceptionResponse.getErrors();
   }

   /**
    * Instantiates a new micro service exception.
    *
    * @param springError the spring error
    */
   public MicroServiceException(ArchRestDefaultSpringError springError) {
      this.mainError = new MedaError(this);
      this.errors = null;
   }

   /**
    * Gets the message.
    *
    * @return the message
    */
   public String getMessage() {
      return this.mainError == null ? super.getMessage() : this.mainError.getMessage();
   }
}

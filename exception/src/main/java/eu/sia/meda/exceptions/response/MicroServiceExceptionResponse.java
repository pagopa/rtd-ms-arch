/*
 * 
 */
package eu.sia.meda.exceptions.response;

import eu.sia.meda.exceptions.model.MedaError;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * The Class MicroServiceExceptionResponse.
 */
public class MicroServiceExceptionResponse {
   
   /** The Constant serialVersionUID. */
   private static final long serialVersionUID = -8165726920213070584L;
   
   /** The main error. */
   private MedaError mainError;
   
   /** The errors. */
   private List<MedaError> errors;

   /**
    * Instantiates a new micro service exception response.
    */
   public MicroServiceExceptionResponse() {
   }

   /**
    * Instantiates a new micro service exception response.
    *
    * @param error the error
    */
   public MicroServiceExceptionResponse(MedaError error) {
      this.mainError = error;
   }

   /**
    * Instantiates a new micro service exception response.
    *
    * @param exception the exception
    */
   public MicroServiceExceptionResponse(Throwable exception) {
      this.mainError = new MedaError(exception);
   }

   /**
    * Gets the errors.
    *
    * @return the errors
    */
   public List<MedaError> getErrors() {
      return this.errors;
   }

   /**
    * Sets the errors.
    *
    * @param errors the new errors
    */
   public void setErrors(List<MedaError> errors) {
      this.errors = errors;
   }

   /**
    * Gets the main error.
    *
    * @return the main error
    */
   public MedaError getMainError() {
      return this.mainError;
   }

   /**
    * Sets the main error.
    *
    * @param mainError the new main error
    */
   public void setMainError(MedaError mainError) {
      this.mainError = mainError;
   }

   /**
    * To error payload response entity.
    *
    * @return the response entity
    */
   public ResponseEntity<MicroServiceExceptionResponse> toErrorPayloadResponseEntity() {
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      return new ResponseEntity(this, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}

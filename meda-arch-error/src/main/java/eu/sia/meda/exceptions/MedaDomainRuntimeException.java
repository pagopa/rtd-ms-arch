package eu.sia.meda.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import eu.sia.meda.exceptions.model.MedaErrorTypeEnum;
import eu.sia.meda.exceptions.model.MedaSeverityEnum;
import lombok.Data;

/**
 * The Class MedaDomainRuntimeException.
 */
@Data
public class MedaDomainRuntimeException extends RuntimeException implements IMedaDomainException {
   
   /** The code. */
   private final String code;
   
   /** The response status. */
   private final HttpStatus responseStatus;
   
   /** The error type. */
   private final MedaErrorTypeEnum errorType;
   
   /** The severity. */
   private final MedaSeverityEnum severity;
   
   /** The additional info. */
   private final Map<String, Object> additionalInfo;
   
   /** The raw remote error. */
   private final String rawRemoteError;
   
   /** The remote source. */
   private final String remoteSource;

   /**
    * Instantiates a new meda domain runtime exception.
    *
    * @param message the message
    * @param code the code
    * @param responseStatus the response status
    */
   public MedaDomainRuntimeException(String message, String code, HttpStatus responseStatus) {
      super(message);
      this.code = code;
      this.responseStatus = responseStatus;
      this.errorType = MedaErrorTypeEnum.BUSINESS;
      this.severity = MedaSeverityEnum.ERROR;
      this.additionalInfo = new HashMap();
      this.rawRemoteError = null;
      this.remoteSource = null;
   }

   /**
    * Instantiates a new meda domain runtime exception.
    *
    * @param message the message
    * @param code the code
    * @param responseStatus the response status
    * @param errorType the error type
    * @param severity the severity
    */
   public MedaDomainRuntimeException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity) {
      super(message);
      this.code = code;
      this.responseStatus = responseStatus;
      this.errorType = errorType;
      this.severity = severity;
      this.additionalInfo = new HashMap();
      this.rawRemoteError = null;
      this.remoteSource = null;
   }

   /**
    * Instantiates a new meda domain runtime exception.
    *
    * @param message the message
    * @param code the code
    * @param responseStatus the response status
    * @param errorType the error type
    * @param severity the severity
    * @param additionalInfo the additional info
    */
   public MedaDomainRuntimeException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity, Map<String, Object> additionalInfo) {
      super(message);
      this.code = code;
      this.responseStatus = responseStatus;
      this.errorType = errorType;
      this.severity = severity;
      this.additionalInfo = additionalInfo;
      this.rawRemoteError = null;
      this.remoteSource = null;
   }

   /**
    * Instantiates a new meda domain runtime exception.
    *
    * @param message the message
    * @param code the code
    * @param responseStatus the response status
    * @param errorType the error type
    * @param severity the severity
    * @param rawRemoteError the raw remote error
    * @param remoteSource the remote source
    */
   public MedaDomainRuntimeException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity, String rawRemoteError, String remoteSource) {
      super(message);
      this.code = code;
      this.responseStatus = responseStatus;
      this.errorType = errorType;
      this.severity = severity;
      this.additionalInfo = new HashMap();
      this.rawRemoteError = rawRemoteError;
      this.remoteSource = remoteSource;
   }

   /**
    * Instantiates a new meda domain runtime exception.
    *
    * @param message the message
    * @param code the code
    * @param responseStatus the response status
    * @param errorType the error type
    * @param severity the severity
    * @param additionalInfo the additional info
    * @param rawRemoteError the raw remote error
    * @param remoteSource the remote source
    */
   public MedaDomainRuntimeException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity, Map<String, Object> additionalInfo, String rawRemoteError, String remoteSource) {
      super(message);
      this.code = code;
      this.responseStatus = responseStatus;
      this.errorType = errorType;
      this.severity = severity;
      this.additionalInfo = additionalInfo;
      this.rawRemoteError = rawRemoteError;
      this.remoteSource = remoteSource;
   }

}

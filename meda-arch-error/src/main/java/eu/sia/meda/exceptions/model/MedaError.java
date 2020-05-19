/*
 * 
 */
package eu.sia.meda.exceptions.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import eu.sia.meda.exceptions.MedaDomainException;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;

/**
 * The Class MedaError.
 */
public class MedaError implements Serializable {
   
   /** The Constant MESSAGE_UNKNOWN. */
   public static final String MESSAGE_UNKNOWN = "Message unspecified";
   
   /** The message. */
   private String message;
   
   /** The code. */
   private String code;
   
   /** The error type. */
   private MedaErrorTypeEnum errorType;
   
   /** The severity. */
   private MedaSeverityEnum severity;
   
   /** The remote source. */
   private String remoteSource;
   
   /** The raw remote error. */
   private String rawRemoteError;
   
   /** The extended message. */
   private String extendedMessage;
   
   /** The localized message. */
   private String localizedMessage;
   
   /** The additional info. */
   private Map<String, Object> additionalInfo;
   
   /** The technical info. */
   private Map<String, Object> technicalInfo;

   /**
    * Instantiates a new meda error.
    *
    * @param errorMessage the error message
    * @param errorCode the error code
    * @param errorType the error type
    */
   public MedaError(String errorMessage, String errorCode, MedaErrorTypeEnum errorType) {
      this(errorMessage, errorCode, errorType, MedaSeverityEnum.ERROR);
   }

   /**
    * Instantiates a new meda error.
    *
    * @param errorMessage the error message
    * @param errorCode the error code
    * @param errorType the error type
    * @param severity the severity
    */
   public MedaError(String errorMessage, String errorCode, MedaErrorTypeEnum errorType, MedaSeverityEnum severity) {
      this.message = errorMessage != null ? errorMessage : MESSAGE_UNKNOWN;
      this.code = errorCode != null ? errorCode : MedaErrorCode.CODE_GENERIC;
      this.errorType = errorType != null ? errorType : MedaErrorTypeEnum.TECHNICAL;
      this.severity = severity != null ? severity : MedaSeverityEnum.ERROR;
   }

   /**
    * Instantiates a new meda error.
    */
   public MedaError() {
      this(MESSAGE_UNKNOWN, MedaErrorCode.CODE_GENERIC, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.ERROR);
   }

   /**
    * Instantiates a new meda error.
    *
    * @param errorMessage the error message
    */
   public MedaError(String errorMessage) {
      this(errorMessage, MedaErrorCode.CODE_GENERIC, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.ERROR);
   }

   /**
    * Instantiates a new meda error.
    *
    * @param errorMessage the error message
    * @param errorCode the error code
    */
   public MedaError(String errorMessage, String errorCode) {
      this(errorMessage, errorCode, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.ERROR);
   }

   /**
    * Instantiates a new meda error.
    *
    * @param medaException the meda exception
    */
   public MedaError(MedaDomainException medaException) {
      this(medaException.getMessage(), medaException.getCode(), medaException.getErrorType(), medaException.getSeverity());
      this.additionalInfo = medaException.getAdditionalInfo();
      this.rawRemoteError = medaException.getRawRemoteError();
      this.remoteSource = medaException.getRemoteSource();
   }

   /**
    * Instantiates a new meda error.
    *
    * @param medaException the meda exception
    */
   public MedaError(MedaDomainRuntimeException medaException) {
      this(medaException.getMessage(), medaException.getCode(), medaException.getErrorType(), medaException.getSeverity());
      this.additionalInfo = medaException.getAdditionalInfo();
      this.rawRemoteError = medaException.getRawRemoteError();
      this.remoteSource = medaException.getRemoteSource();
   }

   /**
    * Instantiates a new meda error.
    *
    * @param throwable the throwable
    */
   public MedaError(Throwable throwable) {
      this(throwable.getMessage(), MedaErrorCode.CODE_GENERIC, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.ERROR);
   }

   /**
    * Instantiates a new meda error.
    *
    * @param throwable the throwable
    * @param errorCode the error code
    */
   public MedaError(Throwable throwable, String errorCode) {
      this(throwable.getMessage(), errorCode, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.ERROR);
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
    * Gets the code.
    *
    * @return the code
    */
   public String getCode() {
      return this.code;
   }

   /**
    * Sets the code.
    *
    * @param code the new code
    */
   public void setCode(String code) {
      this.code = code;
   }

   /**
    * Gets the error type.
    *
    * @return the error type
    */
   public MedaErrorTypeEnum getErrorType() {
      return this.errorType;
   }

   /**
    * Sets the error type.
    *
    * @param errorType the new error type
    */
   public void setErrorType(MedaErrorTypeEnum errorType) {
      this.errorType = errorType;
   }

   /**
    * Gets the raw remote error.
    *
    * @return the raw remote error
    */
   public String getRawRemoteError() {
      return this.rawRemoteError;
   }

   /**
    * Sets the raw remote error.
    *
    * @param rawRemoteError the new raw remote error
    */
   public void setRawRemoteError(String rawRemoteError) {
      this.rawRemoteError = rawRemoteError;
   }

   /**
    * Gets the severity.
    *
    * @return the severity
    */
   public MedaSeverityEnum getSeverity() {
      return this.severity;
   }

   /**
    * Sets the severity.
    *
    * @param severity the new severity
    */
   public void setSeverity(MedaSeverityEnum severity) {
      this.severity = severity;
   }

   /**
    * Gets the remote source.
    *
    * @return the remote source
    */
   public String getRemoteSource() {
      return this.remoteSource;
   }

   /**
    * Sets the remote source.
    *
    * @param remoteSource the new remote source
    */
   public void setRemoteSource(String remoteSource) {
      this.remoteSource = remoteSource;
   }

   /**
    * Gets the technical info.
    *
    * @return the technical info
    */
   public Map<String, Object> getTechnicalInfo() {
      return this.technicalInfo;
   }

   /**
    * Sets the technical info.
    *
    * @param technicalInfo the technical info
    */
   public void setTechnicalInfo(Map<String, Object> technicalInfo) {
      this.technicalInfo = technicalInfo;
   }

   /**
    * Gets the extended message.
    *
    * @return the extended message
    */
   public String getExtendedMessage() {
      return this.extendedMessage;
   }

   /**
    * Sets the extended message.
    *
    * @param extendedMessage the new extended message
    */
   public void setExtendedMessage(String extendedMessage) {
      this.extendedMessage = extendedMessage;
   }

   /**
    * Gets the localized message.
    *
    * @return the localized message
    */
   public String getLocalizedMessage() {
      return this.localizedMessage;
   }

   /**
    * Sets the localized message.
    *
    * @param localizedMessage the new localized message
    */
   public void setLocalizedMessage(String localizedMessage) {
      this.localizedMessage = localizedMessage;
   }

   /**
    * Append technical info.
    *
    * @param info the info
    */
   public void appendTechnicalInfo(Map<String, Object> info) {
      if (info != null && !info.isEmpty()) {
         if (this.technicalInfo == null) {
            this.technicalInfo = new HashMap();
         }

         this.technicalInfo.putAll(info);
      }

   }

   /**
    * Append technical info.
    *
    * @param key the key
    * @param value the value
    */
   public void appendTechnicalInfo(String key, Object value) {
      if (key != null && !key.isEmpty()) {
         if (this.technicalInfo == null) {
            this.technicalInfo = new HashMap();
         }

         this.technicalInfo.put(key, value);
      }

   }

   /**
    * Append additional info.
    *
    * @param key the key
    * @param value the value
    */
   public void appendAdditionalInfo(String key, Object value) {
      if (key != null && !key.isEmpty()) {
         if (this.additionalInfo == null) {
            this.additionalInfo = new HashMap();
         }

         this.additionalInfo.put(key, value);
      }

   }

   /**
    * Append additional info.
    *
    * @param info the info
    */
   public void appendAdditionalInfo(Map<String, Object> info) {
      if (info != null && !info.isEmpty()) {
         if (this.additionalInfo == null) {
            this.additionalInfo = new HashMap();
         }

         this.additionalInfo.putAll(info);
      }

   }

   /**
    * To string.
    *
    * @return the string
    */
   public String toString() {
      return "Error(message=" + this.getMessage() + ", code=" + this.getCode() + ", errorType=" + this.getErrorType() + ", severity=" + this.getSeverity() + ", remoteSource=" + this.getRemoteSource() + ", rawRemoteError=" + this.getRawRemoteError() + ")";
   }

   /**
    * Gets the additional info.
    *
    * @return the additional info
    */
   public Map<String, Object> getAdditionalInfo() {
      return this.additionalInfo;
   }

   /**
    * Sets the additional info.
    *
    * @param additionalInfo the additional info
    */
   public void setAdditionalInfo(Map<String, Object> additionalInfo) {
      this.additionalInfo = additionalInfo;
   }
}

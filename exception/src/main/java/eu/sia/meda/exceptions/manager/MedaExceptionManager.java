/*
 * 
 */
package eu.sia.meda.exceptions.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import eu.sia.meda.exceptions.IMedaDomainException;
import eu.sia.meda.exceptions.MedaDomainException;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;
import eu.sia.meda.exceptions.MedaForbiddenException;
import eu.sia.meda.exceptions.config.ErrorHandlingConfiguration;
import eu.sia.meda.exceptions.config.ErrorMappingConfiguration;
import eu.sia.meda.exceptions.model.MedaError;
import eu.sia.meda.exceptions.model.MedaErrorTypeEnum;
import eu.sia.meda.exceptions.response.MicroServiceExceptionResponse;

/**
 * The Class MedaExceptionManager.
 */
public class MedaExceptionManager extends ResponseEntityExceptionHandler {
   
   /** The Constant ERROR_CODE_UNMAPPED. */
   public static final String ERROR_CODE_UNMAPPED = "Error Code Unmapped";
   
   /** The Constant UNKNOW_ERROR_MAPPING_LOG. */
   private static final String UNKNOW_ERROR_MAPPING_LOG = "[%s] exception[%s] code[%s] message[%s] cause[%s]";
   
   /** The Constant LOG_EXCEPTION_MESSAGE. */
   private static final String LOG_EXCEPTION_MESSAGE = "[MEDA_CORE] %s: Message %s";
   
   /** The log. */
   private final Logger log = LoggerFactory.getLogger(this.getClass());
   
   /** The error handling configurator. */
   @Autowired
   private ErrorHandlingConfiguration errorHandlingConfigurator;
   
   /** The error mapping configurator. */
   @Autowired
   private ErrorMappingConfiguration errorMappingConfigurator;

   /**
    * Handle illegal state exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the micro service exception response
    */
   @ExceptionHandler({IllegalStateException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private MicroServiceExceptionResponse handleIllegalStateException(IllegalStateException e, HttpServletRequest request, HttpServletResponse response) {
      MedaError error = this.generateError((Exception)e, HttpStatus.BAD_REQUEST, request);
      return new MicroServiceExceptionResponse(error);
   }

   /**
    * Handle illegal argument exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the micro service exception response
    */
   @ExceptionHandler({IllegalArgumentException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private MicroServiceExceptionResponse handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request, HttpServletResponse response) {
      MedaError error = this.generateError((Exception)e, HttpStatus.BAD_REQUEST, request);
      return new MicroServiceExceptionResponse(error);
   }

   /**
    * Handle access denied exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the micro service exception response
    */
   @ExceptionHandler({AccessDeniedException.class})
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   @ResponseBody
   private MicroServiceExceptionResponse handleAccessDeniedException(Exception e, HttpServletRequest request, HttpServletResponse response) {
      MedaError error = this.generateError(e, HttpStatus.UNAUTHORIZED, request);
      return new MicroServiceExceptionResponse(error);
   }

   /**
    * Handle method argument not valid.
    *
    * @param ex the ex
    * @param headers the headers
    * @param status the status
    * @param request the request
    * @return the response entity
    */
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      HttpServletRequest httpRequest = this.getHttpServletRequest(request);
      List<MedaError> errors = new ArrayList();
      MedaError error = this.generateError((Exception)ex, status, httpRequest);
      error.setMessage("Validation failed for object[" + ex.getBindingResult().getObjectName() + "] Error count: " + ex.getBindingResult().getErrorCount());
      MicroServiceExceptionResponse responseBody = new MicroServiceExceptionResponse(error);
      Iterator var9 = ex.getBindingResult().getFieldErrors().iterator();

      MedaError specificError;
      while(var9.hasNext()) {
         FieldError fieldError = (FieldError)var9.next();
         specificError = new MedaError(fieldError.getField() + ": " + fieldError.getDefaultMessage());
         specificError.appendAdditionalInfo("field", fieldError.getField());
         specificError.appendAdditionalInfo("objectName", fieldError.getObjectName());
         specificError.appendAdditionalInfo("rejectedValue", fieldError.getRejectedValue());
         specificError.appendAdditionalInfo("message", fieldError.getDefaultMessage());
         errors.add(specificError);
      }

      var9 = ex.getBindingResult().getGlobalErrors().iterator();

      while(var9.hasNext()) {
         ObjectError objError = (ObjectError)var9.next();
         specificError = new MedaError(objError.getObjectName() + ": " + objError.getDefaultMessage());
         errors.add(specificError);
      }

      responseBody.setErrors(errors);
      return new ResponseEntity(responseBody, headers, status);
   }

   /**
    * Handle meda domain exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the response entity
    */
   @ExceptionHandler({MedaDomainException.class})
   private ResponseEntity<MicroServiceExceptionResponse> handleMedaDomainException(MedaDomainException e, HttpServletRequest request, HttpServletResponse response) {
      HttpStatus status = this.verifyHttpStatus(e);
      MedaError error = this.generateError(e, status, request);
      MicroServiceExceptionResponse responseBody = new MicroServiceExceptionResponse(error);
      return new ResponseEntity(responseBody, status);
   }

   /**
    * Handle meda domain runtime exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the response entity
    */
   @ExceptionHandler({MedaDomainRuntimeException.class})
   private ResponseEntity<MicroServiceExceptionResponse> handleMedaDomainRuntimeException(MedaDomainRuntimeException e, HttpServletRequest request, HttpServletResponse response) {
      HttpStatus status = this.verifyHttpStatus(e);
      MedaError error = this.generateError(e, status, request);
      MicroServiceExceptionResponse responseBody = new MicroServiceExceptionResponse(error);
      return new ResponseEntity(responseBody, status);
   }

   /**
    * Verify http status.
    *
    * @param e the e
    * @return the http status
    */
   private HttpStatus verifyHttpStatus(IMedaDomainException e) {
      HttpStatus status;
      if (e != null && e.getResponseStatus() != null) {
         status = e.getResponseStatus();
      } else {
         status = HttpStatus.INTERNAL_SERVER_ERROR;
         this.log.warn("Wrong inizialization of MedaDomainException responseStatus must not be null. Setting default status 500.");
      }

      return status;
   }

   /**
    * Handle meda forbidden exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the micro service exception response
    */
   @ExceptionHandler({MedaForbiddenException.class})
   @ResponseStatus(HttpStatus.FORBIDDEN)
   @ResponseBody
   private MicroServiceExceptionResponse handleMedaForbiddenException(MedaForbiddenException e, HttpServletRequest request, HttpServletResponse response) {
      MedaError error = this.generateError((Exception)e, HttpStatus.FORBIDDEN, request);
      error.setErrorType(MedaErrorTypeEnum.BUSINESS);
      error.setCode(e.getCode());
      return new MicroServiceExceptionResponse(error);
   }

   /**
    * Handle generic exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the micro service exception response
    */
   @ExceptionHandler({Exception.class})
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ResponseBody
   private MicroServiceExceptionResponse handleGenericException(Exception e, HttpServletRequest request, HttpServletResponse response) {
      MedaError error = this.generateError(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
      return new MicroServiceExceptionResponse(error);
   }

   /**
    * Error emitter.
    *
    * @param exception the exception
    * @param error the error
    */
   private void errorEmitter(Exception exception, MedaError error) {
      this.completeLog(exception, error);
      this.emitEvent(exception, error);
   }

   /**
    * Error mapper.
    *
    * @param exception the exception
    * @param error the error
    * @return the meda error
    */
   private MedaError errorMapper(Exception exception, MedaError error) {
      Map<String, String> errorCodeMapping = this.errorMappingConfigurator.getErrors();
      if (errorCodeMapping != null && this.errorHandlingConfigurator.isEnableExceptionDecoding()) {
         error.setExtendedMessage((String)errorCodeMapping.getOrDefault(error.getCode(), null));
      }

      if (this.errorHandlingConfigurator.isEnableUnknowExceptionMapping() && !"GENERIC".equals(error.getCode()) && (errorCodeMapping == null || !errorCodeMapping.containsKey(error.getCode()))) {
         String exceptionMesage = String.format(UNKNOW_ERROR_MAPPING_LOG, this.errorHandlingConfigurator.getUnknowExceptionTag(), exception.getClass().getName(), error.getCode(), exception.getMessage(), error.getRawRemoteError());
         this.log.info(exceptionMesage);
      }

      return error;
   }

   /**
    * Generate error.
    *
    * @param exception the exception
    * @param status the status
    * @param request the request
    * @return the meda error
    */
   private MedaError generateError(MedaDomainException exception, HttpStatus status, HttpServletRequest request) {
      MedaError error = new MedaError(exception);
      return this.enrichError(exception, status, request, error);
   }

   /**
    * Generate error.
    *
    * @param exception the exception
    * @param status the status
    * @param request the request
    * @return the meda error
    */
   private MedaError generateError(MedaDomainRuntimeException exception, HttpStatus status, HttpServletRequest request) {
      MedaError error = new MedaError(exception);
      return this.enrichError(exception, status, request, error);
   }

   /**
    * Generate error.
    *
    * @param e the e
    * @param status the status
    * @param request the request
    * @return the meda error
    */
   private MedaError generateError(Exception e, HttpStatus status, HttpServletRequest request) {
      MedaError error = new MedaError(e);
      return this.enrichError(e, status, request, error);
   }

   /**
    * Enrich error.
    *
    * @param e the e
    * @param status the status
    * @param request the request
    * @param error the error
    * @return the meda error
    */
   private MedaError enrichError(Exception e, HttpStatus status, HttpServletRequest request, MedaError error) {
      this.errorEmitter(e, error);
      error = this.errorMapper(e, error);
      error.appendTechnicalInfo(this.addDefaultTechnicalInfos(e, status, request));
      error.appendTechnicalInfo(this.addTechnicalInfo(e, request));
      error.appendAdditionalInfo(this.addChannelInfo(e, request));
      return error;
   }

   /**
    * Adds the default technical infos.
    *
    * @param e the e
    * @param status the status
    * @param request the request
    * @return the map
    */
   private Map<String, Object> addDefaultTechnicalInfos(Exception e, HttpStatus status, @Nullable HttpServletRequest request) {
      HashMap<String, Object> technicalInfo = new HashMap();
      technicalInfo.put("timestamp", System.currentTimeMillis());
      technicalInfo.put("exception", e.getClass().getName());
      technicalInfo.put("error", status.getReasonPhrase());
      if (e.getMessage() != null) {
         technicalInfo.put("message", e.getMessage());
      } else {
         technicalInfo.put("message", "no message available");
      }

      technicalInfo.put("status", status.value());
      if (request != null) {
         technicalInfo.put("path", request.getRequestURI());
         technicalInfo.put("X-Request-ID", request.getHeader("X-Request-ID"));
      }

      return technicalInfo;
   }

   /**
    * Adds the technical info.
    *
    * @param e the e
    * @param request the request
    * @return the map
    */
   protected final Map<String, Object> addTechnicalInfo(Exception e, @Nullable HttpServletRequest request) {
      return new HashMap<>();
   }

   /**
    * Adds the channel info.
    *
    * @param e the e
    * @param request the request
    * @return the map
    */
   protected Map<String, Object> addChannelInfo(Exception e, @Nullable HttpServletRequest request) {
      return new HashMap<>();
   }

   /**
    * Complete log.
    *
    * @param <E> the element type
    * @param exception the exception
    * @param error the error
    */
   private <E extends Exception> void completeLog(E exception, MedaError error) {
      if (this.errorHandlingConfigurator.isEnableExceptionLogging()) {
         String exceptionMessage = String.format(LOG_EXCEPTION_MESSAGE, exception.getClass().getName(), exception.getMessage());
         if (this.errorHandlingConfigurator.isEnableStacktraceLogging()) {
            this.log.error(exceptionMessage, exception);
         } else {
            this.log.error(exceptionMessage);
         }
      }

   }

   /**
    * Emit event.
    *
    * @param <E> the element type
    * @param exception the exception
    * @param error the error
    */
   private <E extends Exception> void emitEvent(E exception, MedaError error) {
      if (this.errorHandlingConfigurator.isEnableExceptionEvent()) {
      }

   }

   /**
    * Handle exception internal.
    *
    * @param ex the ex
    * @param body the body
    * @param headers the headers
    * @param status the status
    * @param request the request
    * @return the response entity
    */
   protected final ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
      HttpServletRequest httpRequest = this.getHttpServletRequest(request);
      MedaError error = this.generateError(ex, status, httpRequest);
      MicroServiceExceptionResponse responseBody = new MicroServiceExceptionResponse(error);
      return new ResponseEntity<>(responseBody, headers, status);
   }

   /**
    * Gets the http servlet request.
    *
    * @param request the request
    * @return the http servlet request
    */
   private HttpServletRequest getHttpServletRequest(WebRequest request) {
      HttpServletRequest httpRequest = null;
      if (request instanceof ServletWebRequest) {
         ServletWebRequest servletWebRequest = (ServletWebRequest)request;
         httpRequest = servletWebRequest.getRequest();
      }

      return httpRequest;
   }
}

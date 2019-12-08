package eu.sia.meda.error.handler;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.interceptors.RequestContextHolder;
import eu.sia.meda.error.condition.ErrorManagerEnabledCondition;
import eu.sia.meda.error.consts.Constants;
import eu.sia.meda.error.helper.ErrorKeyExtractorHelper;
import eu.sia.meda.error.service.ErrorManagerService;
import eu.sia.meda.exceptions.MedaDomainException;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;
import eu.sia.meda.exceptions.IMedaDomainException;
import eu.sia.meda.exceptions.model.ErrorMessage;
import eu.sia.meda.exceptions.resource.ErrorResource;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * The Class MedaExceptionManager.
 */
@ControllerAdvice
@Conditional({ErrorManagerEnabledCondition.class})
public class MedaExceptionManager {
   
   

/** The Constant logger. */
   private static final Logger logger = LoggerUtils.getLogger(MedaExceptionManager.class);
   
   /** The Constant DEFAULT_SEVERITY. */
   private static final String DEFAULT_SEVERITY = "FATAL";
   
   /** The Constant UNHANDLED_EXCEPTION. */
   private static final String UNHANDLED_EXCEPTION = "unhandled exception: ";
   
   /** The error manager service. */
   @Autowired
   private ErrorManagerService errorManagerService;

   /**
    * Gets the error resource.
    *
    * @return the error resource
    */
   private ErrorResource getErrorResource() {
      Map<String, List<ErrorMessage>> errorMap = this.errorManagerService.getErrorMessages(BaseContextHolder.getErrorContext().getErrorKeys(), DEFAULT_SEVERITY);
      ErrorResource resource = new ErrorResource();
      resource.setReturnMessages(errorMap);
      return resource;
   }

   /**
    * Handle meda domain exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the response entity
    */
   @ExceptionHandler({MedaDomainException.class, MedaDomainRuntimeException.class})
   @ResponseBody
   private ResponseEntity<ErrorResource> handleMedaDomainException(IMedaDomainException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, (Exception)e);
      BaseContextHolder.getErrorContext().addErrorKey(ErrorKeyExtractorHelper.getMedaDomainExceptionErrorKey(e));
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      return new ResponseEntity<>(this.getErrorResource(), httpHeaders, e.getResponseStatus() != null ? e.getResponseStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
   }

   /**
    * Handle constraint violation exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({ConstraintViolationException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private ErrorResource handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      BaseContextHolder.getErrorContext().addErrorKeys(ErrorKeyExtractorHelper.getConstraintViolationExceptionErrorKeys(e));
      return this.getErrorResource();
   }

   /**
    * Handle method argument not valid exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({MethodArgumentNotValidException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private ErrorResource handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      BaseContextHolder.getErrorContext().addErrorKeys(ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e));
      return this.getErrorResource();
   }

   /**
    * Handle missing servlet request parameter exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({MissingServletRequestParameterException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private ErrorResource handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = "missing.parameter.error";
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }

   /**
    * Handle missing servlet request part exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({MissingServletRequestPartException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private ErrorResource handleMissingServletRequestPartException(MissingServletRequestPartException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = "missing.request.part.error";
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }

   /**
    * Handle missing path variable exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({MissingPathVariableException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private ErrorResource handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = "missing.path.variable.error";
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }

   /**
    * Handle http message not readable exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({HttpMessageNotReadableException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   private ErrorResource handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = "missing.body.error";
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }

   /**
    * Handle http media type not supported exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
   @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
   @ResponseBody
   private ErrorResource handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = "unsupported.media.type.error";
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }

   /**
    * Handle http media type not acceptable exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
   @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
   @ResponseBody
   private ErrorResource handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = "not.acceptable.media.type.error";
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }

   /**
    * Handle http request method not supported exception.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
   @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
   @ResponseBody
   private ErrorResource handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = "unsupported.method.error";
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }

   /**
    * Handle throwable.
    *
    * @param e the e
    * @param request the request
    * @param response the response
    * @return the error resource
    */
   @ExceptionHandler({Exception.class})
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ResponseBody
   private ErrorResource handleThrowable(Throwable e, HttpServletRequest request, HttpServletResponse response) {
      logger.error(UNHANDLED_EXCEPTION, e);
      String errorKey = Constants.GENERIC_ERROR_KEY;
      BaseContextHolder.getErrorContext().addErrorKey(errorKey);
      return this.getErrorResource();
   }
}

package eu.sia.meda.error.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import eu.sia.meda.error.consts.Constants;
import eu.sia.meda.error.helper.ErrorKeyExtractorHelper;
import eu.sia.meda.error.service.ErrorManagerService;
import eu.sia.meda.exceptions.IMedaDomainException;
import eu.sia.meda.exceptions.MedaDomainException;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;
import eu.sia.meda.exceptions.MedaTransactionException;
import eu.sia.meda.exceptions.model.ErrorMessage;
import eu.sia.meda.exceptions.resource.ErrorResource;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Class MedaExceptionManager.
 */
@ControllerAdvice
@Slf4j
public class MedaExceptionHandler {

    /**
     * The Constant DEFAULT_SEVERITY.
     */
    private static final String DEFAULT_SEVERITY = "ERROR";

    /**
     * The Constant UNHANDLED_EXCEPTION.
     */
    private static final String UNHANDLED_EXCEPTION = "unhandled exception: ";

    /**
     * The error manager service.
     */
    @Autowired
    private ErrorManagerService errorManagerService;

    /**
     * Handle meda domain exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the response entity
     */
    @ExceptionHandler({MedaDomainException.class, MedaDomainRuntimeException.class})
    @ResponseBody
    private ResponseEntity<ErrorResource> handleMedaDomainException(IMedaDomainException e, HttpServletRequest request,
                                                                    HttpServletResponse response) {
        if(e.getResponseStatus().is4xxClientError()){
            log.warn(UNHANDLED_EXCEPTION, (Exception) e);
        } else {
            log.error(UNHANDLED_EXCEPTION, (Exception) e);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(this.getErrorResource(ErrorKeyExtractorHelper.getMedaDomainExceptionErrorKey(e)),
                httpHeaders, e.getResponseStatus() != null ? e.getResponseStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResource getErrorResource(String errorKey) {
        Set<String> errorKeys = new HashSet<>();
        if (StringUtils.isNotBlank(errorKey)) {
            errorKeys.add(errorKey);
        }
        return this.getErrorResource(errorKeys);
    }

    /**
     * Gets the error resource.
     *
     * @return the error resource
     */
    private ErrorResource getErrorResource(Set<String> errorKeys) {
        Map<String, List<ErrorMessage>> errorMap = this.errorManagerService.getErrorMessages(errorKeys,
                DEFAULT_SEVERITY);
        ErrorResource resource = new ErrorResource();
        resource.setReturnMessages(errorMap);
        return resource;
    }

    /**
     * Handle meda transaction exception. For resolve the eventual message provided
     * by the step in error during the transaction
     *
     * @param e        the exception
     * @param request  the request
     * @param response the response
     * @return the response error entity
     */
    @ExceptionHandler({MedaTransactionException.class})
    @ResponseBody
    private ResponseEntity<ErrorResource> handleMedaTransactionException(MedaTransactionException e,
                                                                         HttpServletRequest request, HttpServletResponse response) {
        if (e.getCause() instanceof HttpStatusCodeException) {
            try {
                if (((HttpStatusCodeException) e.getCause()).getStatusCode().is4xxClientError()){
                    log.warn(UNHANDLED_EXCEPTION, e);
                }else{
                    log.error(UNHANDLED_EXCEPTION, e);
                }
                HttpStatusCodeException ise = (HttpStatusCodeException) e.getCause();
                // recover the original exception if it is an ErrorSource, wrapped by
                // transaction logic
                ErrorResource errorResource = new ObjectMapper().readValue(ise.getResponseBodyAsByteArray(),
                        ErrorResource.class);
                return new ResponseEntity<>(errorResource, ise.getStatusCode());

            } catch (Exception e1) {
                if (log.isErrorEnabled()) {
                    log.error(UNHANDLED_EXCEPTION,
                            "Error on recover original Exception in transaction logic, return generic exception");
                }
                return new ResponseEntity<>(handleThrowable(e, request, response), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.error(UNHANDLED_EXCEPTION, e);
            return new ResponseEntity<>(handleThrowable(e, request, response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handle throwable.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    private ErrorResource handleThrowable(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        log.error(UNHANDLED_EXCEPTION, e);
        String errorKey = Constants.GENERIC_ERROR_KEY;
        return this.getErrorResource(errorKey);
    }

    /**
     * Handle constraint violation exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResource handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request,
                                                             HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        return this.getErrorResource(ErrorKeyExtractorHelper.getConstraintViolationExceptionErrorKeys(e));
    }

    private ErrorResource getErrorResource(List<String> errorKeys) {
        return this.getErrorResource(new HashSet<>(errorKeys));
    }

    /**
     * Handle method argument not valid exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResource handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                HttpServletRequest request, HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        return this.getErrorResource(ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e));
    }

    /**
     * Handle missing servlet request parameter exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResource handleMissingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                        HttpServletRequest request, HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String name = e.getParameterName();
        String type = e.getParameterType();
        String errorKey = buildErrorKeyEnriched("missing.parameter.error", name, type);
        return this.getErrorResource(errorKey);
    }

    private String buildErrorKeyEnriched(String errorKey, String... details) {
        return String.format("%s;%s", errorKey, String.join(";", details));
    }

    /**
     * Handle missing servlet request part exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({MissingServletRequestPartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResource handleMissingServletRequestPartException(MissingServletRequestPartException e,
                                                                   HttpServletRequest request, HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String name = e.getRequestPartName();
        String errorKey = buildErrorKeyEnriched("missing.request.part.error", name);
        return this.getErrorResource(errorKey);
    }

    /**
     * Handle missing path variable exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({MissingPathVariableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResource handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request,
                                                             HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String name = e.getVariableName();
        String errorKey = buildErrorKeyEnriched("missing.path.variable.error", name);
        return this.getErrorResource(errorKey);
    }

    /**
     * Handle http message not readable exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResource handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                                HttpServletRequest request, HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String errorKey = "invalid.body.error";
        if (e.getCause() instanceof JsonMappingException) {
            JsonMappingException casted = (JsonMappingException) e.getCause();
            String path = casted.getPath().stream().map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));
            String value = casted.getOriginalMessage();
            if (e.getCause() instanceof InvalidFormatException) {
                value = ((InvalidFormatException) e.getCause()).getValue().toString();
            }
            String typeName = "unknown";
            if (casted instanceof MismatchedInputException) {
                typeName = ((MismatchedInputException) casted).getTargetType().getTypeName();
            }
            errorKey = buildErrorKeyEnriched(errorKey, path, value, typeName);
        }
        return this.getErrorResource(errorKey);
    }

    /**
     * Handle http media type not supported exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    private ErrorResource handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e,
                                                                   HttpServletRequest request, HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String errorKey = "unsupported.media.type.error";
        return this.getErrorResource(errorKey);
    }

    /**
     * Handle http media type not acceptable exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    private ErrorResource handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e,
                                                                    HttpServletRequest request, HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String errorKey = "not.acceptable.media.type.error";
        return this.getErrorResource(errorKey);
    }

    /**
     * Handle http request method not supported exception.
     *
     * @param e        the e
     * @param request  the request
     * @param response the response
     * @return the error resource
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    private ErrorResource handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                                       HttpServletRequest request, HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String errorKey = "unsupported.method.error";
        return this.getErrorResource(errorKey);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResource handleInvalidParam(MethodArgumentTypeMismatchException e, HttpServletRequest request,
                                             HttpServletResponse response) {
        log.warn(UNHANDLED_EXCEPTION, e);
        String errorKey = "invalid.param.error";
        String path = e.getName();
        String value = e.getValue() != null ? e.getValue().toString() : "null";
        String requiredType = e.getRequiredType() != null ? e.getRequiredType().getTypeName() : "null";
        errorKey = buildErrorKeyEnriched(errorKey, path, value, requiredType);
        return this.getErrorResource(errorKey);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException e) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.status());

        if (e.contentUTF8() != null && e.contentUTF8().startsWith("{\"returnMessages\":")
                && httpStatus != null && httpStatus.is4xxClientError()) {
            log.warn(UNHANDLED_EXCEPTION, e);
        } else {
            log.error(UNHANDLED_EXCEPTION, e);
        }
        return new ResponseEntity<>(e.contentUTF8(), httpHeaders, httpStatus);
    }
}
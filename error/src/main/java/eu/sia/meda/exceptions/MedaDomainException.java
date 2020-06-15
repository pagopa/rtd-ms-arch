package eu.sia.meda.exceptions;

import eu.sia.meda.exceptions.model.MedaErrorTypeEnum;
import eu.sia.meda.exceptions.model.MedaSeverityEnum;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * The Class MedaDomainException.
 */
public class MedaDomainException extends Exception implements IMedaDomainException {

    /**
     * The code.
     */
    private final String code;

    /**
     * The response status.
     */
    private final HttpStatus responseStatus;

    /**
     * The error type.
     */
    private final MedaErrorTypeEnum errorType;

    /**
     * The severity.
     */
    private final MedaSeverityEnum severity;

    /**
     * The additional info.
     */
    private final Map<String, Object> additionalInfo;

    /**
     * The raw remote error.
     */
    private final String rawRemoteError;

    /**
     * The remote source.
     */
    private final String remoteSource;

    /**
     * Instantiates a new meda domain exception.
     *
     * @param message        the message
     * @param code           the code
     * @param responseStatus the response status
     */
    public MedaDomainException(String message, String code, HttpStatus responseStatus) {
        super(message);
        this.code = code;
        this.responseStatus = responseStatus;
        this.errorType = MedaErrorTypeEnum.BUSINESS;
        this.severity = MedaSeverityEnum.ERROR;
        this.additionalInfo = null;
        this.rawRemoteError = null;
        this.remoteSource = null;
    }

    /**
     * Instantiates a new meda domain exception.
     *
     * @param message        the message
     * @param code           the code
     * @param responseStatus the response status
     * @param errorType      the error type
     * @param severity       the severity
     */
    public MedaDomainException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity) {
        super(message);
        this.code = code;
        this.responseStatus = responseStatus;
        this.errorType = errorType;
        this.severity = severity;
        this.additionalInfo = null;
        this.rawRemoteError = null;
        this.remoteSource = null;
    }

    /**
     * Instantiates a new meda domain exception.
     *
     * @param message        the message
     * @param code           the code
     * @param responseStatus the response status
     * @param errorType      the error type
     * @param severity       the severity
     * @param additionalInfo the additional info
     */
    public MedaDomainException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity, Map<String, Object> additionalInfo) {
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
     * Instantiates a new meda domain exception.
     *
     * @param message        the message
     * @param code           the code
     * @param responseStatus the response status
     * @param errorType      the error type
     * @param severity       the severity
     * @param rawRemoteError the raw remote error
     * @param remoteSource   the remote source
     */
    public MedaDomainException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity, String rawRemoteError, String remoteSource) {
        super(message);
        this.code = code;
        this.responseStatus = responseStatus;
        this.errorType = errorType;
        this.severity = severity;
        this.additionalInfo = null;
        this.rawRemoteError = rawRemoteError;
        this.remoteSource = remoteSource;
    }

    /**
     * Instantiates a new meda domain exception.
     *
     * @param message        the message
     * @param code           the code
     * @param responseStatus the response status
     * @param errorType      the error type
     * @param severity       the severity
     * @param additionalInfo the additional info
     * @param rawRemoteError the raw remote error
     * @param remoteSource   the remote source
     */
    public MedaDomainException(String message, String code, HttpStatus responseStatus, MedaErrorTypeEnum errorType, MedaSeverityEnum severity, Map<String, Object> additionalInfo, String rawRemoteError, String remoteSource) {
        super(message);
        this.code = code;
        this.responseStatus = responseStatus;
        this.errorType = errorType;
        this.severity = severity;
        this.additionalInfo = additionalInfo;
        this.rawRemoteError = rawRemoteError;
        this.remoteSource = remoteSource;
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
     * Gets the response status.
     *
     * @return the response status
     */
    public HttpStatus getResponseStatus() {
        return this.responseStatus;
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
     * Gets the severity.
     *
     * @return the severity
     */
    public MedaSeverityEnum getSeverity() {
        return this.severity;
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
     * Gets the raw remote error.
     *
     * @return the raw remote error
     */
    public String getRawRemoteError() {
        return this.rawRemoteError;
    }

    /**
     * Gets the remote source.
     *
     * @return the remote source
     */
    public String getRemoteSource() {
        return this.remoteSource;
    }
}

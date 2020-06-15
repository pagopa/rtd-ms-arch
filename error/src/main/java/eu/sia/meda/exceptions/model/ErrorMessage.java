package eu.sia.meda.exceptions.model;

import lombok.Data;

import java.io.Serializable;

/**
 * The Class ErrorMessage.
 */
@Data
public class ErrorMessage implements Serializable {

    /**
     * The message.
     */
    private String message;

    /**
     * The message title.
     */
    private String messageTitle;

    /**
     * The message key.
     */
    private String messageKey;

    /**
     * The retry.
     */
    private Boolean retry;

    /**
     * The error code.
     */
    private String errorCode;

    /**
     * The severity.
     */
    private String severity;

    /**
     * The language.
     */
    private String language;

}

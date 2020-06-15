package eu.sia.meda.exceptions;

/**
 * The Class MedaForbiddenException.
 */
public class MedaForbiddenException extends Exception {

    /**
     * The Constant GENERIC_FORBIDDEN.
     */
    private static final String GENERIC_FORBIDDEN = "GENERIC";

    /**
     * The code.
     */
    private final String code;

    /**
     * Instantiates a new meda forbidden exception.
     *
     * @param message the message
     */
    public MedaForbiddenException(String message) {
        this(message, GENERIC_FORBIDDEN);
    }

    /**
     * Instantiates a new meda forbidden exception.
     *
     * @param message the message
     * @param code    the code
     */
    public MedaForbiddenException(String message, String code) {
        super(message);
        this.code = code;
    }

}

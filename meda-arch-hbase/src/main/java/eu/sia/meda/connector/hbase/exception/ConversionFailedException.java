package eu.sia.meda.connector.hbase.exception;

import eu.sia.meda.connector.hbase.HBObjectMapper;

/**
 * Exception raised due to an unhandled scenario in {@link HBObjectMapper HBObjectMapper}
 */
public class ConversionFailedException extends RuntimeException {
    public ConversionFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }

}

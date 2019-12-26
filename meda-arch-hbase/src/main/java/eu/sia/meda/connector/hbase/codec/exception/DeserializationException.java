package eu.sia.meda.connector.hbase.codec.exception;

import java.io.IOException;

import eu.sia.meda.connector.hbase.codec.Codec;

/**
 * To be thrown when {@link Codec} fails to deserialize
 */
public class DeserializationException extends IOException {
    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

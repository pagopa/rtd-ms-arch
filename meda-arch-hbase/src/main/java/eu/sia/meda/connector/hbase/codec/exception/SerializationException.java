package eu.sia.meda.connector.hbase.codec.exception;

import java.io.IOException;

import eu.sia.meda.connector.hbase.codec.Codec;

/**
 * To be thrown when {@link Codec} fails to serialize
 */
public class SerializationException extends IOException {

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package eu.sia.meda.connector.hbase.exception;

public class ObjectNotInstantiatableException extends IllegalArgumentException {
    public ObjectNotInstantiatableException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

package eu.sia.meda.connector.hbase.exception;

public class RowKeyCantBeComposedException extends IllegalArgumentException {
    public RowKeyCantBeComposedException(Throwable throwable) {
        super("Error while composing row key for object", throwable);
    }
}

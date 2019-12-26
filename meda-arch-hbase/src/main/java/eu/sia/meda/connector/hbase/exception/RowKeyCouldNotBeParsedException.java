package eu.sia.meda.connector.hbase.exception;

public class RowKeyCouldNotBeParsedException extends IllegalArgumentException {
    public RowKeyCouldNotBeParsedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

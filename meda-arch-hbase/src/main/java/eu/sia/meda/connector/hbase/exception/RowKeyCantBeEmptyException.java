package eu.sia.meda.connector.hbase.exception;

public class RowKeyCantBeEmptyException extends IllegalArgumentException {
    public RowKeyCantBeEmptyException() {
        super("Row key composed for object is null or empty");
    }
}

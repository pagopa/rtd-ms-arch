package eu.sia.meda.connector.hbase.exception;

public class FieldsMappedToSameColumnException extends IllegalArgumentException {
    public FieldsMappedToSameColumnException(String s) {
        super(s);
    }
}

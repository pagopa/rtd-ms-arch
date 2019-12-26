package eu.sia.meda.connector.hbase.exception;

public class UnsupportedFieldTypeException extends IllegalArgumentException {
    public UnsupportedFieldTypeException(String s) {
        super(s);
    }
}

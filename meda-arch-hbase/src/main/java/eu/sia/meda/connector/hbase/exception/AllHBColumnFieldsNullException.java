package eu.sia.meda.connector.hbase.exception;

public class AllHBColumnFieldsNullException extends IllegalArgumentException {
    public AllHBColumnFieldsNullException() {
        super("Cannot accept input object with all it's column-mapped variables null");
    }
}

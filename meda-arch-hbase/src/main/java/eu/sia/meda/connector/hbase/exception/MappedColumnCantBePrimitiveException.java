package eu.sia.meda.connector.hbase.exception;

public class MappedColumnCantBePrimitiveException extends IllegalArgumentException {
    public MappedColumnCantBePrimitiveException(String s) {
        super(s);
    }

}

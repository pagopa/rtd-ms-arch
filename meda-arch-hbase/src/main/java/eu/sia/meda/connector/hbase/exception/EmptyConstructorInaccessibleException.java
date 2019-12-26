package eu.sia.meda.connector.hbase.exception;

public class EmptyConstructorInaccessibleException extends IllegalArgumentException {
    public EmptyConstructorInaccessibleException(String s) {
        super(s);
    }
}

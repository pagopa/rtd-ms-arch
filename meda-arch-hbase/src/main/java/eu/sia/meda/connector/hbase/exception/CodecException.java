package eu.sia.meda.connector.hbase.exception;

public class CodecException extends IllegalArgumentException {
    public CodecException(String s, Throwable t) {
        super(s, t);
    }
}

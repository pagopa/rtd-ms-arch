package eu.sia.meda.connector.hbase.exception;

import java.util.NavigableMap;

import eu.sia.meda.connector.hbase.annotation.HBColumnMultiVersion;

public class IncompatibleFieldForHBColumnMultiVersionAnnotationException extends IllegalArgumentException {
    public IncompatibleFieldForHBColumnMultiVersionAnnotationException(String message) {
        super(String.format("A field annotated with @%s should be of type %s<%s, ?> (%s)", HBColumnMultiVersion.class.getName(), NavigableMap.class.getName(), Long.class.getName(), message));
    }
}

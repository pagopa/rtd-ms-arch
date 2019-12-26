package eu.sia.meda.connector.hbase.exception;

import java.lang.reflect.Field;

import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBColumnMultiVersion;

public class BothHBColumnAnnotationsPresentException extends IllegalArgumentException {

    public BothHBColumnAnnotationsPresentException(Field field) {
        super(String.format("Class %s has a field %s that's annotated with both @%s and @%s (you can use only one of them on a field)", field.getDeclaringClass(), field.getName(), HBColumn.class.getName(), HBColumnMultiVersion.class.getName()));
    }
}

package eu.sia.meda.connector.hbase.exception;

import eu.sia.meda.connector.hbase.annotation.HBColumnMultiVersion;

public class FieldAnnotatedWithHBColumnMultiVersionCantBeEmpty extends IllegalArgumentException {
    public FieldAnnotatedWithHBColumnMultiVersionCantBeEmpty() {
        super("Fields annotated with @" + HBColumnMultiVersion.class.getName() + " cannot be empty (null is ok, though)");
    }
}

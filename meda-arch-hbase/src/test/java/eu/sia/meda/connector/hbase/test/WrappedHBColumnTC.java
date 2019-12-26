package eu.sia.meda.connector.hbase.test;

import java.lang.reflect.Field;

import eu.sia.meda.connector.hbase.WrappedHBColumn;
/**
 * Wrapper for {@link WrappedHBColumn} class. To be used in test cases only.
 */
public class WrappedHBColumnTC extends WrappedHBColumn {
    public WrappedHBColumnTC(Field field) {
        super(field);
    }
}

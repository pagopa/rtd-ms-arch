package eu.sia.meda.connector.hbase.exception;

import java.io.Serializable;

import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBTable;

public class DuplicateCodecFlagForRowKeyException extends IllegalArgumentException {
    public <R extends Serializable & Comparable<R>, T extends HBRecord<R>> DuplicateCodecFlagForRowKeyException(Class<T> clazz, String flagName) {
        super(String.format("The %s annotation on %s class has duplicate codec flags. See codec flag '%s'.", HBTable.class.getSimpleName(), clazz.getName(), flagName));
    }
}

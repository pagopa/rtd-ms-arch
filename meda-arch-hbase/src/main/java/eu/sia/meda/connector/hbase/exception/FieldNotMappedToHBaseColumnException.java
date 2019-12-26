package eu.sia.meda.connector.hbase.exception;

import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBColumnMultiVersion;
import eu.sia.meda.connector.hbase.annotation.HBRecord;

public class FieldNotMappedToHBaseColumnException extends IllegalArgumentException {
    public FieldNotMappedToHBaseColumnException(Class<? extends HBRecord> hbRecordClass, String fieldName) {
        super(String.format("Field %s.%s is not mapped to an HBase column (consider adding %s or %s annotation)", hbRecordClass.getSimpleName(), fieldName, HBColumn.class.getSimpleName(), HBColumnMultiVersion.class.getSimpleName()));
    }
}

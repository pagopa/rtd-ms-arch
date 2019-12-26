package eu.sia.meda.connector.hbase;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

import eu.sia.meda.connector.hbase.annotation.Flag;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBColumnMultiVersion;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.exception.BothHBColumnAnnotationsPresentException;
import eu.sia.meda.connector.hbase.exception.DuplicateCodecFlagForColumnException;
import eu.sia.meda.connector.hbase.exception.FieldNotMappedToHBaseColumnException;


/**
 * A wrapper class for {@link HBColumn} and {@link HBColumnMultiVersion} annotations (for internal use only)
 */
public class WrappedHBColumn {
    private final String family, column;
    private final boolean multiVersioned, singleVersioned;
    private final Class annotationClass;
    private final Map<String, String> codecFlags;
    private final Field field;

    public WrappedHBColumn(Field field) {
        this(field, false);
    }

    @SuppressWarnings("unchecked")
    public WrappedHBColumn(Field field, boolean throwExceptionIfNonHBColumn) {
        this.field = field;
        HBColumn hbColumn = field.getAnnotation(HBColumn.class);
        HBColumnMultiVersion hbColumnMultiVersion = field.getAnnotation(HBColumnMultiVersion.class);
        if (hbColumn != null && hbColumnMultiVersion != null) {
            throw new BothHBColumnAnnotationsPresentException(field);
        }
        if (hbColumn != null) {
            family = hbColumn.family();
            column = hbColumn.column();
            singleVersioned = true;
            multiVersioned = false;
            annotationClass = HBColumn.class;
            codecFlags = toMap(hbColumn.codecFlags());
        } else if (hbColumnMultiVersion != null) {
            family = hbColumnMultiVersion.family();
            column = hbColumnMultiVersion.column();
            singleVersioned = false;
            multiVersioned = true;
            annotationClass = HBColumnMultiVersion.class;
            codecFlags = toMap(hbColumnMultiVersion.codecFlags());
        } else {
            if (throwExceptionIfNonHBColumn) {
                throw new FieldNotMappedToHBaseColumnException((Class<HBRecord>) field.getDeclaringClass(), field.getName());
            }
            family = null;
            column = null;
            singleVersioned = false;
            multiVersioned = false;
            annotationClass = null;
            codecFlags = null;
        }
    }

    private Map<String, String> toMap(Flag[] codecFlags) {
        Map<String, String> flagsMap = new HashMap<>(codecFlags.length, 1.0f);
        for (Flag flag : codecFlags) {
            String previousValue = flagsMap.put(flag.name(), flag.value());
            if (previousValue != null) {
                throw new DuplicateCodecFlagForColumnException(field.getDeclaringClass(), field.getName(), annotationClass, flag.name());
            }
        }
        return flagsMap;
    }

    public String family() {
        return family;
    }

    public byte[] familyBytes() {
        return Bytes.toBytes(family);
    }

    public String column() {
        return column;
    }

    public byte[] columnBytes() {
        return Bytes.toBytes(column);
    }

    public Map<String, String> codecFlags() {
        return codecFlags;
    }

    public boolean isPresent() {
        return singleVersioned || multiVersioned;
    }

    public boolean isMultiVersioned() {
        return multiVersioned;
    }

    public boolean isSingleVersioned() {
        return singleVersioned;
    }

    public String getName() {
        return annotationClass.getName();
    }

    @Override
    public String toString() {
        return String.format("%s:%s", family, column);
    }
}

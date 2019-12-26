package eu.sia.meda.connector.hbase.test.entity;

import java.util.NavigableMap;
import java.util.TreeMap;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBColumnMultiVersion;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("unused")
@ToString
@EqualsAndHashCode
@HBTable(name = "counters", families = {@Family(name = "a", versions = 10)})
public class Counter implements HBRecord<String> {
    @HBRowKey
    private String key;

    @HBColumn(family = "a", column = "var")
    private Long var;

    @HBColumn(family = "a", column = "badvarI")
    public Integer badvarI = 2000;

    @HBColumnMultiVersion(family = "a", column = "value")
    private NavigableMap<Long, Long> value;

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        key = rowKey;
    }

    public Counter() {
        value = new TreeMap<>();
    }

    public Counter(String key) {
        this();
        this.key = key;
    }

    public Counter(String key, NavigableMap<Long, Long> value) {
        this(key);
        this.value = value;
    }

    public Long getVar() {
        return var;
    }

    public void setVar(Long var) {
        this.var = var;
    }

    public NavigableMap<Long, Long> getValue() {
        return value;
    }

    public void setValue(Long timestamp, Long value) {
        this.value.put(timestamp, value);
    }
}

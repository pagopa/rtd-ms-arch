package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;

@SuppressWarnings("unused")
@HBTable(name = "blah", families = {@Family(name = "main")})
public class UninstantiatableClass implements HBRecord<String> {
    @HBRowKey
    private Integer uid;
    @HBColumn(family = "main", column = "name")
    private String name;

    public UninstantiatableClass() {
        throw new RuntimeException("I'm a bad constructor");
    }

    @Override
    public String composeRowKey() {
        return uid.toString();
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.uid = Integer.valueOf(rowKey);
    }
}

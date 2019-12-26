package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;

@SuppressWarnings("unused")
@HBTable(name = "blah", families = {@Family(name = "a")})
public class ClassWithNoHBColumns implements HBRecord<String> {
    @HBRowKey
    protected String key = "key";

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.key = rowKey;
    }

    private Float f; //not adding @HBColumn here!
}

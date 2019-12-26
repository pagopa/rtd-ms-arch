package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;

@SuppressWarnings({"CanBeFinal", "unused"})
@HBTable(name = "blah", families = {@Family(name = "f")})
public class Singleton implements HBRecord<String> {
    private static Singleton ourInstance = new Singleton();

    @HBRowKey
    protected String key = "key";

    @HBColumn(family = "f", column = "c")
    String column;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
        column = "something";
    }

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.key = rowKey;
    }

}

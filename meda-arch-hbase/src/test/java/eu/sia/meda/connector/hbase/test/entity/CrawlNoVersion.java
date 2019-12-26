package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;
import lombok.ToString;

@HBTable(name = "crawls", families = {@Family(name = "a")})
@ToString
public class CrawlNoVersion implements HBRecord<String> {
    @HBRowKey
    String key;

    @HBColumn(family = "a", column = "f1")
    Double f1;

    public CrawlNoVersion() {

    }

    public CrawlNoVersion(String key) {
        this.key = key;
    }

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.key = rowKey;
    }

    public Double getF1() {
        return f1;
    }

    public CrawlNoVersion setF1(Double f1) {
        this.f1 = f1;
        return this;
    }
}

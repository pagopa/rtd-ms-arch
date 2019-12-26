package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.HBRecord;

abstract class AbstractHBRecordWithDummyStringRowKey implements HBRecord<String> {
    protected String key = "key";

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.key = rowKey;
    }
}

package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBTable;

@SuppressWarnings("unused")
@HBTable(name = "blah", families = {@Family(name = "f")})
public class ClassWithNoHBRowKeys extends AbstractHBRecordWithDummyStringRowKey {

    @HBColumn(family = "f", column = "c")
    private Float f;
}

package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.MappedSuperClass;

@MappedSuperClass
public class AbstractRecord {

    @HBColumn(family = "a", column = "created_at")
    protected Long createdAt;
}

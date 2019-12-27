package eu.sia.meda.connector.hbase.test.dao;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;

import eu.sia.meda.connector.hbase.HBaseConnector;
import eu.sia.meda.connector.hbase.test.entity.Counter;

public class CounterDAO extends HBaseConnector<Counter, String> {
    public CounterDAO(Connection connection) throws IOException {
        super(connection);
    }
}

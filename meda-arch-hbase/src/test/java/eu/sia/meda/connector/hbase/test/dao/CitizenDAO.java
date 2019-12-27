package eu.sia.meda.connector.hbase.test.dao;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import eu.sia.meda.connector.hbase.HBaseConnector;
import eu.sia.meda.connector.hbase.test.entity.Citizen;

public class CitizenDAO extends HBaseConnector<Citizen, String> {

    public CitizenDAO(Configuration conf) throws IOException {
        super(conf);
    }
}

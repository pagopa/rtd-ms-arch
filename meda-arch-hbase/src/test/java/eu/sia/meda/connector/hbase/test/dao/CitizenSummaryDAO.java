package eu.sia.meda.connector.hbase.test.dao;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import eu.sia.meda.connector.hbase.HBaseConnector;
import eu.sia.meda.connector.hbase.test.entity.CitizenSummary;

public class CitizenSummaryDAO extends HBaseConnector<CitizenSummary, String> {

    public CitizenSummaryDAO(Configuration conf) throws IOException {
        super(conf);
    }
}
package eu.sia.meda.connector.hbase.test.dao;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import eu.sia.meda.connector.hbase.HBaseConnector;
import eu.sia.meda.connector.hbase.test.entity.CrawlNoVersion;

public class CrawlNoVersionDAO extends HBaseConnector<CrawlNoVersion, String> {

    public CrawlNoVersionDAO(Configuration conf) throws IOException {
        super(conf);
    }
}

package eu.sia.meda.connector.hbase.test.dao;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import eu.sia.meda.connector.hbase.HBaseConnector;
import eu.sia.meda.connector.hbase.test.entity.CrawlNoVersion;

public class CrawlNoVersionDAO extends HBaseConnector<String, CrawlNoVersion> {

    public CrawlNoVersionDAO(Configuration conf) throws IOException {
        super(conf);
    }
}

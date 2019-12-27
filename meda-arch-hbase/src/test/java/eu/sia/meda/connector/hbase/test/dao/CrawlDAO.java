package eu.sia.meda.connector.hbase.test.dao;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import eu.sia.meda.connector.hbase.HBaseConnector;
import eu.sia.meda.connector.hbase.test.entity.Crawl;

public class CrawlDAO extends HBaseConnector<Crawl, String> {

    public CrawlDAO(Configuration conf) throws IOException {
        super(conf);
    }
}

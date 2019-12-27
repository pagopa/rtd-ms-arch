package eu.sia.meda.connector.hbase.test.dao;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import eu.sia.meda.connector.hbase.HBaseConnector;
import eu.sia.meda.connector.hbase.test.entity.Employee;

public class EmployeeDAO extends HBaseConnector<Employee, Long> {

    public EmployeeDAO(Configuration conf) throws IOException {
        super(conf);
    }
}

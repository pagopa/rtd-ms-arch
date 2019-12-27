package eu.sia.meda.connector.hbase.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.sia.meda.config.LoggerUtils;
import wiremock.org.apache.commons.lang3.StringUtils;

/**
 * The Class JPAConnectorConfig.
 */
@Configuration
@ConditionalOnProperty(
   prefix = "connectors",
   name = {"hbaseConfigurations.connection.zkQuorum"}
)
public class HBaseConnectorConfig {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(HBaseConnectorConfig.class);
   
   /** The Configuration Constant keys. */
   private static final String HBASE_ZK_QUORUM = "hbase.zookeeper.quorum";
   private static final String HBASE_ZK_CLIENT_PORT = "hbase.zookeeper.property.clientport";
   private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";
   
   /** The configurations. */
   @Autowired
   @Qualifier("HBaseConfiguration")
   private ArchHBaseConfigurationService configurations;

   /**
    * HBase configuration
    *
    * @return the HBase configuration
    */
   @Bean(
      name = {"hbaseConfiguration"}
   )
   public org.apache.hadoop.conf.Configuration hbaseConfiguration() {
	   ArchHBaseConfigurationService.HBaseConnection hbaseConnection = this.configurations.retrieveHBaseConnection(null);
      if (hbaseConnection == null) {
         throw new ExceptionInInitializerError();
      } else {
    	  //TODO check mocked flag and try to do an in-memory cluster
    	  org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
    	  if(StringUtils.isNotBlank(hbaseConnection.getZkQuorum())){configuration.set(HBASE_ZK_QUORUM, hbaseConnection.getZkQuorum());}
    	  if(StringUtils.isNotBlank(hbaseConnection.getZkClientPort())){configuration.set(HBASE_ZK_CLIENT_PORT, hbaseConnection.getZkClientPort());}
    	  if(StringUtils.isNotBlank(hbaseConnection.getZkZnodeParent())){configuration.set(HBASE_ZNODE_PARENT, hbaseConnection.getZkZnodeParent());}

          return configuration;
      }
   }
   
}

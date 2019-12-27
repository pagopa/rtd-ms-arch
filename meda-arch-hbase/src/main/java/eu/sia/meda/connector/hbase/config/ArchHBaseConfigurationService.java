package eu.sia.meda.connector.hbase.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.ConnectorConfiguration;
import lombok.Builder;
import lombok.Data;

/**
 * The Class ArchHBaseConfigurationService.
 */
@Service("HBaseConfiguration")
public class ArchHBaseConfigurationService {

	/** The Constant log. */
	private static final Logger log = LoggerUtils.getLogger(ArchHBaseConfigurationService.class);

	/** The Constant HBASE_CONNECTOR_TYPE. */
	public static final String HBASE_CONNECTOR_TYPE = "connectors.hbaseConfigurations";

	/** The Constant HBASE_CONNECTION. */
	public static final String HBASE_CONNECTION = "connectors.hbaseConfigurations.connection.";

	private static final String ZK_QUORUM_SUFFIX = "zkQuorum";
	private static final String ZK_CLIPORT_SUFFIX = "zkClientPort";
	private static final String ZK_ZNODEP_SUFFIX = "zkZnodeParent";
	
	private static final String PROP_MANAGER_ZK_QUORUM_KEY = HBASE_CONNECTION + ZK_QUORUM_SUFFIX;
	private static final String PROP_MANAGER_ZK_CLIPORT_KEY = HBASE_CONNECTION + ZK_CLIPORT_SUFFIX;
	private static final String PROP_MANAGER_ZK_ZNODEP_KEY = HBASE_CONNECTION + ZK_ZNODEP_SUFFIX;
	private static final String PROP_MANAGER_MOCKED_KEY = HBASE_CONNECTION + ConnectorConfiguration.MOCKED_KEY;
	
	/** The properties manager. */
	@Autowired
	private PropertiesManager propertiesManager;

	/**
	 * Retrieve HBase connection.
	 *
	 * @param className the class name
	 * @return the arch HBase configuration service.
	 */
	public ArchHBaseConfigurationService.HBaseConnection retrieveHBaseConnection(String className) {

		if (this.propertiesManager.containsProperty(PROP_MANAGER_ZK_QUORUM_KEY)) {

			ArchHBaseConfigurationService.HBaseConnection connection = new ArchHBaseConfigurationService.HBaseConnection.HBaseConnectionBuilder()
					.mocked((Boolean) this.propertiesManager.get(PROP_MANAGER_MOCKED_KEY, Boolean.class, false))
					.zkQuorum((String) this.propertiesManager.get(PROP_MANAGER_ZK_QUORUM_KEY, String.class))
					.zkClientPort((String) this.propertiesManager.get(PROP_MANAGER_ZK_CLIPORT_KEY, String.class))
					.zkZnodeParent((String) this.propertiesManager.get(PROP_MANAGER_ZK_ZNODEP_KEY, String.class))
					.build();

			return connection;

		} else {
			if (log.isWarnEnabled()) {
				log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
			}
			return null;
		}
	}

	/**
	 * The Class HBaseConnection contains the property for configure connection to
	 * HBASE.
	 */
	@Builder
	@Data
	public static class HBaseConnection implements ConnectorConfiguration {

		/** The zookeeper quorum. */
		private String zkQuorum;

		/** The zookeeper client port. */
		private String zkClientPort;

		/** The znode parent name (configured in hbase-site.xml). */
		private String zkZnodeParent;

		@Builder.Default
		private Boolean mocked = Boolean.FALSE;

		@Override
		public Boolean isMocked() {
			return mocked;
		}

		@Override
		public void setMocked(Boolean mocked) {
			this.mocked = mocked;
		}

	}

}

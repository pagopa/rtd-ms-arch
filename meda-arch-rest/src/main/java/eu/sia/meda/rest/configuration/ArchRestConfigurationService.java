package eu.sia.meda.rest.configuration;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.config.http.HttpConnectionPoolConfiguration;
import eu.sia.meda.config.http.IHttpConnectionPoolConfigurationAware;
import eu.sia.meda.connector.meda.ArchMedaInternalConnectorConfigurationService;
import eu.sia.meda.connector.medacore.ArchMedaCoreConnectorConfigurationService;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.AuditableConnectorConfiguration;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ArchRestConfigurationService.
 */
@Service("RestConfiguration")
public class ArchRestConfigurationService {
   
   /** The Constant CONNECTOR_TYPE. */
   public static final String CONNECTOR_TYPE = "connectors.restConfigurations";
   
   /** The Constant DATAPOWER_CONNECTOR_TYPE. */
   public static final String DATAPOWER_CONNECTOR_TYPE = "connectors.restDataPowerConfigurations";
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(ArchRestConfigurationService.class);
   
   /** The properties manager. */
   @Autowired
   private PropertiesManager propertiesManager;

   /**
    * Retrieve rest configuration.
    *
    * @param className the class name
    * @return the arch rest configuration service. rest configuration
    */
   public ArchRestConfigurationService.RestConfiguration retrieveRestConfiguration(String className) {
      ArchRestConfigurationService.RestConfiguration dataPowerRestConfig;
      if (this.propertiesManager.containsConnectorProperty(CONNECTOR_TYPE, className, "url")) {
         dataPowerRestConfig = new ArchRestConfigurationService.RestConfiguration();
         dataPowerRestConfig.setUrl((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "url", String.class));
         dataPowerRestConfig.setTimeout((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "timeout", Integer.TYPE));
         dataPowerRestConfig.setMocked((Boolean)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "mocked", Boolean.class, false));
         dataPowerRestConfig.setPath((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "path", String.class));
         dataPowerRestConfig.setRandomMock((Boolean)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "randomMock", Boolean.class));
         dataPowerRestConfig.setFiles(this.propertiesManager.getConnectorPropertyList(CONNECTOR_TYPE, className, "files", String.class));
         dataPowerRestConfig.setEnabledTracing((Boolean)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "enabledTracing", Boolean.class));
         dataPowerRestConfig.setAuditEnabled((Boolean)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "auditEnabled", Boolean.class));
         dataPowerRestConfig.setCodService((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "codService", String.class));
         dataPowerRestConfig.setConnectionPoolConfiguration(HttpConnectionPoolConfiguration.loadFromProperties(this.propertiesManager, CONNECTOR_TYPE, className));
         return dataPowerRestConfig;
      } else if (this.propertiesManager.containsConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "url")) {
         dataPowerRestConfig = new ArchRestConfigurationService.RestConfiguration();
         dataPowerRestConfig.setUrl((String)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "url", String.class));
         dataPowerRestConfig.setTimeout((Integer)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "timeout", Integer.TYPE));
         dataPowerRestConfig.setMocked((Boolean)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "mocked", Boolean.class, false));
         dataPowerRestConfig.setPath((String)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "path", String.class));
         dataPowerRestConfig.setRandomMock((Boolean)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "randomMock", Boolean.class));
         dataPowerRestConfig.setFiles(this.propertiesManager.getConnectorPropertyList(DATAPOWER_CONNECTOR_TYPE, className, "files", String.class));
         dataPowerRestConfig.setEnabledTracing((Boolean)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "enabledTracing", Boolean.class));
         dataPowerRestConfig.setAuditEnabled((Boolean)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "auditEnabled", Boolean.class));
         dataPowerRestConfig.setCodService((String)this.propertiesManager.getConnectorProperty(DATAPOWER_CONNECTOR_TYPE, className, "codService", String.class));
         dataPowerRestConfig.setConnectionPoolConfiguration(HttpConnectionPoolConfiguration.loadFromProperties(this.propertiesManager, DATAPOWER_CONNECTOR_TYPE, className));
         return dataPowerRestConfig;
      } else {
    	 if(log.isWarnEnabled()) {
    		 log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
    	 }
         return null;
      }
   }

   /**
    * The Class RestConfiguration.
    */
   public static class RestConfiguration implements IHttpConnectionPoolConfigurationAware, AuditableConnectorConfiguration {
      
      /** The url. */
      private String url;
      
      /** The timeout. */
      private int timeout = 0;
      
      /** The mocked. */
      private Boolean mocked = false;
      
      /** The random mock. */
      private boolean randomMock = true;
      
      /** The path. */
      private String path;
      
      /** The files. */
      private List<String> files;
      
      /** The connection pool. */
      private HttpConnectionPoolConfiguration connectionPool = null;
      
      /** The enabled tracing. */
      private Boolean enabledTracing;
      
      /** The audit enabled. */
      private Boolean auditEnabled;
      
      /** The cod service. */
      private String codService;

      /**
       * Instantiates a new rest configuration.
       */
      public RestConfiguration() {
      }

      /**
       * Instantiates a new rest configuration.
       *
       * @param configuration the configuration
       */
      public RestConfiguration(ArchMedaInternalConnectorConfigurationService.MedaInternalConfiguration configuration) {
         this.url = configuration.getUrl();
         this.mocked = configuration.isMocked();
         this.randomMock = configuration.isRandomMock();
         this.path = configuration.getPath();
         this.files = configuration.getFiles();
         this.timeout = configuration.getTimeout();
         this.connectionPool = configuration.getConnectionPoolConfiguration();
         this.enabledTracing = configuration.isEnabledTracing();
         this.auditEnabled = configuration.isAuditEnabled();
         this.codService = configuration.getCodService();
      }

      /**
       * Instantiates a new rest configuration.
       *
       * @param configuration the configuration
       */
      public RestConfiguration(ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration configuration) {
         this.url = configuration.getUrl();
         this.mocked = configuration.isMocked();
         this.path = configuration.getPath();
         this.files = configuration.getFiles();
         this.timeout = configuration.getTimeout();
         this.connectionPool = configuration.getConnectionPoolConfiguration();
      }

      /**
       * Gets the url.
       *
       * @return the url
       */
      public String getUrl() {
         return this.url;
      }

      /**
       * Sets the url.
       *
       * @param url the new url
       */
      public void setUrl(String url) {
         this.url = url;
      }

      /**
       * Checks if is mocked.
       *
       * @return the boolean
       */
      public Boolean isMocked() {
         return this.mocked;
      }

      /**
       * Sets the mocked.
       *
       * @param mocked the new mocked
       */
      public void setMocked(Boolean mocked) {
         this.mocked = mocked != null ? mocked : Boolean.FALSE;
      }

      /**
       * Gets the path.
       *
       * @return the path
       */
      public String getPath() {
         return this.path;
      }

      /**
       * Sets the path.
       *
       * @param path the new path
       */
      public void setPath(String path) {
         this.path = path;
      }

      /**
       * Gets the files.
       *
       * @return the files
       */
      public List<String> getFiles() {
         return this.files;
      }

      /**
       * Sets the files.
       *
       * @param files the new files
       */
      public void setFiles(List<String> files) {
         this.files = files;
      }

      /**
       * Gets the timeout.
       *
       * @return the timeout
       */
      public int getTimeout() {
         return this.timeout;
      }

      /**
       * Sets the timeout.
       *
       * @param timeout the new timeout
       */
      public void setTimeout(Integer timeout) {
         this.timeout = timeout != null ? timeout : 0;
      }

      /**
       * Checks if is random mock.
       *
       * @return true, if is random mock
       */
      public boolean isRandomMock() {
         return this.randomMock;
      }

      /**
       * Sets the random mock.
       *
       * @param randomMock the new random mock
       */
      public void setRandomMock(Boolean randomMock) {
         this.randomMock = randomMock != null ? randomMock : Boolean.TRUE;
      }

      /**
       * Checks if is audit enabled.
       *
       * @return the boolean
       */
      public Boolean isAuditEnabled() {
         return this.auditEnabled;
      }

      /**
       * Sets the audit enabled.
       *
       * @param auditEnabled the new audit enabled
       */
      public void setAuditEnabled(Boolean auditEnabled) {
         this.auditEnabled = auditEnabled;
      }

      /**
       * Gets the cod service.
       *
       * @return the cod service
       */
      public String getCodService() {
         return this.codService;
      }

      /**
       * Sets the cod service.
       *
       * @param codService the new cod service
       */
      public void setCodService(String codService) {
         this.codService = codService;
      }

      /**
       * Gets the connection pool configuration.
       *
       * @return the connection pool configuration
       */
      public HttpConnectionPoolConfiguration getConnectionPoolConfiguration() {
         return this.connectionPool;
      }

      /**
       * Sets the connection pool configuration.
       *
       * @param connectionPool the new connection pool configuration
       */
      public void setConnectionPoolConfiguration(HttpConnectionPoolConfiguration connectionPool) {
         this.connectionPool = connectionPool;
      }

      /**
       * Checks for connection pool configuration.
       *
       * @return true, if successful
       */
      public boolean hasConnectionPoolConfiguration() {
         return this.connectionPool != null;
      }

      /**
       * Checks if is enabled tracing.
       *
       * @return the boolean
       */
      public Boolean isEnabledTracing() {
         return this.enabledTracing;
      }

      /**
       * Sets the enabled tracing.
       *
       * @param enabledTracing the new enabled tracing
       */
      public void setEnabledTracing(Boolean enabledTracing) {
         this.enabledTracing = enabledTracing;
      }
   }
}

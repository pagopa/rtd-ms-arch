/*
 * 
 */
package eu.sia.meda.connector.jdbc.configuration;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.TraceableConnectorConfiguration;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ArchJDBCConfigurationService.
 */
@Service("JDBCConfiguration")
public class ArchJDBCConfigurationService {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(ArchJDBCConfigurationService.class);
   
   /** The Constant CONNECTOR_TYPE_KEY. */
   private static final String CONNECTOR_TYPE_KEY = "connectors.jdbcConfigurations";
   
   /** The Constant URI_KEY. */
   private static final String URI_KEY = "uri";
   
   /** The Constant DRIVER_CLASS_NAME_KEY. */
   private static final String DRIVER_CLASS_NAME_KEY = "driver-class-name";
   
   /** The Constant USERNAME_KEY. */
   private static final String USERNAME_KEY = "username";
   
   /** The Constant SECRET_KEY. */
   private static final String SECRET_KEY = "password";
   
   /** The Constant CONNECTION_POOL_SIZE_KEY. */
   private static final String CONNECTION_POOL_SIZE_KEY = "connectionPoolSize";
   
   /** The Constant TIMEOUT_KEY. */
   private static final String TIMEOUT_KEY = "timeout";
   
   /** The Constant PATH_KEY. */
   private static final String PATH_KEY = "path";
   
   /** The Constant FILES_KEY. */
   private static final String FILES_KEY = "files";
   
   /** The Constant MOCKED_KEY. */
   private static final String MOCKED_KEY = "mocked";
   
   /** The Constant ENABLED_TRACING_KEY. */
   private static final String ENABLED_TRACING_KEY = "enabledTracing";
   
   /** The properties manager. */
   @Autowired
   private PropertiesManager propertiesManager;

   /**
    * Retrieve JDBC configuration.
    *
    * @param className the class name
    * @return the arch JDBC configuration service. JDBC configuration
    */
   public ArchJDBCConfigurationService.JDBCConfiguration retrieveJDBCConfiguration(String className) {
      ArchJDBCConfigurationService.JDBCConfiguration jdbcConfig = new ArchJDBCConfigurationService.JDBCConfiguration();
      if (this.propertiesManager.containsConnectorProperty(CONNECTOR_TYPE_KEY, className, "uri")) {
         jdbcConfig.setUri((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, URI_KEY, String.class));
         jdbcConfig.setDriverClassName((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, DRIVER_CLASS_NAME_KEY, String.class));
         jdbcConfig.setUsername((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, USERNAME_KEY, String.class));
         jdbcConfig.setPassword((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, SECRET_KEY, String.class));
         jdbcConfig.setConnectionPoolSize((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, CONNECTION_POOL_SIZE_KEY, Integer.class));
         jdbcConfig.setTimeout((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, TIMEOUT_KEY, Integer.class));
         jdbcConfig.setMocked((Boolean)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, MOCKED_KEY, Boolean.class, false));
         jdbcConfig.setPath((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, PATH_KEY, String.class));
         jdbcConfig.setFiles(this.propertiesManager.getConnectorPropertyList(CONNECTOR_TYPE_KEY, className, FILES_KEY, String.class));
         jdbcConfig.setEnabledTracing((Boolean)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE_KEY, className, ENABLED_TRACING_KEY, Boolean.class));
         return jdbcConfig;
      } else {
    	  if(log.isWarnEnabled()) {
    		  log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
    	  }
         return null;
      }
   }

   /**
    * The Class JDBCConfiguration.
    */
   public static class JDBCConfiguration implements TraceableConnectorConfiguration {
      
      /** The uri. */
      private String uri;
      
      /** The driver class name. */
      private String driverClassName;
      
      /** The username. */
      private String username;
      
      /** The password. */
      private String password;
      
      /** The connection pool size. */
      private int connectionPoolSize;
      
      /** The timeout. */
      private int timeout;
      
      /** The mocked. */
      private Boolean mocked;
      
      /** The path. */
      private String path;
      
      /** The files. */
      private List<String> files;
      
      /** The enabled tracing. */
      private Boolean enabledTracing;

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
         this.mocked = mocked;
      }

      /**
       * Gets the uri.
       *
       * @return the uri
       */
      public String getUri() {
         return this.uri;
      }

      /**
       * Sets the uri.
       *
       * @param uri the new uri
       */
      public void setUri(String uri) {
         this.uri = uri;
      }

      /**
       * Gets the driver class name.
       *
       * @return the driver class name
       */
      public String getDriverClassName() {
         return this.driverClassName;
      }

      /**
       * Sets the driver class name.
       *
       * @param driverClassName the new driver class name
       */
      public void setDriverClassName(String driverClassName) {
         this.driverClassName = driverClassName;
      }

      /**
       * Gets the username.
       *
       * @return the username
       */
      public String getUsername() {
         return this.username;
      }

      /**
       * Sets the username.
       *
       * @param username the new username
       */
      public void setUsername(String username) {
         this.username = username;
      }

      /**
       * Gets the password.
       *
       * @return the password
       */
      public String getPassword() {
         return this.password;
      }

      /**
       * Sets the password.
       *
       * @param password the new password
       */
      public void setPassword(String password) {
         this.password = password;
      }

      /**
       * Gets the connection pool size.
       *
       * @return the connection pool size
       */
      public int getConnectionPoolSize() {
         return this.connectionPoolSize;
      }

      /**
       * Sets the connection pool size.
       *
       * @param connectionPoolSize the new connection pool size
       */
      public void setConnectionPoolSize(int connectionPoolSize) {
         this.connectionPoolSize = connectionPoolSize;
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
      public void setTimeout(int timeout) {
         this.timeout = timeout;
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

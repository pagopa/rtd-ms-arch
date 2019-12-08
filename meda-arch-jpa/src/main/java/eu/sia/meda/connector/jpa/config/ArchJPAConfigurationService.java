/*
 * 
 */
package eu.sia.meda.connector.jpa.config;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.ConnectorConfiguration;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ArchJPAConfigurationService.
 */
@Service("JPAConfiguration")
public class ArchJPAConfigurationService {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(ArchJPAConfigurationService.class);
   
   /** The Constant JPA_CONNECTOR_TYPE. */
   public static final String JPA_CONNECTOR_TYPE = "connectors.jpaConfigurations";
   
   /** The Constant JPA_CONNECTION. */
   public static final String JPA_CONNECTION = "connectors.jpaConfigurations.connection";
   
   /** The Constant JPA_REPO. */
   public static final String JPA_REPO = "connectors.jpaConfigurations.repositories";
   
   /** The properties manager. */
   @Autowired
   private PropertiesManager propertiesManager;

   /**
    * Retrieve JPA connection.
    *
    * @param className the class name
    * @return the arch JPA configuration service. JPA connection
    */
   public ArchJPAConfigurationService.JPAConnection retrieveJPAConnection(String className) {
      ArchJPAConfigurationService.JPAConnection connection = new ArchJPAConfigurationService.JPAConnection();
      if (this.propertiesManager.containsProperty("connectors.jpaConfigurations.connection.url")) {
         connection.setUrl((String)this.propertiesManager.get("connectors.jpaConfigurations.connection.url", String.class));
         connection.setDriverClassName((String)this.propertiesManager.get("connectors.jpaConfigurations.connection.driver-class-name", String.class));
         connection.setUsername((String)this.propertiesManager.get("connectors.jpaConfigurations.connection.username", String.class));
         connection.setPassword((String)this.propertiesManager.get("connectors.jpaConfigurations.connection.password", String.class));
         connection.setConnectionPoolSize((Integer)this.propertiesManager.get("connectors.jpaConfigurations.connection.connectionPoolSize", Integer.TYPE));
         connection.setTimeout((Integer)this.propertiesManager.get("connectors.jpaConfigurations.connection.timeout", Integer.TYPE));
         connection.setHibernateDialect((String)this.propertiesManager.get("connectors.jpaConfigurations.connection.hibernateDialect", String.class));
         connection.setHbm2ddl((String)this.propertiesManager.get("connectors.jpaConfigurations.connection.hbm2ddl", String.class));
         connection.setShowSql((Boolean)this.propertiesManager.get("connectors.jpaConfigurations.connection.show-sql", Boolean.class, true));
         connection.setBatchSize((Integer)this.propertiesManager.get("connectors.jpaConfigurations.connection.batch-size", Integer.class, 0));
         connection.setOrderInserts((Boolean)this.propertiesManager.get("connectors.jpaConfigurations.connection.order-inserts", Boolean.class, false));
         connection.setOrderUpdates((Boolean)this.propertiesManager.get("connectors.jpaConfigurations.connection.order-updates", Boolean.class, false));
         connection.setBatchVersionedData((Boolean)this.propertiesManager.get("connectors.jpaConfigurations.connection.batch-versioned-data", Boolean.class, false));
         connection.setNewGeneratorMappings((Boolean)this.propertiesManager.get("connectors.jpaConfigurations.connection.new-generator-mappings", Boolean.class, true));
         connection.setMocked((Boolean)this.propertiesManager.get("connectors.jpaConfigurations.connection.mocked", Boolean.class, false));
         connection.setPath((String)this.propertiesManager.get("connectors.jpaConfigurations.connection.path", String.class));
         connection.setFiles(this.propertiesManager.getAsList("connectors.jpaConfigurations.connection.files", String.class));
         connection.setEntityPackages(this.propertiesManager.getAsList("connectors.jpaConfigurations.connection.entityPackages", String.class));
         return connection;
      } else {
    	 if(log.isWarnEnabled()) {
    		 log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
    	 }
         return null;
      }
   }

   /**
    * The Class JPAConnection.
    */
   public static class JPAConnection implements ConnectorConfiguration {
      
      /** The url. */
      private String url;
      
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
      
      /** The hibernate dialect. */
      private String hibernateDialect;
      
      /** The hbm 2 ddl. */
      private String hbm2ddl;
      
      /** The show sql. */
      private boolean showSql;
      
      /** The batch size. */
      private int batchSize;
      
      /** The order inserts. */
      private boolean orderInserts;
      
      /** The order updates. */
      private boolean orderUpdates;
      
      /** The batch versioned data. */
      private boolean batchVersionedData;
      
      /** The new generator mappings. */
      private boolean newGeneratorMappings;
      
      /** The mocked. */
      private Boolean mocked;
      
      /** The path. */
      private String path;
      
      /** The files. */
      private List<String> files;
      
      /** The entity packages. */
      private List<String> entityPackages;

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
       * Gets the hibernate dialect.
       *
       * @return the hibernate dialect
       */
      public String getHibernateDialect() {
         return this.hibernateDialect;
      }

      /**
       * Sets the hibernate dialect.
       *
       * @param hibernateDialect the new hibernate dialect
       */
      public void setHibernateDialect(String hibernateDialect) {
         this.hibernateDialect = hibernateDialect;
      }

      /**
       * Gets the hbm 2 ddl.
       *
       * @return the hbm 2 ddl
       */
      public String getHbm2ddl() {
         return this.hbm2ddl;
      }

      /**
       * Sets the hbm 2 ddl.
       *
       * @param hbm2ddl the new hbm 2 ddl
       */
      public void setHbm2ddl(String hbm2ddl) {
         this.hbm2ddl = hbm2ddl;
      }

      /**
       * Checks if is show sql.
       *
       * @return true, if is show sql
       */
      public boolean isShowSql() {
         return this.showSql;
      }

      /**
       * Sets the show sql.
       *
       * @param showSql the new show sql
       */
      public void setShowSql(boolean showSql) {
         this.showSql = showSql;
      }

      /**
       * Gets the batch size.
       *
       * @return the batch size
       */
      public int getBatchSize() {
         return this.batchSize;
      }

      /**
       * Sets the batch size.
       *
       * @param batchSize the new batch size
       */
      public void setBatchSize(int batchSize) {
         this.batchSize = batchSize;
      }

      /**
       * Checks if is order inserts.
       *
       * @return true, if is order inserts
       */
      public boolean isOrderInserts() {
         return this.orderInserts;
      }

      /**
       * Sets the order inserts.
       *
       * @param orderInserts the new order inserts
       */
      public void setOrderInserts(boolean orderInserts) {
         this.orderInserts = orderInserts;
      }

      /**
       * Checks if is order updates.
       *
       * @return true, if is order updates
       */
      public boolean isOrderUpdates() {
         return this.orderUpdates;
      }

      /**
       * Sets the order updates.
       *
       * @param orderUpdates the new order updates
       */
      public void setOrderUpdates(boolean orderUpdates) {
         this.orderUpdates = orderUpdates;
      }

      /**
       * Checks if is batch versioned data.
       *
       * @return true, if is batch versioned data
       */
      public boolean isBatchVersionedData() {
         return this.batchVersionedData;
      }

      /**
       * Sets the batch versioned data.
       *
       * @param batchVersionedData the new batch versioned data
       */
      public void setBatchVersionedData(boolean batchVersionedData) {
         this.batchVersionedData = batchVersionedData;
      }

      /**
       * Checks if is new generator mappings.
       *
       * @return true, if is new generator mappings
       */
      public boolean isNewGeneratorMappings() {
         return this.newGeneratorMappings;
      }

      /**
       * Sets the new generator mappings.
       *
       * @param newGeneratorMappings the new new generator mappings
       */
      public void setNewGeneratorMappings(boolean newGeneratorMappings) {
         this.newGeneratorMappings = newGeneratorMappings;
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
       * Gets the entity packages.
       *
       * @return the entity packages
       */
      public List<String> getEntityPackages() {
         return this.entityPackages;
      }

      /**
       * Sets the entity packages.
       *
       * @param entityPackages the new entity packages
       */
      public void setEntityPackages(List<String> entityPackages) {
         this.entityPackages = entityPackages;
      }
   }
}

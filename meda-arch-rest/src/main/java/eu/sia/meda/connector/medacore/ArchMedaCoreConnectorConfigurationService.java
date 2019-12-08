package eu.sia.meda.connector.medacore;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.config.http.HttpConnectionPoolConfiguration;
import eu.sia.meda.config.http.IHttpConnectionPoolConfigurationAware;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.rest.configuration.ArchRestConfigurationService;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ArchMedaCoreConnectorConfigurationService.
 */
@Service
public class ArchMedaCoreConnectorConfigurationService {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(ArchMedaCoreConnectorConfigurationService.class);
   
   /** The Constant CONNECTOR_TYPE. */
   public static final String CONNECTOR_TYPE = "connectors.medaCoreConfigurations";
   
   /** The properties manager. */
   @Autowired
   private PropertiesManager propertiesManager;

   /**
    * Retrieve rest configuration.
    *
    * @param className the class name
    * @return the arch meda core connector configuration service. meda core configuration
    */
   public ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration retrieveRestConfiguration(String className) {
      ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration config = new ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration();
      if (this.propertiesManager.containsConnectorProperty(CONNECTOR_TYPE, className, "url")) {
         config.setUrl((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "url", String.class));
         config.setTimeout((Integer)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "timeout", Integer.TYPE));
         config.setPath((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "path", String.class));
         config.setFiles(this.propertiesManager.getConnectorPropertyList(CONNECTOR_TYPE, className, "files", String.class));
         config.setClient((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "clientId", String.class));
         config.setClientSecret((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "clientSecret", String.class));
         config.setOauthUrl((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "oauthUrl", String.class));
         config.setScope((String)this.propertiesManager.getConnectorProperty(CONNECTOR_TYPE, className, "scope", String.class));
         config.setConnectionPoolConfiguration(HttpConnectionPoolConfiguration.loadFromProperties(this.propertiesManager, CONNECTOR_TYPE, className));
         return config;
      } else {
    	 if(log.isWarnEnabled()) {
    		 log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
    	 }
         return null;
      }
   }

   /**
    * The Class MedaCoreConfiguration.
    */
   public static class MedaCoreConfiguration extends ArchRestConfigurationService.RestConfiguration implements IHttpConnectionPoolConfigurationAware {
      
      /** The url. */
      private String url;
      
      /** The timeout. */
      private int timeout = 0;
      
      /** The mocked. */
      private boolean mocked = false;
      
      /** The path. */
      private String path;
      
      /** The files. */
      private List<String> files;
      
      /** The connection pool. */
      private HttpConnectionPoolConfiguration connectionPool = null;
      
      /** The client. */
      private String client;
      
      /** The client secret. */
      private String clientSecret;
      
      /** The oauth url. */
      private String oauthUrl;
      
      /** The scope. */
      private String scope;

      /**
       * Gets the url.
       *
       * @return the url
       */
      @Override
      public String getUrl() {
         return this.url;
      }

      /**
       * Sets the url.
       *
       * @param url the new url
       */
      @Override
      public void setUrl(String url) {
         this.url = url;
      }

      /**
       * Checks if is mocked.
       *
       * @return the boolean
       */
      @Override
      public Boolean isMocked() {
         return this.mocked;
      }
      
      /**
       * Sets the mocked.
       *
       * @param mocked the new mocked
       */
      @Override
      public void setMocked(Boolean mocked) {
         this.mocked = mocked != null ? mocked : Boolean.FALSE;
      }

      /**
       * Gets the path.
       *
       * @return the path
       */
      @Override
      public String getPath() {
         return this.path;
      }

      /**
       * Sets the path.
       *
       * @param path the new path
       */
      @Override
      public void setPath(String path) {
         this.path = path;
      }

      /**
       * Gets the files.
       *
       * @return the files
       */
      @Override
      public List<String> getFiles() {
         return this.files;
      }

      /**
       * Sets the files.
       *
       * @param files the new files
       */
      @Override
      public void setFiles(List<String> files) {
         this.files = files;
      }

      /**
       * Gets the timeout.
       *
       * @return the timeout
       */
      @Override
      public int getTimeout() {
         return this.timeout;
      }

      /**
       * Sets the timeout.
       *
       * @param timeout the new timeout
       */
      @Override
      public void setTimeout(Integer timeout) {
         this.timeout = timeout != null ? timeout : 0;
      }

      /**
       * Gets the connection pool configuration.
       *
       * @return the connection pool configuration
       */
      @Override
      public HttpConnectionPoolConfiguration getConnectionPoolConfiguration() {
         return this.connectionPool;
      }

      /**
       * Sets the connection pool configuration.
       *
       * @param connectionPool the new connection pool configuration
       */
      @Override
      public void setConnectionPoolConfiguration(HttpConnectionPoolConfiguration connectionPool) {
         this.connectionPool = connectionPool;
      }

      /**
       * Checks for connection pool configuration.
       *
       * @return true, if successful
       */
      @Override
      public boolean hasConnectionPoolConfiguration() {
         return this.connectionPool != null;
      }

      /**
       * Gets the client.
       *
       * @return the client
       */
      public String getClient() {
         return this.client;
      }

      /**
       * Sets the client.
       *
       * @param client the new client
       */
      public void setClient(String client) {
         this.client = client;
      }

      /**
       * Gets the client secret.
       *
       * @return the client secret
       */
      public String getClientSecret() {
         return this.clientSecret;
      }

      /**
       * Sets the client secret.
       *
       * @param clientSecret the new client secret
       */
      public void setClientSecret(String clientSecret) {
         this.clientSecret = StringUtils.isEmpty(clientSecret) ? "" : clientSecret;
      }

      /**
       * Gets the oauth url.
       *
       * @return the oauth url
       */
      public String getOauthUrl() {
         return this.oauthUrl;
      }

      /**
       * Sets the oauth url.
       *
       * @param oauthUrl the new oauth url
       */
      public void setOauthUrl(String oauthUrl) {
         this.oauthUrl = StringUtils.isEmpty(oauthUrl) ? "" : oauthUrl;
      }

      /**
       * Gets the scope.
       *
       * @return the scope
       */
      public String getScope() {
         return this.scope;
      }

      /**
       * Sets the scope.
       *
       * @param scope the new scope
       */
      public void setScope(String scope) {
         this.scope = scope;
      }
   }
}

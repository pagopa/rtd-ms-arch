package eu.sia.meda.config.http;

import eu.sia.meda.core.properties.PropertiesManager;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * The Class HttpConnectionPoolConfiguration.
 */
@Configuration
public class HttpConnectionPoolConfiguration {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(HttpConnectionPoolConfiguration.class);
   
   /** The Constant DEFAULT_PREFIX. */
   public static final String DEFAULT_PREFIX = "connectionPool";
   
   /** The read timeout. */
   private Integer readTimeout;
   
   /** The connect timeout. */
   private Integer connectTimeout;
   
   /** The connect request timeout. */
   private Integer connectRequestTimeout;
   
   /** The idle timeout. */
   private Integer idleTimeout;
   
   /** The connection sweeper interval. */
   private Integer connectionSweeperInterval;
   
   /** The max connections. */
   private Integer maxConnections;
   
   /** The enabled. */
   private Boolean enabled;
   
   /** The connector class name. */
   private String connectorClassName;

   /**
    * Load from properties.
    *
    * @param propertiesManager the properties manager
    * @param connectorType the connector type
    * @param className the class name
    * @return the http connection pool configuration
    */
   public static HttpConnectionPoolConfiguration loadFromProperties(PropertiesManager propertiesManager, String connectorType, String className) {
      return loadFromProperties(propertiesManager, connectorType, className, DEFAULT_PREFIX);
   }

   /**
    * Load from properties.
    *
    * @param propertiesManager the properties manager
    * @param connectorType the connector type
    * @param className the class name
    * @param prefix the prefix
    * @return the http connection pool configuration
    */
   public static HttpConnectionPoolConfiguration loadFromProperties(PropertiesManager propertiesManager, String connectorType, String className, String prefix) {
      HttpConnectionPoolConfiguration configuration = new HttpConnectionPoolConfiguration();

      try {
         boolean isDefault = true;
         Field[] var6 = HttpConnectionPoolConfiguration.class.getDeclaredFields();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Field field = var6[var8];
            if (!Modifier.isStatic(field.getModifiers())) {
               Object value = propertiesManager.getConnectorProperty(connectorType, className, prefix + "." + field.getName(), field.getType());
               if (value != null) {
                  field.set(configuration, value);
                  isDefault = false;
               }
            }
         }

         configuration.connectorClassName = className;
         if (isDefault) {
            configuration = makeDefaultConfig(className);
         } else {
            if (configuration.getEnabled() != null && !configuration.getEnabled()) {
               return null;
            }

            configuration.setEnabled(true);
         }

         Integer connectorTimeout = propertiesManager.getConnectorProperty(connectorType, className, "timeout", Integer.class);
         if (connectorTimeout != null && (configuration.getReadTimeout() == null || isDefault)) {
            configuration.setReadTimeout(connectorTimeout);
         }

         return configuration;
      } catch (IllegalAccessException var11) {
         logger.error("Error while trying to populate HTTP connection pool configuration from properties: ",  var11);
         return null;
      }
   }

   /**
    * Make default config.
    *
    * @return the http connection pool configuration
    */
   public static HttpConnectionPoolConfiguration makeDefaultConfig() {
      return makeDefaultConfig(null);
   }

   /**
    * Make default config.
    *
    * @param className the class name
    * @return the http connection pool configuration
    */
   public static HttpConnectionPoolConfiguration makeDefaultConfig(String className) {
      HttpConnectionPoolConfiguration configuration = new HttpConnectionPoolConfiguration();
      configuration.setReadTimeout(5000);
      configuration.setConnectTimeout(5000);
      configuration.setConnectRequestTimeout(5000);
      configuration.setIdleTimeout(60000);
      configuration.setConnectionSweeperInterval(5000);
      configuration.setMaxConnections(20);
      configuration.setEnabled(true);
      configuration.setConnectorClassName(className);
      return configuration;
   }

   /**
    * Gets the read timeout.
    *
    * @return the read timeout
    */
   public Integer getReadTimeout() {
      return this.readTimeout;
   }

   /**
    * Sets the read timeout.
    *
    * @param readTimeout the new read timeout
    */
   public void setReadTimeout(Integer readTimeout) {
      this.readTimeout = readTimeout;
   }

   /**
    * Gets the connect timeout.
    *
    * @return the connect timeout
    */
   public Integer getConnectTimeout() {
      return this.connectTimeout;
   }

   /**
    * Sets the connect timeout.
    *
    * @param connectTimeout the new connect timeout
    */
   public void setConnectTimeout(Integer connectTimeout) {
      this.connectTimeout = connectTimeout;
   }

   /**
    * Gets the connect request timeout.
    *
    * @return the connect request timeout
    */
   public Integer getConnectRequestTimeout() {
      return this.connectRequestTimeout;
   }

   /**
    * Sets the connect request timeout.
    *
    * @param connectRequestTimeout the new connect request timeout
    */
   public void setConnectRequestTimeout(Integer connectRequestTimeout) {
      this.connectRequestTimeout = connectRequestTimeout;
   }

   /**
    * Gets the max connections.
    *
    * @return the max connections
    */
   public Integer getMaxConnections() {
      return this.maxConnections;
   }

   /**
    * Sets the max connections.
    *
    * @param maxConnections the new max connections
    */
   public void setMaxConnections(Integer maxConnections) {
      this.maxConnections = maxConnections;
   }

   /**
    * Gets the idle timeout.
    *
    * @return the idle timeout
    */
   public Integer getIdleTimeout() {
      return this.idleTimeout;
   }

   /**
    * Sets the idle timeout.
    *
    * @param idleTimeout the new idle timeout
    */
   public void setIdleTimeout(Integer idleTimeout) {
      this.idleTimeout = idleTimeout;
   }

   /**
    * Gets the connection sweeper interval.
    *
    * @return the connection sweeper interval
    */
   public Integer getConnectionSweeperInterval() {
      return this.connectionSweeperInterval;
   }

   /**
    * Sets the connection sweeper interval.
    *
    * @param connectionSweeperInterval the new connection sweeper interval
    */
   public void setConnectionSweeperInterval(Integer connectionSweeperInterval) {
      this.connectionSweeperInterval = connectionSweeperInterval;
   }

   /**
    * Gets the connector class name.
    *
    * @return the connector class name
    */
   public String getConnectorClassName() {
      return this.connectorClassName;
   }

   /**
    * Sets the connector class name.
    *
    * @param connectorClassName the new connector class name
    */
   public void setConnectorClassName(String connectorClassName) {
      this.connectorClassName = connectorClassName;
   }

   /**
    * Gets the enabled.
    *
    * @return the enabled
    */
   public Boolean getEnabled() {
      return this.enabled;
   }

   /**
    * Sets the enabled.
    *
    * @param enabled the new enabled
    */
   public void setEnabled(Boolean enabled) {
      this.enabled = enabled;
   }
}

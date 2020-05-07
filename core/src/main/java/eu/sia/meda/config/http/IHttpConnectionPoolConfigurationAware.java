package eu.sia.meda.config.http;

/**
 * The Interface IHttpConnectionPoolConfigurationAware.
 */
public interface IHttpConnectionPoolConfigurationAware {
   
   /**
    * Gets the connection pool configuration.
    *
    * @return the connection pool configuration
    */
   HttpConnectionPoolConfiguration getConnectionPoolConfiguration();

   /**
    * Sets the connection pool configuration.
    *
    * @param configuration the new connection pool configuration
    */
   void setConnectionPoolConfiguration(HttpConnectionPoolConfiguration configuration);

   /**
    * Checks for connection pool configuration.
    *
    * @return true, if successful
    */
   boolean hasConnectionPoolConfiguration();
}

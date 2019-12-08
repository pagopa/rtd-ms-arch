package eu.sia.meda.layers.connector;

/**
 * The Class ConnectorConfigurationException.
 */
public class ConnectorConfigurationException extends RuntimeException {
   
   /**
    * Instantiates a new connector configuration exception.
    */
   public ConnectorConfigurationException() {
      super("Error loading configuration managers for the connector");
   }

   /**
    * Instantiates a new connector configuration exception.
    *
    * @param connectorName the connector name
    */
   public ConnectorConfigurationException(String connectorName) {
      super(String.format("Error loading configuration managers for the connector: %s", connectorName));
   }
}

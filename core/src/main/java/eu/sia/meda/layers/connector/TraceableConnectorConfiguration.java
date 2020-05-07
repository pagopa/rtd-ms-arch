package eu.sia.meda.layers.connector;

/**
 * The Interface TraceableConnectorConfiguration.
 */
public interface TraceableConnectorConfiguration extends ConnectorConfiguration {
   
   /** The enabled tracing key. */
   String ENABLED_TRACING_KEY = "enabledTracing";

   /**
    * Checks if is enabled tracing.
    *
    * @return the boolean
    */
   Boolean isEnabledTracing();

   /**
    * Sets the enabled tracing.
    *
    * @param enabledTracing the new enabled tracing
    */
   void setEnabledTracing(Boolean enabledTracing);
}

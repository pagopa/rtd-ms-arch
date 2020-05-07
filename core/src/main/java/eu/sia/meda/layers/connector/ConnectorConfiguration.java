package eu.sia.meda.layers.connector;

/**
 * The Interface ConnectorConfiguration.
 */
public interface ConnectorConfiguration {
   
   /** The mocked key. */
   String MOCKED_KEY = "mocked";

   /**
    * Checks if is mocked.
    *
    * @return the boolean
    */
   Boolean isMocked();

   /**
    * Sets the mocked.
    *
    * @param mocked the new mocked
    */
   void setMocked(Boolean mocked);
}

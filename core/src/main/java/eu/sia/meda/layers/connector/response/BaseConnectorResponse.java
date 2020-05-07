package eu.sia.meda.layers.connector.response;

/**
 * The Class BaseConnectorResponse.
 *
 * @param <O> the generic type
 */
public abstract class BaseConnectorResponse<O> {
   
   /**
    * Gets the result.
    *
    * @return the result
    */
   public abstract O getResult();
}

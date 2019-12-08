package eu.sia.meda.connector.rest.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;

/**
 * The Interface IRestResponseTransformer.
 *
 * @param <RESOURCE> the generic type
 * @param <OUTPUT> the generic type
 */
public interface IRestResponseTransformer<RESOURCE, OUTPUT> {
   
   /**
    * Transform.
    *
    * @param restConnectorResponse the rest connector response
    * @return the output
    */
   OUTPUT transform(RestConnectorResponse<RESOURCE> restConnectorResponse);
}

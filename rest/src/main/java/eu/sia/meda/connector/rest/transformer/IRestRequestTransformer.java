package eu.sia.meda.connector.rest.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;

/**
 * The Interface IRestRequestTransformer.
 *
 * @param <INPUT> the generic type
 * @param <DTO> the generic type
 */
public interface IRestRequestTransformer<INPUT, DTO> {
   
   /**
    * Transform.
    *
    * @param om the om
    * @param args the args
    * @return the rest connector request
    */
   RestConnectorRequest<DTO> transform(INPUT om, Object... args);
}

package eu.sia.meda.event.transformer;

import eu.sia.meda.event.response.EventResponse;

/**
 * The Interface IEventResponseTransformer.
 *
 * @param <RESOURCE> the generic type
 * @param <OUTPUT> the generic type
 */
public interface IEventResponseTransformer<RESOURCE, OUTPUT> {
   
   /**
    * Transform.
    *
    * @param restConnectorResponse the rest connector response
    * @return the output
    */
   OUTPUT transform(EventResponse<RESOURCE> restConnectorResponse);
}

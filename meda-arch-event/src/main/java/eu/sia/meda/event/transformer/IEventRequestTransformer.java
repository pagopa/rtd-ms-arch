package eu.sia.meda.event.transformer;

import eu.sia.meda.event.request.EventRequest;

/**
 * The Interface IEventRequestTransformer.
 *
 * @param <INPUT> the generic type
 * @param <DTO> the generic type
 */
public interface IEventRequestTransformer<INPUT, DTO> {
   
   /**
    * Transform.
    *
    * @param om the om
    * @param args the args
    * @return the event request
    */
   EventRequest<DTO> transform(INPUT om, Object... args);
}

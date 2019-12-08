package eu.sia.meda.tracing.event;

import eu.sia.meda.event.response.EventResponse;
import eu.sia.meda.event.transformer.IEventResponseTransformer;
import eu.sia.meda.tracing.config.condition.RealTracingCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * The Class TracingEventResponseTransformer.
 */
@Component
@Conditional({RealTracingCondition.class})
public class TracingEventResponseTransformer implements IEventResponseTransformer<Void, Boolean> {
   
   /**
    * Transform.
    *
    * @param eventResponse the event response
    * @return the boolean
    */
   public Boolean transform(EventResponse<Void> eventResponse) {
      return eventResponse.getResult();
   }
}

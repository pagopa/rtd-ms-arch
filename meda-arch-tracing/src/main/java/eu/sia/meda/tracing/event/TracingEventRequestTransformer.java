package eu.sia.meda.tracing.event;

import eu.sia.meda.event.request.EventRequest;
import eu.sia.meda.event.transformer.IEventRequestTransformer;
import eu.sia.meda.tracing.config.condition.RealTracingCondition;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * The Class TracingEventRequestTransformer.
 */
@Component
@Conditional({RealTracingCondition.class})
public class TracingEventRequestTransformer implements IEventRequestTransformer<String, String> {
   
   /**
    * Transform.
    *
    * @param message the message
    * @param args the args
    * @return the event request
    */
   public EventRequest<String> transform(String message, Object... args) {
      EventRequest<String> event = new EventRequest();
      event.setPayload(message.getBytes(StandardCharsets.UTF_8));
      return event;
   }
}

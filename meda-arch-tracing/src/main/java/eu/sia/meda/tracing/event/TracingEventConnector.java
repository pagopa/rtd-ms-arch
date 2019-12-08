package eu.sia.meda.tracing.event;

import eu.sia.meda.event.BaseEventConnector;
import eu.sia.meda.tracing.config.condition.RealTracingCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class TracingEventConnector.
 */
@Service
@Conditional({RealTracingCondition.class})
public class TracingEventConnector extends BaseEventConnector<String, Boolean, String, Void> {
}

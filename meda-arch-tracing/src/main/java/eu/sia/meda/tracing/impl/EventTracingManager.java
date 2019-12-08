package eu.sia.meda.tracing.impl;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.RequestContextHolder;
import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.TracingMessageBuilder;
import eu.sia.meda.tracing.config.condition.RealTracingCondition;
import eu.sia.meda.tracing.event.TracingEventConnector;
import eu.sia.meda.tracing.event.TracingEventRequestTransformer;
import eu.sia.meda.tracing.event.TracingEventResponseTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * The Class EventTracingManager.
 */
@Component
@Conditional({RealTracingCondition.class})
public class EventTracingManager implements TracingManager {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(EventTracingManager.class);
   
   /** The tracing event connector. */
   private final TracingEventConnector tracingEventConnector;
   
   /** The request transformer. */
   private final TracingEventRequestTransformer requestTransformer;
   
   /** The response transformer. */
   private final TracingEventResponseTransformer responseTransformer;

   /**
    * Instantiates a new event tracing manager.
    *
    * @param tracingEventConnector the tracing event connector
    * @param requestTransformer the request transformer
    * @param responseTransformer the response transformer
    */
   @Autowired
   public EventTracingManager(TracingEventConnector tracingEventConnector, TracingEventRequestTransformer requestTransformer, TracingEventResponseTransformer responseTransformer) {
      this.tracingEventConnector = tracingEventConnector;
      this.requestTransformer = requestTransformer;
      this.responseTransformer = responseTransformer;
   }

   /**
    * Trace.
    *
    * @param message the message
    */
   public void trace(TracingMessageBuilder message) {
      try {
         message.newSection("ID").newEntry("RequestId", RequestContextHolder.getApplicationContext().getRequestId());
         Boolean ack = (Boolean)this.tracingEventConnector.call(message.toString(), this.requestTransformer, this.responseTransformer, new Object[0]);
         if (ack == null || !ack) {
            logger.error(LoggerUtils.formatArchRow("Tracing event was sent but ack was false"));
         }
      } catch (Throwable var3) {
         logger.error(LoggerUtils.formatArchRow("Error while sending tracing event: {}"), var3);
      }

   }
}

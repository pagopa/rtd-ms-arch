package eu.sia.meda.tracing.impl;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.TracingMessageBuilder;
import eu.sia.meda.tracing.config.condition.MockedTracingCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * The Class LogTracingManager.
 */
@Component
@Conditional({MockedTracingCondition.class})
public class LogTracingManager implements TracingManager {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(LogTracingManager.class);

   /**
    * Trace.
    *
    * @param message the message
    */
   public void trace(TracingMessageBuilder message) {
      logger.info(LoggerUtils.formatArchRow("[TRACING]: {}"), message.toString().replaceAll("[\n\r]", ""));
   }
}

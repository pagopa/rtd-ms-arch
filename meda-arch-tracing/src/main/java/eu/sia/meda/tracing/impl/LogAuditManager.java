package eu.sia.meda.tracing.impl;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.domain.model.be4fe.TraceGen;
import eu.sia.meda.tracing.AuditManager;
import eu.sia.meda.tracing.config.condition.AuditMockedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class LogAuditManager.
 */
@Conditional({AuditMockedCondition.class})
@Service
public class LogAuditManager implements AuditManager {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(LogTracingManager.class);

   /**
    * Trace.
    *
    * @param traceGen the trace gen
    */
   public void trace(TraceGen traceGen) {
      logger.info(LoggerUtils.formatArchRow("[AUDIT TRACING]: {}"), traceGen == null ? null : traceGen.toString().replaceAll("[\n\r]", ""));
   }
}

package eu.sia.meda.audit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import eu.sia.meda.audit.connector.rest.AuditRestConnector;
import eu.sia.meda.audit.connector.rest.transformer.AuditRestRequestTransformer;
import eu.sia.meda.audit.connector.rest.transformer.AuditRestResponseTransformer;
import eu.sia.meda.domain.model.be4fe.TraceGen;
import eu.sia.meda.tracing.AuditManager;
import eu.sia.meda.tracing.config.condition.AuditRealCondition;

/**
 * The Class RestAuditManager.
 */
@Conditional({AuditRealCondition.class})
@Service
public class RestAuditManager implements AuditManager {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(RestAuditManager.class);
   
   /** The audit rest connector. */
   @Autowired
   AuditRestConnector auditRestConnector;
   
   /** The audit rest request transformer. */
   @Autowired
   AuditRestRequestTransformer auditRestRequestTransformer;
   
   /** The audit rest response transformer. */
   @Autowired
   AuditRestResponseTransformer auditRestResponseTransformer;

   /**
    * Trace.
    *
    * @param traceGen the trace gen
    */
   public void trace(TraceGen traceGen) {
      logger.debug("auditing tracing: {}", traceGen);

      try {
         this.auditRestConnector.call(traceGen, this.auditRestRequestTransformer, this.auditRestResponseTransformer, 0);
      } catch (Exception var3) {
         logger.error("error calling audit microservice: ", var3);
      }

   }
}

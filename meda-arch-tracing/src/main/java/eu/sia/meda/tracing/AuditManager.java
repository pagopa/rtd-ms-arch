package eu.sia.meda.tracing;

import eu.sia.meda.domain.model.be4fe.TraceGen;

/**
 * The Interface AuditManager.
 */
public interface AuditManager {
   
   /**
    * Trace.
    *
    * @param traceGen the trace gen
    */
   void trace(TraceGen traceGen);
}

package eu.sia.meda.service;

import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.domain.model.be4fe.TraceGen;

/**
 * The Interface SessionContextAuditMapper.
 *
 * @param <T> the generic type
 */
public interface SessionContextAuditMapper<T extends BaseContext> {
   
   /**
    * Map session context to trace gen.
    *
    * @param traceGen the trace gen
    * @param sessionContext the session context
    */
   void mapSessionContextToTraceGen(TraceGen traceGen, T sessionContext);
}

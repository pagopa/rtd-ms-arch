package eu.sia.meda.audit.connector.rest;

import eu.sia.meda.audit.dto.TraceGenDto;
import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.domain.model.be4fe.TraceGen;
import eu.sia.meda.tracing.config.condition.AuditRealCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class AuditRestConnector.
 */
@Service
@Conditional({AuditRealCondition.class})
public class AuditRestConnector extends BaseRestConnector<TraceGen, Void, TraceGenDto, Void> {
}

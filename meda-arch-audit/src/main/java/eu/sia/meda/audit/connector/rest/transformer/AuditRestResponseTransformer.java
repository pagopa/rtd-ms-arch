package eu.sia.meda.audit.connector.rest.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.tracing.config.condition.AuditRealCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class AuditRestResponseTransformer.
 */
@Service
@Conditional({AuditRealCondition.class})
public class AuditRestResponseTransformer implements IRestResponseTransformer<Void, Void> {
   
   /**
    * Transform.
    *
    * @param restConnectorResponse the rest connector response
    * @return the void
    */
   public Void transform(RestConnectorResponse<Void> restConnectorResponse) {
      return null;
   }
}

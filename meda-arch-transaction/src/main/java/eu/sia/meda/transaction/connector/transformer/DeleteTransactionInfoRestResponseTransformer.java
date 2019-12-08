/*
 * 
 */
package eu.sia.meda.transaction.connector.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.transaction.condition.EnabledTransactionManagerCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class DeleteTransactionInfoRestResponseTransformer.
 */
@Service
@Conditional({EnabledTransactionManagerCondition.class})
public class DeleteTransactionInfoRestResponseTransformer implements IRestResponseTransformer<Void, Void> {
   
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

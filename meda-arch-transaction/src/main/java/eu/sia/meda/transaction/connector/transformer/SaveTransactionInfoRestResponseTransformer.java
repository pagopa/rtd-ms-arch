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
 * The Class SaveTransactionInfoRestResponseTransformer.
 */
@Service
@Conditional({EnabledTransactionManagerCondition.class})
public class SaveTransactionInfoRestResponseTransformer implements IRestResponseTransformer<String, String> {
   
   /**
    * Transform.
    *
    * @param restConnectorResponse the rest connector response
    * @return the string
    */
   public String transform(RestConnectorResponse<String> restConnectorResponse) {
      return (String)restConnectorResponse.getResponse().getBody();
   }
}

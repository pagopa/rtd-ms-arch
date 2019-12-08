/*
 * 
 */
package eu.sia.meda.transaction.connector.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.transaction.condition.EnabledTransactionManagerCondition;
import eu.sia.meda.transaction.resource.TransactionInfoResource;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class GetTransactionInfoRestResponseTransformer.
 *
 * @param <T> the generic type
 */
@Service
@Conditional({EnabledTransactionManagerCondition.class})
public class GetTransactionInfoRestResponseTransformer<T> implements IRestResponseTransformer<TransactionInfoResource<T>, T> {
   
   /**
    * Transform.
    *
    * @param restConnectorResponse the rest connector response
    * @return the t
    */
   public T transform(RestConnectorResponse<TransactionInfoResource<T>> restConnectorResponse) {
      return (restConnectorResponse.getResponse().getBody()).getTransactionInfo();
   }
}

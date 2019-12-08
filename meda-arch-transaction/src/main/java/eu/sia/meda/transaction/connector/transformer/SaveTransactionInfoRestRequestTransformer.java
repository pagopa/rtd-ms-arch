/*
 * 
 */
package eu.sia.meda.transaction.connector.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.transaction.condition.EnabledTransactionManagerCondition;
import eu.sia.meda.transaction.dto.TransactionDispoDto;
import eu.sia.meda.transaction.model.TransactionInfoWithIdOp;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * The Class SaveTransactionInfoRestRequestTransformer.
 *
 * @param <T> the generic type
 */
@Service
@Conditional({EnabledTransactionManagerCondition.class})
public class SaveTransactionInfoRestRequestTransformer<T> implements IRestRequestTransformer<TransactionInfoWithIdOp<T>, TransactionDispoDto> {
   
   /** The Constant ID_OP_KEY. */
   private static final String ID_OP_KEY = "idOp";

   /**
    * Transform.
    *
    * @param transactionInfoWithIdOp the transaction info with id op
    * @param args the args
    * @return the rest connector request
    */
   public RestConnectorRequest<TransactionDispoDto> transform(TransactionInfoWithIdOp<T> transactionInfoWithIdOp, Object... args) {
      HttpHeaders headers = new HttpHeaders();
      Map<String, String> queryParams = new HashMap();
      queryParams.put("idOp", transactionInfoWithIdOp.getIdOp());
      TransactionDispoDto dto = new TransactionDispoDto();
      dto.setTransactionInfo(transactionInfoWithIdOp.getTransactionInfo());
      dto.setDispoValues(transactionInfoWithIdOp.getDispoValues());
      RestConnectorRequest<TransactionDispoDto> restConnectorRequest = new RestConnectorRequest(headers, dto, new HashMap(), queryParams, HttpMethod.POST);
      return restConnectorRequest;
   }
}

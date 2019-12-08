/*
 * 
 */
package eu.sia.meda.transaction.connector.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.transaction.condition.EnabledTransactionManagerCondition;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * The Class GetTransactionInfoRestRequestTransformer.
 */
@Service
@Conditional({EnabledTransactionManagerCondition.class})
public class GetTransactionInfoRestRequestTransformer implements IRestRequestTransformer<String, Void> {
   
   /** The Constant ID_OP. */
   private static final String ID_OP = "idOp";

   /**
    * Transform.
    *
    * @param idOp the id op
    * @param args the args
    * @return the rest connector request
    */
   public RestConnectorRequest<Void> transform(String idOp, Object... args) {
      HttpHeaders headers = new HttpHeaders();
      Map<String, String> parametersMap = new HashMap();
      parametersMap.put("idOp", idOp);
      RestConnectorRequest<Void> restConnectorRequest = new RestConnectorRequest(headers, (Object)null, parametersMap, new HashMap(), HttpMethod.GET);
      return restConnectorRequest;
   }
}

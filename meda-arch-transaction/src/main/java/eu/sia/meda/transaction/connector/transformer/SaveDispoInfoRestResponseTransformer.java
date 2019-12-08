/*
 * 
 */
package eu.sia.meda.transaction.connector.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.transaction.condition.EnabledPutDispoInfoCondition;
import eu.sia.meda.transaction.model.SaveDispoInfoResponse;
import eu.sia.meda.transaction.resource.SaveDispoInfoResponseResource;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class SaveDispoInfoRestResponseTransformer.
 */
@Service
@Conditional({EnabledPutDispoInfoCondition.class})
public class SaveDispoInfoRestResponseTransformer implements IRestResponseTransformer<SaveDispoInfoResponseResource, SaveDispoInfoResponse> {
   
   /**
    * Transform.
    *
    * @param restConnectorResponse the rest connector response
    * @return the save dispo info response
    */
   public SaveDispoInfoResponse transform(RestConnectorResponse<SaveDispoInfoResponseResource> restConnectorResponse) {
      SaveDispoInfoResponse response = new SaveDispoInfoResponse();
      response.setExitCode(((SaveDispoInfoResponseResource)restConnectorResponse.getResponse().getBody()).getExitCode());
      response.setMessage(((SaveDispoInfoResponseResource)restConnectorResponse.getResponse().getBody()).getMessage());
      return response;
   }
}

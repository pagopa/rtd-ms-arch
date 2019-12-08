/*
 * 
 */
package eu.sia.meda.transaction.connector.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.transaction.condition.EnabledPutDispoInfoCondition;
import eu.sia.meda.transaction.dto.SaveDispoInfoRequestDto;
import eu.sia.meda.transaction.dto.SaveDispoInfoRequestPayloadDto;
import eu.sia.meda.transaction.model.SaveDispoInfoRequest;
import java.util.HashMap;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

/**
 * The Class SaveDispoInfoRestRequestTransformer.
 */
@Service
@Conditional({EnabledPutDispoInfoCondition.class})
public class SaveDispoInfoRestRequestTransformer implements IRestRequestTransformer<SaveDispoInfoRequest, SaveDispoInfoRequestDto> {
   
   /**
    * Transform.
    *
    * @param request the request
    * @param args the args
    * @return the rest connector request
    */
   public RestConnectorRequest<SaveDispoInfoRequestDto> transform(SaveDispoInfoRequest request, Object... args) {
      SaveDispoInfoRequestPayloadDto payloadDto = new SaveDispoInfoRequestPayloadDto();
      payloadDto.setInfoDispoMap(request.getDispoValues());
      payloadDto.setTrxid(request.getIdOp());
      SaveDispoInfoRequestDto dto = new SaveDispoInfoRequestDto();
      dto.setPayload(payloadDto);
      HttpHeaders headers = new HttpHeaders();
      RestConnectorRequest<SaveDispoInfoRequestDto> restConnectorRequest = new RestConnectorRequest(headers, dto, new HashMap(), new LinkedMultiValueMap(), HttpMethod.POST);
      return restConnectorRequest;
   }
}

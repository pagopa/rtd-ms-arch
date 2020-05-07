package eu.sia.meda.connector.rest.transformer.response;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** To handle cases where you want just to check if the status is 2xx */
@Service
@Slf4j
public class EmptyRest2xxResponseTransformer implements IRestResponseTransformer<Void, Boolean> {
    @Override
    public Boolean transform(RestConnectorResponse<Void> restConnectorResponse) {
        if(restConnectorResponse.getResponse().getStatusCode().is2xxSuccessful()){
            return true;
        } else {
            throw new IllegalStateException(String.format("Unexpected status %d%n%s", restConnectorResponse.getResponse().getStatusCodeValue(), restConnectorResponse.getResponse().toString()));
        }
    }
}

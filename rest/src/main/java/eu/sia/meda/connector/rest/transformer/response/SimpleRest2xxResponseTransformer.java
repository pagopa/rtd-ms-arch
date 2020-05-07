package eu.sia.meda.connector.rest.transformer.response;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** To handle simple cases where RESOURCE == OUTPUT and the expected status is 2xx */
@Service
@Slf4j
public class SimpleRest2xxResponseTransformer<T> implements IRestResponseTransformer<T, T> {
    @Override
    public T transform(RestConnectorResponse<T> restConnectorResponse) {
        if(restConnectorResponse.getResponse().getStatusCode().is2xxSuccessful()){
            return restConnectorResponse.getResponse().getBody();
        } else {
            throw new IllegalStateException(String.format("Unexpected status %d%n%s", restConnectorResponse.getResponse().getStatusCodeValue(), restConnectorResponse.getResponse().toString()));
        }
    }
}

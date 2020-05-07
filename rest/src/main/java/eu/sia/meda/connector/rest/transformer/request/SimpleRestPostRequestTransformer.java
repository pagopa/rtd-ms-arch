package eu.sia.meda.connector.rest.transformer.request;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/** To handle simple cases where INPUT == DTO, method is {@link HttpMethod#POST} and the body is
 *  @see BaseSimpleRestRequestTransformer Handling args
 */
@Service
public class SimpleRestPostRequestTransformer <T> extends BaseSimpleRestRequestTransformer implements IRestRequestTransformer<T,T> {
    @Override
    public RestConnectorRequest<T> transform(T om, Object... args) {
        RestConnectorRequest<T> out = new RestConnectorRequest<>();
        out.setMethod(HttpMethod.POST);
        out.setRequest(om);
        readArgs(out, args);
        return out;
    }
}

package eu.sia.meda.connector.rest.transformer.request;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/** To handle simple cases where
 * <li>INPUT == DTO == Void</li>
 * <li>Method is {@link HttpMethod#PUT}</li>
 * @see BaseSimpleRestRequestTransformer Handling args
 */
@Service
public class SimpleRestPutRequestTransformer extends BaseSimpleRestRequestTransformer implements IRestRequestTransformer<Void,Void> {
    @Override
    public RestConnectorRequest<Void> transform(Void om, Object... args) {
        RestConnectorRequest<Void> out = new RestConnectorRequest<>();
        out.setMethod(HttpMethod.PUT);
        readArgs(out, args);
        return out;
    }
}

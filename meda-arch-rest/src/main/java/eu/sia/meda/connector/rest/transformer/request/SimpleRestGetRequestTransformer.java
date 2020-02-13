package eu.sia.meda.connector.rest.transformer.request;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/** To handle simple cases where
 * <li>INPUT == DTO == Void</li>
 * <li>Method is {@link HttpMethod#GET}</li>
 * @see BaseSimpleRestRequestTransformer Handling args
 */
@Service
public class SimpleRestGetRequestTransformer extends BaseSimpleRestRequestTransformer implements IRestRequestTransformer<Void,Void> {
    @Override
    public RestConnectorRequest<Void> transform(Void om, Object... args) {
        RestConnectorRequest<Void> out = new RestConnectorRequest<>();
        out.setMethod(HttpMethod.GET);
        readArgs(out, args);
        return out;
    }
}

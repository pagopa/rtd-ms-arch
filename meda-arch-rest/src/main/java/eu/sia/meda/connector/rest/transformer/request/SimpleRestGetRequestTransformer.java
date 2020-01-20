package eu.sia.meda.connector.rest.transformer.request;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

/** To handle simple cases where
 * <li>INPUT == DTO == Void</li>
 * <li>Method is {@link HttpMethod#GET}</li>
 * and args contains:
 * <ol>
 * <li>Params, if provided, or a Map&lt;String,String&gt; of PathVariables</li>
 * <li>QueryParams, if provided, or a Map&lt;String,String&gt; of variables to pass as Get params</li>
 * </ol>
 */
@Service
public class SimpleRestGetRequestTransformer implements IRestRequestTransformer<Void,Void> {
    @Override
    public RestConnectorRequest<Void> transform(Void om, Object... args) {
        RestConnectorRequest<Void> out = new RestConnectorRequest<>();
        out.setMethod(HttpMethod.GET);
        if(args.length>0) {
            //noinspection unchecked
            out.setParams((Map<String, String>) args[0]);
        }
        if(args.length>1) {
            //noinspection unchecked
            out.setQueryParams((Map<String, String>) args[1]);
        }
        return out;
    }
}

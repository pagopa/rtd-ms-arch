package eu.sia.meda.connector.rest.transformer.request;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * Args contains:
 * <ol>
 * <li>Params, if provided, or a Map&lt;String,String&gt; of PathVariables</li>
 * <li>QueryParams, if provided, or a Map&lt;String,String&gt; of variables to pass as Get params</li>
 * </ol>
 */
public abstract class BaseSimpleRestRequestTransformer {
    public void readArgs(RestConnectorRequest<?> request, Object... args) {
        request.setMethod(HttpMethod.GET);
        if (args.length > 0 && args[0] != null) {
            //noinspection unchecked
            request.setParams((Map<String, String>) args[0]);
        }
        if (args.length > 1 && args[1] != null) {
            //noinspection unchecked
            request.setQueryParams((Map<String, String>) args[1]);
        }
    }
}

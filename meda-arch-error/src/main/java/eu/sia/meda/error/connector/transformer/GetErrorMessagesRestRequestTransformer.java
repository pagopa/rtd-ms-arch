package eu.sia.meda.error.connector.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.error.condition.RemoteErrorManagerCondition;

/**
 * The Class GetErrorMessagesRestRequestTransformer.
 */
@Service
@Conditional({ RemoteErrorManagerCondition.class })
public class GetErrorMessagesRestRequestTransformer
		implements IRestRequestTransformer<Collection<String>, Collection<String>> {

	/** The Constant ERROR_KEYS_PARAM. */
	private static final String ERROR_KEYS_PARAM = "errorKeys";

	/** The Constant PROJECT_ID_PARAM. */
	private static final String PROJECT_ID_PARAM = "projectId";

	/** The project id. */
	@Value("${spring.application.name}")
	private String projectId;

	/**
	 * Transform.
	 *
	 * @param params the params
	 * @param args   the args
	 * @return the rest connector request
	 */
	public RestConnectorRequest<Collection<String>> transform(Collection<String> params, Object... args) {
		List<String> errorKeys = new ArrayList();
		errorKeys.addAll((Collection) Objects.requireNonNull(params, "null params"));
		errorKeys.removeAll(Collections.singletonList((Object) null));
		Map<String, String> pathParams = new HashMap<>();
		pathParams.put(PROJECT_ID_PARAM, this.projectId);
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.put(ERROR_KEYS_PARAM, errorKeys);
		HttpHeaders httpHeaders = new HttpHeaders();
		RestConnectorRequest request = new RestConnectorRequest(httpHeaders, (Object) null, pathParams, queryParams,
				HttpMethod.GET);
		return request;
	}
}

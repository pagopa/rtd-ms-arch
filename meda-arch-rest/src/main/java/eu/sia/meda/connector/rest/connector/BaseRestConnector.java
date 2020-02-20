package eu.sia.meda.connector.rest.connector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.tracing.RestAuditInterceptor;
import eu.sia.meda.connector.rest.tracing.RestTracingInterceptor;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.BaseConnector;
import eu.sia.meda.layers.connector.http.HttpConnectionPool;
import eu.sia.meda.layers.connector.http.HttpConnectionPoolSweeperScheduler;
import eu.sia.meda.rest.configuration.ArchRestConfigurationService;
import eu.sia.meda.tracing.config.TracingConfiguration;
import eu.sia.meda.util.MockUtils;
import eu.sia.meda.util.ReflectionUtils;
import org.apache.http.auth.Credentials;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.wiremock.WireMockRestServiceServer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.*;

/**
 * The Class BaseRestConnector.
 *
 * @param <INPUT>    the generic type
 * @param <OUTPUT>   the generic type
 * @param <DTO>      the generic type
 * @param <RESOURCE> the generic type
 */
public abstract class BaseRestConnector<INPUT, OUTPUT, DTO, RESOURCE>
		extends BaseConnector<RestConnectorRequest<DTO>, RestConnectorResponse<RESOURCE>> {

	/** The rt. */
	protected RestTemplate rt;

	/** The locations. */
	String[] locations;

	/** The configuration. */
	@Autowired
	@Qualifier("RestConfiguration")
	ArchRestConfigurationService configuration;

	/** The bean factory. */
	@Autowired
	private BeanFactory beanFactory;

	/** The url. */
	private String url;

	/** The name. */
	private String name;

	/** The mocked. */
	private boolean mocked;

	/** The random mock. */
	private boolean randomMock;
	
	/** The copyHeaders - check for copy original headers in the connector request.*/
	private boolean copyHeaders;

	/** The clazz. */
	private Object clazz;

	/** The mock server. */
	private MockRestServiceServer mockServer;

	/** The mock locations. */
	private List<String> mockLocations;

	/** The http connection pool. */
	private HttpConnectionPool httpConnectionPool = null;

	/** The rest template builder. */
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	/** The properties manager. */
	@Autowired
	private PropertiesManager propertiesManager;

	/** The rest tracing interceptor. */
	@Autowired(required = false)
	private RestTracingInterceptor restTracingInterceptor;

	/** The rest audit interceptor. */
	private RestAuditInterceptor restAuditInterceptor;

	/** The tracing configuration. */
	@Autowired
	private TracingConfiguration tracingConfiguration;

	/** The http connection pool sweeper scheduler. */
	@Autowired
	private HttpConnectionPoolSweeperScheduler httpConnectionPoolSweeperScheduler;

	/**
	 * Call method contains the business process for the rest connector.
	 *
	 * @param input               the input dto for the request
	 * @param requestTransformer  the request transformer
	 * @param responseTransformer the response transformer
	 * @param args                the args, variable arguments
	 * @return the output resource
	 */
	public final OUTPUT call(INPUT input, IRestRequestTransformer<INPUT, DTO> requestTransformer,
			IRestResponseTransformer<RESOURCE, OUTPUT> responseTransformer, Object... args) {
		RestConnectorRequest<DTO> restConnectorRequest = requestTransformer.transform(input, args);
		RestConnectorResponse<RESOURCE> restConnectorResponse = (RestConnectorResponse) this
				.execute(restConnectorRequest);
		return responseTransformer.transform(restConnectorResponse);
	}

	/**
	 * Do pre execute.
	 *
	 * @param request the request
	 */
	@Override
	protected void doPreExecute(RestConnectorRequest<DTO> request) {
		super.doPreExecute(request);
		if (this.mocked && this.mockServer != null) {
			this.mockServer.reset();
			this.initWireMockServer(this.buildMockUrl(request));
		}
		if (this.copyHeaders) {
			Map<String, String> headers = BaseContextHolder.getApplicationContext().getCopyHeader();
			if(headers!=null){
				for (Map.Entry<String, String> h : headers.entrySet()) {
					request.addHeader(h.getKey(), h.getValue());
				}
			}
		}
	}

	/**
	 * Builds the mock url.
	 *
	 * @param request the request
	 * @return the string
	 */
	private String buildMockUrl(RestConnectorRequest<DTO> request) {
		DefaultUriBuilderFactory builder = new DefaultUriBuilderFactory();
		URI uri = builder.expand(this.url, request.getParams());
		return uri.toString();
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		ArchRestConfigurationService.RestConfiguration restConfiguration = this.configuration
				.retrieveRestConfiguration(this.getClass().getSimpleName());
		if (restConfiguration == null) {
			throw new ExceptionInInitializerError();
		} else {
			this.configure(restConfiguration);
		}
	}

	/**
	 * Configure.
	 *
	 * @param restConfiguration the rest configuration
	 */
	protected void configure(ArchRestConfigurationService.RestConfiguration restConfiguration) {
		this.name = this.getClass().getSimpleName();
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for REST connector <{}>"), this.name);
		}
		this.rt = this.restTemplateBuilder.setConnectTimeout(restConfiguration.getTimeout())
				.setReadTimeout(restConfiguration.getTimeout()).build();
		if (this.rt.getMessageConverters() != null) {
			this.rt.getMessageConverters().add(0, this.getHalMessageConverter());
		} else {
			this.rt.setMessageConverters(Arrays.asList(this.getHalMessageConverter()));
		}

		List<ClientHttpRequestInterceptor> interceptors = this.rt.getInterceptors();
		if (CollectionUtils.isEmpty((Collection<ClientHttpRequestInterceptor>) interceptors)) {
			interceptors = new ArrayList<>();
			this.rt.setInterceptors((List<ClientHttpRequestInterceptor>) interceptors);
		}

		if (this.tracingConfiguration.isTracingEnabledFor(restConfiguration)) {
			((List) interceptors).add(this.restTracingInterceptor);
		}

		if (this.tracingConfiguration.isAuditEnabledFor(restConfiguration)) {
			this.restAuditInterceptor = (RestAuditInterceptor) this.beanFactory.getBean(RestAuditInterceptor.class,
					new Object[] { restConfiguration.getCodService() });
			((List) interceptors).add(this.restAuditInterceptor);
		}

		if (restConfiguration.hasConnectionPoolConfiguration()) {
			this.httpConnectionPool = new HttpConnectionPool(restConfiguration.getConnectionPoolConfiguration(),
					(Credentials) null, this.httpConnectionPoolSweeperScheduler);
			this.rt.setRequestFactory(new BufferingClientHttpRequestFactory(
					new HttpComponentsClientHttpRequestFactory(this.httpConnectionPool.getHttpClient())));
		} else {
			this.rt.setRequestFactory(
					new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));
		}
		this.copyHeaders = restConfiguration.isCopyHeaders();
		this.mocked = restConfiguration.isMocked();
		this.randomMock = restConfiguration.isRandomMock();
		this.setUrl(restConfiguration.getUrl());
		this.setClazz(ReflectionUtils.getGenericTypeClass(this.getClass(), 3));
		if (this.mocked) {
			if(this.logger.isDebugEnabled()) {
				this.logger.debug(LoggerUtils.formatArchRow("REST connector <{}> MOCKED"), this.name);
			}
			try {
				this.mockLocations = MockUtils.getMockResourcePath(restConfiguration.getPath(),
						restConfiguration.getFiles(), "json");
				this.initWireMockServer(this.url);
			} catch (Exception var4) {
				this.logger.error(LoggerUtils.formatArchRow("Error occured loading mock file: ") + var4.getMessage(),
						var4);
				throw new ExceptionInInitializerError(var4);
			}
		}

	}

	/**
	 * Inits the wire mock server.
	 *
	 * @param url the url
	 */
	private void initWireMockServer(String url) {
		if (this.randomMock) {
			this.locations = new String[1];
			this.locations[0] = MockUtils.selectRandomMockLocation(this.mockLocations);
		} else {
			this.locations = this.mockLocations.toArray(new String[this.mockLocations.size()]);
		}
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(LoggerUtils.formatArchRow("REST connector <{}> mock file(s) <{}>"), this.name,
					this.locations);
			this.logger.debug(LoggerUtils.formatArchRow("REST connector <{}> mock url <{}>"), this.name, url);
		}
	}

	/**
	 * Do execute.
	 *
	 * @param request the request
	 * @return the rest connector response
	 */
	@Override
	protected RestConnectorResponse<RESOURCE> doExecute(RestConnectorRequest<DTO> request) {

		RestConnectorResponse<RESOURCE> response = new RestConnectorResponse<>();
		if (this.mocked) {
			this.mockServer = WireMockRestServiceServer.with(this.rt)
					.baseUrl(this.buildUriString((MultiValueMap) null, request.getParams())).stubs(this.locations)
					.build();
		}

		try {
			Object theClazz = this.getClazz();
			HttpEntity httpEntity = new HttpEntity<>(request.getRequest(), request.getHttpHeaders());
			ResponseEntity responseEntity;
			if (theClazz instanceof ParameterizedType) {
				responseEntity = this.rt.exchange(
						this.buildUri(request.getQueryParamsMultiValue(), request.getParams()), request.getMethod(),
						httpEntity, ParameterizedTypeReference.forType((ParameterizedType) theClazz));
			} else {
				responseEntity = this.rt.exchange(
						this.buildUri(request.getQueryParamsMultiValue(), request.getParams()), request.getMethod(),
						httpEntity, (Class) theClazz);
			}
			if (logger.isDebugEnabled()) {
				this.logger.debug(LoggerUtils.formatArchRow("response HEADERS: {}"), responseEntity.getHeaders());
			}
			response.setResponse(responseEntity);
			return response;
		} catch (AssertionError var6) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, var6.getMessage());
		}
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	protected void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Builds the uri.
	 *
	 * @param queryParam the query param
	 * @param uriParam   the uri param
	 * @return the uri
	 */
	protected URI buildUri(MultiValueMap<String, String> queryParam, Map<String, String> uriParam) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url);
		if (queryParam != null) {
			builder.queryParams(queryParam);
		}

		return builder.buildAndExpand(uriParam).toUri();
	}

	/**
	 * Builds the uri string.
	 *
	 * @param queryParam the query param
	 * @param uriParam   the uri param
	 * @return the string
	 */
	protected String buildUriString(MultiValueMap<String, String> queryParam, Map<String, String> uriParam) {
		return this.buildUri(queryParam, uriParam).toString();
	}

	/**
	 * Gets the clazz.
	 *
	 * @return the clazz
	 */
	public Object getClazz() {
		return this.clazz;
	}

	/**
	 * Sets the clazz.
	 *
	 * @param clazz the new clazz
	 */
	public void setClazz(Object clazz) {
		this.clazz = clazz;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the hal message converter.
	 *
	 * @return the hal message converter
	 */
	private HttpMessageConverter getHalMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new Jackson2HalModule());
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new Jdk8Module());
		MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(
				ResourceSupport.class);
		halConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
		halConverter.setObjectMapper(objectMapper);
		return halConverter;
	}


}

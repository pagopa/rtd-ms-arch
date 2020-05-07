package eu.sia.meda.connector.rest.model;

import eu.sia.meda.layers.connector.request.BaseConnectorRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * The Class RestConnectorRequest.
 *
 * @param <INPUT> the generic type
 */
public class RestConnectorRequest<INPUT> extends BaseConnectorRequest {
   
   /** The http headers. */
   private HttpHeaders httpHeaders;
   
   /** The payload. */
   private INPUT payload;
   
   /** The params. */
   private Map<String, String> params = new HashMap<>();
   
   /** The query params. */
   private MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
   
   /** The method. */
   private HttpMethod method;

   /**
    * Instantiates a new rest connector request.
    */
   public RestConnectorRequest() {
   }

   /**
    * Instantiates a new rest connector request.
    *
    * @param httpHeaders the http headers
    * @param payload the payload
    * @param params the params
    * @param queryParams the query params
    * @param method the method
    */
   public RestConnectorRequest(HttpHeaders httpHeaders, @Nullable INPUT payload, Map<String, String> params, Map<String, String> queryParams, HttpMethod method) {
      this.httpHeaders = httpHeaders;
      this.payload = payload;
      this.params = params;
      this.method = method;
      this.queryParams = this.toMultiValueMap(queryParams);
   }

   /**
    * Instantiates a new rest connector request.
    *
    * @param httpHeaders the http headers
    * @param payload the payload
    * @param params the params
    * @param queryParams the query params
    * @param method the method
    */
   public RestConnectorRequest(HttpHeaders httpHeaders, @Nullable INPUT payload, Map<String, String> params, MultiValueMap<String, String> queryParams, HttpMethod method) {
      this.httpHeaders = httpHeaders;
      this.payload = payload;
      this.params = params;
      this.method = method;
      this.queryParams = queryParams;
   }

   /**
    * Gets the http headers.
    *
    * @return the http headers
    */
   public HttpHeaders getHttpHeaders() {
      return this.httpHeaders;
   }

   /**
    * Sets the http headers.
    *
    * @param httpHeaders the new http headers
    */
   private void setHttpHeaders(HttpHeaders httpHeaders) {
      this.httpHeaders = httpHeaders;
   }

   /**
    * Gets the request.
    *
    * @return the request
    */
   public INPUT getRequest() {
      return this.payload;
   }

   /**
    * Sets the request.
    *
    * @param request the new request
    */
   public void setRequest(INPUT request) {
      this.payload = request;
   }

   /**
    * Gets the params.
    *
    * @return the params
    */
   public Map<String, String> getParams() {
      return this.params;
   }

   /**
    * Sets the params.
    *
    * @param params the params
    */
   public void setParams(Map<String, String> params) {
      this.params = params;
   }

   /**
    * Adds the params.
    *
    * @param key the key
    * @param value the value
    */
   public void addParams(String key, String value) {
      if (this.params == null) {
         this.params = new HashMap<>();
      }

      this.params.put(key, value);
   }

   /**
    * Gets the method.
    *
    * @return the method
    */
   public HttpMethod getMethod() {
      return this.method;
   }

   /**
    * Sets the method.
    *
    * @param method the new method
    */
   public void setMethod(HttpMethod method) {
      this.method = method;
   }

   /**
    * Gets the query params.
    *
    * @return the query params
    */
   public Map<String, String> getQueryParams() {
      return this.queryParams == null ? null : this.queryParams.toSingleValueMap();
   }

   /**
    * Sets the query params.
    *
    * @param queryParams the query params
    */
   public void setQueryParams(Map<String, String> queryParams) {
      this.queryParams = this.toMultiValueMap(queryParams);
   }

   /**
    * Sets the query params.
    *
    * @param queryParams the query params
    */
   public void setQueryParams(MultiValueMap<String, String> queryParams) {
      this.queryParams = queryParams;
   }

   /**
    * Gets the query params multi value.
    *
    * @return the query params multi value
    */
   public MultiValueMap<String, String> getQueryParamsMultiValue() {
      return this.queryParams;
   }

   /**
    * Adds the header.
    *
    * @param key the key
    * @param value the value
    */
   public void addHeader(String key, String value) {
      if (this.httpHeaders == null) {
         this.httpHeaders = new HttpHeaders();
      }

      this.httpHeaders.add(key, value);
   }

   /**
    * Adds the header if exists.
    *
    * @param key the key
    * @param value the value
    * @return true, if successful
    */
   public boolean addHeaderIfExists(String key, String value) {
      if (this.httpHeaders == null) {
         this.httpHeaders = new HttpHeaders();
      }

      if (value != null) {
         this.httpHeaders.add(key, value);
         return true;
      } else {
         return false;
      }
   }

   /**
    * To multi value map.
    *
    * @param map the map
    * @return the multi value map
    */
   private MultiValueMap<String, String> toMultiValueMap(Map<String, String> map) {
      if (map == null) {
         return null;
      } else {
         MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
         multiValueMap.setAll(map);
         return multiValueMap;
      }
   }
}

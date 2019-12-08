package eu.sia.meda.connector.rest.model;

import eu.sia.meda.layers.connector.response.BaseConnectorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * The Class RestConnectorResponse.
 *
 * @param <T> the generic type
 */
public class RestConnectorResponse<T> extends BaseConnectorResponse {
   
   /** The response. */
   private ResponseEntity<T> response;
   
   /** The http headers. */
   private HttpHeaders httpHeaders;

   /**
    * Gets the response.
    *
    * @return the response
    */
   public ResponseEntity<T> getResponse() {
      return this.response;
   }

   /**
    * Sets the response.
    *
    * @param response the new response
    */
   public void setResponse(ResponseEntity<T> response) {
      this.response = response;
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
   public void setHttpHeaders(HttpHeaders httpHeaders) {
      this.httpHeaders = httpHeaders;
   }

   /**
    * Gets the result.
    *
    * @return the result
    */
   public RestConnectorResponse<T> getResult() {
      return this;
   }
}

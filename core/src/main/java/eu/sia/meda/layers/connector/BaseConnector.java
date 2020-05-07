package eu.sia.meda.layers.connector;

import org.slf4j.Logger;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.layers.connector.request.BaseConnectorRequest;
import eu.sia.meda.layers.connector.response.BaseConnectorResponse;

/**
 * The Class BaseConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 */
public abstract class BaseConnector<INPUT extends BaseConnectorRequest, OUTPUT extends BaseConnectorResponse> {
   
   /** The logger. */
   protected final Logger logger = LoggerUtils.getLogger(this.getClass());

   /**
    * Do pre execute.
    *
    * @param request the request
    */
   protected void doPreExecute(INPUT request) {
	  if(this.logger.isDebugEnabled()) {
		  this.logger.debug(LoggerUtils.formatArchRow("STARTING doPreExecute"));
	  }
   }

   /**
    * Do execute.
    *
    * @param request the request
    * @return the output
    */
   protected OUTPUT doExecute(INPUT request) {
      return null;
   }

   /**
    * Do post execute.
    *
    * @param request the request
    * @param response the response
    * @return the output
    */
   protected OUTPUT doPostExecute(INPUT request, OUTPUT response) {
      return response;
   }

   /**
    * Execute.
    *
    * @param request the request
    * @return the output
    */
   protected OUTPUT execute(INPUT request) {
      this.doPreExecute(request);
      OUTPUT output = this.doExecute(request);
      return this.doPostExecute(request, output);
   }
}

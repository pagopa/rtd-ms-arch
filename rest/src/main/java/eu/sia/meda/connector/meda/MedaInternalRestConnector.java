/*
 * 
 */
package eu.sia.meda.connector.meda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.rest.configuration.ArchRestConfigurationService;

/**
 * The Class MedaInternalRestConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 * @param <DTO> the generic type
 * @param <RESOURCE> the generic type
 */
public class MedaInternalRestConnector<INPUT, OUTPUT, DTO, RESOURCE> extends BaseRestConnector<INPUT, OUTPUT, DTO, RESOURCE> {
   
   /** The connector name. */
   private String connectorName;
   
   /** The my configuration. */
   private ArchRestConfigurationService.RestConfiguration myConfiguration;
   
   /** The configuration. */
   @Autowired
   @Qualifier("RestConfiguration")
   ArchRestConfigurationService configuration;

   /**
    * Instantiates a new meda internal rest connector.
    *
    * @param connectorName the connector name
    */
   public MedaInternalRestConnector(String connectorName) {
      this.connectorName = connectorName;
   }

   /**
    * Instantiates a new meda internal rest connector.
    *
    * @param connectorName the connector name
    * @param configuration the configuration
    */
   public MedaInternalRestConnector(String connectorName, ArchRestConfigurationService.RestConfiguration configuration) {
      this.connectorName = connectorName;
      this.myConfiguration = configuration;
      this.setName(connectorName);
   }

   /**
    * Inits the.
    */
   @Override
   protected void init() {
      if (this.myConfiguration == null) {
         this.myConfiguration = this.configuration.retrieveRestConfiguration(this.connectorName);
      }

      if (this.myConfiguration == null) {
         throw new ExceptionInInitializerError();
      } else {
    	  if(this.logger.isDebugEnabled()) {
    		  this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for REST connector <{}>"), this.connectorName);
    	  }
         this.configure(this.myConfiguration);
      }
   }

   /**
    * Do pre execute.
    *
    * @param request the request
    */
   @Override
   protected void doPreExecute(RestConnectorRequest<DTO> request) {
      try {
         String authorizationHeader = BaseContextHolder.getAuthorizationContext().getAuthorizationHeader();
         if (authorizationHeader != null) {
            request.addHeader("Authorization", authorizationHeader);
         }
      } catch (Exception var3) {
    	  if(this.logger.isErrorEnabled()) {
    		  this.logger.error(LoggerUtils.formatArchRow("error retrieving authorization header"));
    	  }
      }
      super.doPreExecute(request);
   }
}

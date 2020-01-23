package eu.sia.meda.connector.medacore;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.rest.configuration.ArchRestConfigurationService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class MedaCoreRestConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 * @param <DTO> the generic type
 * @param <RESOURCE> the generic type
 */
public class MedaCoreRestConnector<INPUT, OUTPUT, DTO, RESOURCE> extends BaseRestConnector<INPUT, OUTPUT, DTO, RESOURCE> {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerUtils.getLogger(MedaCoreRestConnector.class);
   
   /** The connector name. */
   private String connectorName;
   
   /** The my configuration. */
   private ArchRestConfigurationService.RestConfiguration myConfiguration;
   
   /** The configuration. */
   @Autowired
   ArchMedaCoreConnectorConfigurationService configuration;
   
   /** The jwt token provisioner. */
   @Autowired
   IJwtTokenProvisioner jwtTokenProvisioner;

   /**
    * Instantiates a new meda core rest connector.
    *
    * @param connectorName the connector name
    */
   public MedaCoreRestConnector(String connectorName) {
      this.connectorName = connectorName;
   }

   /**
    * Instantiates a new meda core rest connector.
    *
    * @param connectorName the connector name
    * @param configuration the configuration
    */
   public MedaCoreRestConnector(String connectorName, ArchRestConfigurationService.RestConfiguration configuration) {
      this.connectorName = connectorName;
      this.myConfiguration = configuration;
      this.setName(connectorName);
   }

   /**
    * Inits the.
    */
   protected void init() {
      if (this.myConfiguration == null) {
         this.myConfiguration = this.configuration.retrieveRestConfiguration(this.connectorName);
      }

      if (this.myConfiguration == null) {
         throw new ExceptionInInitializerError();
      } else {
         logger.debug(LoggerUtils.formatArchRow("Configuration loaded for REST connector <{}>"), this.connectorName);
         this.configure(this.myConfiguration);
         ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
         ses.scheduleAtFixedRate(() -> {
            logger.debug(LoggerUtils.formatArchRow("Attemting to call MedaCoreRest ScheduledExecutor"));
            this.jwtTokenProvisioner.manipulateToken((ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration)this.myConfiguration);
         }, 0L, 50L, TimeUnit.MINUTES);
      }
   }

   /**
    * Do pre execute.
    *
    * @param request the request
    */
   protected void doPreExecute(RestConnectorRequest<DTO> request) {
      logger.debug(LoggerUtils.formatArchRow("Entering MedaCoreRest preExecute"));

      try {
         logger.debug(LoggerUtils.formatArchRow("Trying to call TokenProvisioner"));
         String jwtToken = this.jwtTokenProvisioner.manipulateToken((ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration)this.myConfiguration);
         logger.debug(LoggerUtils.formatArchRow("TokenProvisioner returned: {}"), jwtToken);
         if (jwtToken != null && !jwtToken.isEmpty()) {
            request.addHeader("Authorization", "Bearer " + jwtToken);
            logger.debug(LoggerUtils.formatArchRow("Adding JWT token to request header"));
         }
      } catch (Exception var3) {
         logger.debug(LoggerUtils.formatArchRow("Exception during TokenProvisioner manipulate: {}"), var3.getMessage());
      }

      super.doPreExecute(request);
   }
}

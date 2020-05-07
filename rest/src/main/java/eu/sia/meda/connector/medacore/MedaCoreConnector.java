package eu.sia.meda.connector.medacore;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.util.ReflectionUtils;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * The Class MedaCoreConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 * @param <DTO> the generic type
 * @param <RESOURCE> the generic type
 */
public abstract class MedaCoreConnector<INPUT, OUTPUT, DTO, RESOURCE> {
   
   /** The Constant BEAN_SUFFIX. */
   private static final String BEAN_SUFFIX = "_CORE";
   
   /** The logger. */
   protected final Logger logger = LoggerUtils.getLogger(this.getClass());
   
   /** The configuration. */
   @Autowired
   ArchMedaCoreConnectorConfigurationService configuration;
   
   /** The bean factory. */
   @Autowired
   private AutowireCapableBeanFactory beanFactory;
   
   /** The core rest connector. */
   private MedaCoreRestConnector<INPUT, OUTPUT, DTO, RESOURCE> coreRestConnector;

   /**
    * Inits the.
    */
   @PostConstruct
   private void init() {
      String connectorName = this.getClass().getSimpleName();
      ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration myConfiguration = this.configuration.retrieveRestConfiguration(this.getClass().getSimpleName());
      if (myConfiguration == null) {
         throw new ExceptionInInitializerError();
      } else {
         this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for CORE REST connector <{}>"), connectorName);

         try {
            this.coreRestConnector = new MedaCoreRestConnector(connectorName + BEAN_SUFFIX, myConfiguration);
            this.beanFactory.autowireBean(this.coreRestConnector);
            this.coreRestConnector = (MedaCoreRestConnector)this.beanFactory.initializeBean(this.coreRestConnector, connectorName + BEAN_SUFFIX);
            this.coreRestConnector.setClazz(ReflectionUtils.getGenericTypeClass(this.getClass(), 3));
         } catch (BeansException var4) {
            this.coreRestConnector = null;
            throw new ExceptionInInitializerError(var4);
         }
      }
   }

   /**
    * Call.
    *
    * @param input the input
    * @param requestTransformer the request transformer
    * @param responseTransformer the response transformer
    * @param args the args
    * @return the output
    */
   public OUTPUT call(INPUT input, IRestRequestTransformer<INPUT, DTO> requestTransformer, IRestResponseTransformer<RESOURCE, OUTPUT> responseTransformer, Object... args) {
      if (this.coreRestConnector == null) {
         throw new UnsupportedOperationException("Core Rest Connector not defined");
      } else {
         return this.coreRestConnector.call(input, requestTransformer, responseTransformer, args);
      }
   }
}

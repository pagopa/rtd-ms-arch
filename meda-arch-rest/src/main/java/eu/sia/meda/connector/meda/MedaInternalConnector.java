/*
 * 
 */
package eu.sia.meda.connector.meda;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.rest.configuration.ArchRestConfigurationService;
import eu.sia.meda.util.ReflectionUtils;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * The Class MedaInternalConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 * @param <DTO> the generic type
 * @param <RESOURCE> the generic type
 */
public abstract class MedaInternalConnector<INPUT, OUTPUT, DTO, RESOURCE> {
   
   /** The Constant BEAN_SUFFIX. */
   private static final String BEAN_SUFFIX = "_INTERNAL";
   
   /** The logger. */
   protected final Logger logger = LoggerUtils.getLogger(this.getClass());
   
   /** The configuration. */
   @Autowired
   @Qualifier("MedaConnectorConfiguration")
   ArchMedaInternalConnectorConfigurationService configuration;
   
   /** The bean factory. */
   @Autowired
   private AutowireCapableBeanFactory beanFactory;
   
   /** The internal rest connector. */
   private MedaInternalRestConnector<INPUT, OUTPUT, DTO, RESOURCE> internalRestConnector;

   /**
    * Inits the.
    */
   @PostConstruct
   private void init() {
      String connectorName = this.getClass().getSimpleName();
      ArchMedaInternalConnectorConfigurationService.MedaInternalConfiguration myConfiguration = this.configuration.retrieveRestConfiguration(this.getClass().getSimpleName());
      if (myConfiguration == null) {
         throw new ExceptionInInitializerError();
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for INTERNAL REST connector <{}>"),
						connectorName);
			}
			ArchRestConfigurationService.RestConfiguration restConfig = new ArchRestConfigurationService.RestConfiguration(
					myConfiguration);

			try {
				this.internalRestConnector = new MedaInternalRestConnector(connectorName + BEAN_SUFFIX, restConfig);
				this.beanFactory.autowireBean(this.internalRestConnector);
				this.internalRestConnector = (MedaInternalRestConnector) this.beanFactory
						.initializeBean(this.internalRestConnector, connectorName + BEAN_SUFFIX);
				this.internalRestConnector.setClazz(ReflectionUtils.getGenericTypeClass(this.getClass(), 3));
			} catch (BeansException var5) {
				this.internalRestConnector = null;
				throw new ExceptionInInitializerError(var5);
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
      if (this.internalRestConnector == null) {
         throw new UnsupportedOperationException("Internal Rest Connector not defined");
      } else {
         return this.internalRestConnector.call(input, requestTransformer, responseTransformer, args);
      }
   }
}

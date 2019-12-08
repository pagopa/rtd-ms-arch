package eu.sia.meda.switcher;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.eventlistener.configuration.ArchEventListenerConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class CopySwitcherListenerConfig.
 */
@Configuration
public class CopySwitcherListenerConfig {
   
   /** The logger. */
   private final Logger logger = LoggerFactory.getLogger(CopySwitcherListenerConfig.class);
   
   /** The properties manager. */
   @Autowired
   private PropertiesManager propertiesManager;

   /**
    * Copy switcher listener.
    *
    * @return the copy switcher semaphore listener
    */
   @Bean
   @ConditionalOnProperty(
      prefix = "copy-switcher",
      name = {"semaphoreListener.topic", "semaphoreListener.bootstrapServers"}
   )
   public CopySwitcherSemaphoreListener copySwitcherListener() {
      this.logger.debug(LoggerUtils.formatArchRow("Instantiate CopySwitcherListener"));
      ArchEventListenerConfigurationService.EventListenerConfiguration switcherConfiguration = this.retrieveSwitcherListenerConfiguration();
      return new CopySwitcherSemaphoreListener(switcherConfiguration);
   }

   /**
    * Retrieve switcher listener configuration.
    *
    * @return the arch event listener configuration service. event listener configuration
    */
   private ArchEventListenerConfigurationService.EventListenerConfiguration retrieveSwitcherListenerConfiguration() {
      ArchEventListenerConfigurationService.EventListenerConfiguration config = new ArchEventListenerConfigurationService.EventListenerConfiguration();
      if (this.propertiesManager.containsProperty("copy-switcher.semaphoreListener.topic") && this.propertiesManager.containsProperty("copy-switcher.semaphoreListener.bootstrapServers")) {
         config.setTopic((String)this.propertiesManager.get("copy-switcher.semaphoreListener.topic", String.class));
         config.setGroupId((String)this.propertiesManager.get("copy-switcher.semaphoreListener.groupId", String.class));
         config.setBootstrapServers((String)this.propertiesManager.get("copy-switcher.semaphoreListener.bootstrapServers", String.class));
         config.setSecurityProtocol((String)this.propertiesManager.get("copy-switcher.semaphoreListener.security.protocol", String.class));
         config.setSaslServiceName((String)this.propertiesManager.get("copy-switcher.semaphoreListener.sasl.kerberos.service.name", String.class));
         config.setSaslJaasConf((String)this.propertiesManager.get("copy-switcher.semaphoreListener.sasl.jaas.config", String.class));
         return config;
      } else {
         this.logger.warn(LoggerUtils.formatArchRow("Configuration not present for semaphoreListener"));
         return null;
      }
   }
}

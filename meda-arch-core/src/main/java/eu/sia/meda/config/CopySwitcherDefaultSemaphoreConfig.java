package eu.sia.meda.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class CopySwitcherDefaultSemaphoreConfig.
 */
@Configuration
@ConfigurationProperties(
   prefix = "copy-switcher"
)
public class CopySwitcherDefaultSemaphoreConfig {
   
   /** The semaphores. */
   private List<String> semaphores = new ArrayList<>();
   
   /** The validities. */
   private Map<String, Long> validities = new HashMap<>();
   
   /** The config base url. */
   private String configBaseUrl;
   
   /** The refresh delay seconds. */
   private Long refreshDelaySeconds;

   /**
    * Gets the semaphores.
    *
    * @return the semaphores
    */
   public List<String> getSemaphores() {
      return this.semaphores;
   }

   /**
    * Gets the validities.
    *
    * @return the validities
    */
   public Map<String, Long> getValidities() {
      return this.validities;
   }

   /**
    * Gets the config base url.
    *
    * @return the config base url
    */
   public String getConfigBaseUrl() {
      return this.configBaseUrl;
   }

   /**
    * Sets the config base url.
    *
    * @param configBaseUrl the new config base url
    */
   public void setConfigBaseUrl(String configBaseUrl) {
      this.configBaseUrl = configBaseUrl;
   }

   /**
    * Gets the refresh delay seconds.
    *
    * @return the refresh delay seconds
    */
   public Long getRefreshDelaySeconds() {
      return this.refreshDelaySeconds;
   }

   /**
    * Sets the refresh delay seconds.
    *
    * @param refreshDelaySeconds the new refresh delay seconds
    */
   public void setRefreshDelaySeconds(Long refreshDelaySeconds) {
      this.refreshDelaySeconds = refreshDelaySeconds;
   }
}

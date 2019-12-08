/*
 * 
 */
package eu.sia.meda.exceptions.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class ErrorMappingConfiguration.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(
   prefix = "error-mapping"
)
@PropertySource(
   value = {"${application.config.path:file:./config/}error-mapping.properties"},
   ignoreResourceNotFound = true
)
public class ErrorMappingConfiguration {
   
   /** The errors. */
   private Map<String, String> errors = new HashMap();

   /**
    * Gets the errors.
    *
    * @return the errors
    */
   public Map<String, String> getErrors() {
      return this.errors;
   }

   /**
    * Sets the errors.
    *
    * @param errors the errors
    */
   public void setErrors(Map<String, String> errors) {
      this.errors = errors;
   }
}

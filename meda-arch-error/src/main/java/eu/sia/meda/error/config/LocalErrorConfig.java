package eu.sia.meda.error.config;

import eu.sia.meda.error.condition.ErrorManagerEnabledCondition;
import eu.sia.meda.exceptions.model.ErrorMessage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class LocalErrorConfig.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(
   prefix = "local-error"
)
@PropertySource(
   value = {"classpath:local-error.properties"},
   name = "localErrorConfig",
   ignoreResourceNotFound = true
)
@Conditional({ErrorManagerEnabledCondition.class})
public class LocalErrorConfig {
   
   /** The messages. */
   Map<String, ErrorMessage> messages = new HashMap();

   /**
    * Gets the messages.
    *
    * @return the messages
    */
   public Map<String, ErrorMessage> getMessages() {
      return this.messages;
   }

   /**
    * Sets the messages.
    *
    * @param messages the messages
    */
   public void setMessages(Map<String, ErrorMessage> messages) {
      this.messages = new HashMap();
      Iterator var2 = messages.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, ErrorMessage> entry = (Entry)var2.next();
         ((ErrorMessage)entry.getValue()).setMessageKey((String)entry.getKey());
         this.messages.put(((String)entry.getKey()).toLowerCase(), entry.getValue());
      }

   }
}

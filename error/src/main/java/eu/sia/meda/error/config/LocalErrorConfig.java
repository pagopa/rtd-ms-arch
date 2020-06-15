package eu.sia.meda.error.config;

import eu.sia.meda.exceptions.model.ErrorMessage;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class LocalErrorConfig.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "local-error")
@PropertySource(value = {"classpath:error.properties",
        "classpath:local-error.properties"}, name = "localErrorConfig", ignoreResourceNotFound = true)
public class LocalErrorConfig {

    /**
     * The messages.
     */
    Map<String, ErrorMessage> messages = new HashMap<>();

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
        this.messages = new HashMap<>();

        messages.entrySet().stream().forEach(x -> {
            x.getValue().setMessageKey(x.getKey());
            this.messages.put((x.getKey()).toLowerCase(), x.getValue());
        });

    }
}

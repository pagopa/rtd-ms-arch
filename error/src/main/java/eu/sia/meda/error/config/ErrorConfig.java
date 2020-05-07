package eu.sia.meda.error.config;

import eu.sia.meda.error.condition.ErrorManagerEnabledCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class ErrorConfig.
 */
@Configuration
@PropertySource(
   value = {"classpath:error.properties"},
   name = "errorConfig"
)
@Conditional({ErrorManagerEnabledCondition.class})
public class ErrorConfig {
}

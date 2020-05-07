package eu.sia.meda.error.config;

import eu.sia.meda.error.condition.RemoteErrorManagerCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class RemoteErrorConfig.
 */
@Configuration
@PropertySource(
   value = {"classpath:remote-error.properties"},
   name = "remoteErrorConfig"
)
@Conditional({RemoteErrorManagerCondition.class})
public class RemoteErrorConfig {
}

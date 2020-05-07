package eu.sia.meda.error.config;

import eu.sia.meda.error.condition.ErrorManagerKafkaServersCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class KafkaServersErrorConfig.
 */
@Configuration
@PropertySource(
   value = {"classpath:kafka-error.properties"},
   name = "kafkaErrorConfig"
)
@Conditional({ErrorManagerKafkaServersCondition.class})
public class KafkaServersErrorConfig {
}

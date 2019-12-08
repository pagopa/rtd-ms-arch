package eu.sia.meda.audit.config;

import eu.sia.meda.tracing.config.condition.AuditRealCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class AuditConfig.
 */
@Configuration
@PropertySource(
   value = {"classpath:audit.properties"},
   name = "auditConfig"
)
@Conditional({AuditRealCondition.class})
public class AuditConfig {
}

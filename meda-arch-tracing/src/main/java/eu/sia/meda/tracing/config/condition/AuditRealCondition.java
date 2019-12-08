package eu.sia.meda.tracing.config.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class AuditRealCondition.
 */
public class AuditRealCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new audit real condition.
    */
   public AuditRealCondition() {
      super(ConfigurationPhase.PARSE_CONFIGURATION);
   }

   /**
    * The Class AuditNotMocked.
    */
   @ConditionalOnProperty(
      prefix = "tracing",
      name = {"auditMocked"},
      havingValue = "false",
      matchIfMissing = true
   )
   public static class AuditNotMocked {
   }

   /**
    * The Class TracingEnabled.
    */
   @Conditional({EnabledTracingCondition.class})
   public static class TracingEnabled {
   }
}

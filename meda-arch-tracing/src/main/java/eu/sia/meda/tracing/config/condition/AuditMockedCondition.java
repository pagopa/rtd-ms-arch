package eu.sia.meda.tracing.config.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class AuditMockedCondition.
 */
public class AuditMockedCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new audit mocked condition.
    */
   public AuditMockedCondition() {
      super(ConfigurationPhase.PARSE_CONFIGURATION);
   }

   /**
    * The Class AuditMocked.
    */
   @ConditionalOnProperty(
      prefix = "tracing",
      name = {"auditMocked"},
      havingValue = "true"
   )
   public static class AuditMocked {
   }

   /**
    * The Class TracingEnabled.
    */
   @Conditional({EnabledTracingCondition.class})
   public static class TracingEnabled {
   }
}

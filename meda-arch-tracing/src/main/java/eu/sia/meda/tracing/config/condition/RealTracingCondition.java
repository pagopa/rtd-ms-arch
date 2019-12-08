package eu.sia.meda.tracing.config.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class RealTracingCondition.
 */
public class RealTracingCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new real tracing condition.
    */
   public RealTracingCondition() {
      super(ConfigurationPhase.PARSE_CONFIGURATION);
   }

   /**
    * The Class TracingNotMocked.
    */
   @ConditionalOnProperty(
      prefix = "tracing",
      name = {"mocked"},
      havingValue = "false"
   )
   public static class TracingNotMocked {
   }

   /**
    * The Class TracingEnabled.
    */
   @Conditional({EnabledTracingCondition.class})
   public static class TracingEnabled {
   }
}

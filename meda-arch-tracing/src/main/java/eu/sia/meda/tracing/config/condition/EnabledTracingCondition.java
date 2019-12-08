package eu.sia.meda.tracing.config.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class EnabledTracingCondition.
 */
public class EnabledTracingCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new enabled tracing condition.
    */
   public EnabledTracingCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class TracingEnabled.
    */
   @ConditionalOnProperty(
      prefix = "tracing",
      name = {"enabled"},
      havingValue = "true"
   )
   public static class TracingEnabled {
   }
}

package eu.sia.meda.performance;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * The Class EnabledTracingCondition.
 */
public class EnabledPerformanceAspect extends AllNestedConditions {
   
   /**
    * Instantiates a new enabled tracing condition.
    */
   public EnabledPerformanceAspect() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class TracingEnabled.
    */
   @ConditionalOnProperty(
      prefix = "performance-aspect",
      name = {"enabled"},
      havingValue = "true"
   )
   public static class PerformanceAspectEnabled {
   }
}

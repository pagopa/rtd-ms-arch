package eu.sia.meda.tracing.controllers;

import eu.sia.meda.tracing.config.condition.EnabledTracingCondition;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class ControllerTracingCondition.
 */
public class ControllerTracingCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new controller tracing condition.
    */
   public ControllerTracingCondition() {
      super(ConfigurationPhase.PARSE_CONFIGURATION);
   }

   /**
    * The Class ControllerTracingEnabled.
    */
   @ConditionalOnProperty(
      prefix = "controllers",
      name = {"tracing.enabled"},
      havingValue = "true"
   )
   public static class ControllerTracingEnabled {
   }

   /**
    * The Class TracingEnabled.
    */
   @Conditional({EnabledTracingCondition.class})
   public static class TracingEnabled {
   }
}

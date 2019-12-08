package eu.sia.meda.tracing.config.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class MockedTracingCondition.
 */
public class MockedTracingCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new mocked tracing condition.
    */
   public MockedTracingCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class TracingMocked.
    */
   @ConditionalOnProperty(
      prefix = "tracing",
      name = {"mocked"},
      havingValue = "true",
      matchIfMissing = true
   )
   public static class TracingMocked {
   }

   /**
    * The Class TracingEnabled.
    */
   @Conditional({EnabledTracingCondition.class})
   public static class TracingEnabled {
   }
}

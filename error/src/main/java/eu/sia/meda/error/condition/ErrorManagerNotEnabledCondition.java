package eu.sia.meda.error.condition;

import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.Conditional;

/**
 * The Class ErrorManagerNotEnabledCondition.
 */
public class ErrorManagerNotEnabledCondition extends NoneNestedConditions {
   
   /**
    * Instantiates a new error manager not enabled condition.
    */
   public ErrorManagerNotEnabledCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class ErrorManagerEnabled.
    */
   @Conditional({ErrorManagerEnabledCondition.class})
   private static class ErrorManagerEnabled {
   }
}

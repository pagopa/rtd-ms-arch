/*
 * 
 */
package eu.sia.meda.error.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class LocalErrorManagerCondition.
 */
public class LocalErrorManagerCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new local error manager condition.
    */
   public LocalErrorManagerCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class MissingErrorManagerUrl.
    */
   @Conditional({MissingErrorManagerUrlCondition.class})
   private static class MissingErrorManagerUrl {
   }

   /**
    * The Class ErrorManagerEnabled.
    */
   @Conditional({ErrorManagerEnabledCondition.class})
   private static class ErrorManagerEnabled {
   }
}

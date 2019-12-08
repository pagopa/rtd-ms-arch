/*
 * 
 */
package eu.sia.meda.error.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class RemoteErrorManagerCondition.
 */
public class RemoteErrorManagerCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new remote error manager condition.
    */
   public RemoteErrorManagerCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class ErrorManagerUrl.
    */
   @Conditional({ErrorManagerUrlCondition.class})
   private static class ErrorManagerUrl {
   }

   /**
    * The Class ErrorManagerEnabled.
    */
   @Conditional({ErrorManagerEnabledCondition.class})
   private static class ErrorManagerEnabled {
   }
}

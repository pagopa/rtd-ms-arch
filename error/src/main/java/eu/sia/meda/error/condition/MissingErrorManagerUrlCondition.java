/*
 * 
 */
package eu.sia.meda.error.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class MissingErrorManagerUrlCondition.
 */
public class MissingErrorManagerUrlCondition extends NoneNestedConditions {
   
   /**
    * Instantiates a new missing error manager url condition.
    */
   public MissingErrorManagerUrlCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class ErrorManagerUrl.
    */
   @ConditionalOnProperty(
      prefix = "error-manager",
      name = {"url"}
   )
   private static class ErrorManagerUrl {
   }
}

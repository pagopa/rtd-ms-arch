/*
 * 
 */
package eu.sia.meda.transaction.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class EnabledPutDispoInfoCondition.
 */
public class EnabledPutDispoInfoCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new enabled put dispo info condition.
    */
   public EnabledPutDispoInfoCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class EnabledPutDispoInfo.
    */
   @ConditionalOnProperty(
      prefix = "transaction-manager",
      name = {"putDispoInfoEnabled"},
      havingValue = "true"
   )
   public static class EnabledPutDispoInfo {
   }

   /**
    * The Class TransactionManagerReal.
    */
   @Conditional({TransactionManagerRealCondition.class})
   public static class TransactionManagerReal {
   }

   /**
    * The Class ErrorManagerEnabled.
    */
   @Conditional({EnabledTransactionManagerCondition.class})
   public static class ErrorManagerEnabled {
   }
}

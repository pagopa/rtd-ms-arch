/*
 * 
 */
package eu.sia.meda.transaction.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class TransactionManagerRealCondition.
 */
public class TransactionManagerRealCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new transaction manager real condition.
    */
   public TransactionManagerRealCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class ErrorManagerMocked.
    */
   @ConditionalOnProperty(
      prefix = "transaction-manager",
      name = {"mocked"},
      havingValue = "false",
      matchIfMissing = true
   )
   public static class ErrorManagerMocked {
   }

   /**
    * The Class ErrorManagerEnabled.
    */
   @Conditional({EnabledTransactionManagerCondition.class})
   public static class ErrorManagerEnabled {
   }
}

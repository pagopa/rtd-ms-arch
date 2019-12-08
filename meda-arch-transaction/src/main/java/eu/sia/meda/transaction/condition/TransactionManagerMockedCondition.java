/*
 * 
 */
package eu.sia.meda.transaction.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;

/**
 * The Class TransactionManagerMockedCondition.
 */
public class TransactionManagerMockedCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new transaction manager mocked condition.
    */
   public TransactionManagerMockedCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class ErrorManagerSmartMocked.
    */
   @ConditionalOnProperty(
      prefix = "transaction-manager",
      name = {"mocked"},
      havingValue = "true"
   )
   public static class ErrorManagerSmartMocked {
   }

   /**
    * The Class ErrorManagerEnabled.
    */
   @Conditional({EnabledTransactionManagerCondition.class})
   public static class ErrorManagerEnabled {
   }
}

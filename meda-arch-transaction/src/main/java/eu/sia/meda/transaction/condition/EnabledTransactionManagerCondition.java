/*
 * 
 */
package eu.sia.meda.transaction.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * The Class EnabledTransactionManagerCondition.
 */
public class EnabledTransactionManagerCondition extends AllNestedConditions {
   
   /**
    * Instantiates a new enabled transaction manager condition.
    */
   public EnabledTransactionManagerCondition() {
      super(ConfigurationPhase.REGISTER_BEAN);
   }

   /**
    * The Class TransactionEnabled.
    */
   @ConditionalOnProperty(
      prefix = "transaction-manager",
      name = {"enabled"},
      havingValue = "true"
   )
   public static class TransactionEnabled {
   }
}

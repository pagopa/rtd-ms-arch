/*
 * 
 */
package eu.sia.meda.transaction.config;

import eu.sia.meda.transaction.condition.TransactionManagerMockedCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class TransactionManagerMockConfig.
 */
@Configuration
@PropertySource(
   value = {"classpath:transaction-mock.properties"},
   name = "transactionManagerMockConfig"
)
@Conditional({TransactionManagerMockedCondition.class})
public class TransactionManagerMockConfig {
}

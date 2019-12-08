/*
 * 
 */
package eu.sia.meda.transaction.config;

import eu.sia.meda.transaction.condition.TransactionManagerRealCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class TransactionManagerConfig.
 */
@Configuration
@PropertySource(
   value = {"classpath:transaction.properties"},
   name = "transactionManagerConfig"
)
@Conditional({TransactionManagerRealCondition.class})
public class TransactionManagerConfig {
}

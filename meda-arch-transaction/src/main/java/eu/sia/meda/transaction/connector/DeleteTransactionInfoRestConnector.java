/*
 * 
 */
package eu.sia.meda.transaction.connector;

import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.transaction.condition.EnabledTransactionManagerCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class DeleteTransactionInfoRestConnector.
 */
@Service
@Conditional({EnabledTransactionManagerCondition.class})
public class DeleteTransactionInfoRestConnector extends BaseRestConnector<String, Void, Void, Void> {
}

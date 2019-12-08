/*
 * 
 */
package eu.sia.meda.transaction.connector;

import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.transaction.condition.EnabledTransactionManagerCondition;
import eu.sia.meda.transaction.resource.TransactionInfoResource;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * The Class GetTransactionInfoRestConnector.
 *
 * @param <T> the generic type
 */
@Service
@Scope("prototype")
@Conditional({EnabledTransactionManagerCondition.class})
public class GetTransactionInfoRestConnector<T> extends BaseRestConnector<String, T, Void, TransactionInfoResource<T>> {
}

/*
 * 
 */
package eu.sia.meda.transaction.connector;

import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.transaction.condition.EnabledTransactionManagerCondition;
import eu.sia.meda.transaction.dto.TransactionDispoDto;
import eu.sia.meda.transaction.model.TransactionInfoWithIdOp;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class SaveTransactionInfoRestConnector.
 *
 * @param <T> the generic type
 */
@Service
@Conditional({EnabledTransactionManagerCondition.class})
public class SaveTransactionInfoRestConnector<T> extends BaseRestConnector<TransactionInfoWithIdOp<T>, String, TransactionDispoDto, String> {
}

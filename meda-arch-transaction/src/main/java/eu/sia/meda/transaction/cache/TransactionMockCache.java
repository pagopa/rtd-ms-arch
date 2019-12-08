/*
 * 
 */
package eu.sia.meda.transaction.cache;

import eu.sia.meda.cache.BaseMedaCache;
import eu.sia.meda.transaction.condition.TransactionManagerMockedCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class TransactionMockCache.
 *
 * @param <T> the generic type
 */
@Service
@Conditional({TransactionManagerMockedCondition.class})
public class TransactionMockCache<T> extends BaseMedaCache<String, T> {
}

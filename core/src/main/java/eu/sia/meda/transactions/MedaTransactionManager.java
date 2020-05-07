package eu.sia.meda.transactions;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;

import eu.sia.meda.config.LoggerUtils;

/**
 * The Class MedaTransactionManager.
 */
public class MedaTransactionManager {

	/** The Constant log. */
	private static final Logger log = LoggerUtils.getLogger(MedaTransactionManager.class);

	/** The transaction step map. */
	private Map<String, Stack<TransactionStep>> transactionStepMap = new HashMap<>();

	/**
	 * Append step.
	 *
	 * @param transactionId   the transaction id
	 * @param transactionStep the transaction step
	 */
	public void appendStep(String transactionId, TransactionStep transactionStep) {
		Stack transactionStepStack;
		if (this.transactionStepMap.containsKey(transactionId)) {
			transactionStepStack = (Stack) this.transactionStepMap.get(transactionId);
		} else {
			transactionStepStack = new Stack();
		}

		transactionStepStack.push(transactionStep);
		this.transactionStepMap.put(transactionId, transactionStepStack);
	}

	/**
	 * Rollback.
	 *
	 * @param transactionId the transaction id
	 */
	public void rollback(String transactionId) {
		Stack steps = (Stack) this.transactionStepMap.get(transactionId);

		if (steps != null) {
			while (!steps.empty()) {
				TransactionStep step = (TransactionStep) steps.pop();
				if (log.isDebugEnabled()) {
					log.debug(LoggerUtils.formatArchRow("TransactionId:{} stepID {} perform rollback"), transactionId,
							step.getIdOperation());
				}
				step.getTransactionalCompensation().get();
			}
			if (log.isInfoEnabled()) {
				log.info(LoggerUtils.formatArchRow("TransactionId: {} rollBack True"), transactionId);
			}
		} else {
			if (log.isInfoEnabled()) {
				log.info(LoggerUtils.formatArchRow("TransactionId: {} rollBack not execute transactionStepMap is null"), transactionId);
			}
		}
	}

	/**
	 * Gets the transaction step map.
	 *
	 * @return the transaction step map
	 */
	public Map<String, Stack<TransactionStep>> getTransactionStepMap() {
		return this.transactionStepMap;
	}

	/**
	 * Sets the transaction step map.
	 *
	 * @param transactionStepMap the transaction step map
	 */
	public void setTransactionStepMap(Map<String, Stack<TransactionStep>> transactionStepMap) {
		this.transactionStepMap = transactionStepMap;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return "MedaTransactionManager(transactionStepMap=" + this.getTransactionStepMap() + ")";
	}
}

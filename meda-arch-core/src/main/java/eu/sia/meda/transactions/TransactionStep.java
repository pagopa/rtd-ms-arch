package eu.sia.meda.transactions;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * The Class TransactionStep.
 *
 * @param <T> the generic type
 * @param <F> the generic type
 */
public class TransactionStep<T, F> {

	/** The id operation. */
	private String idOperation;

	/** The transactional operation. */
	private Supplier<T> transactionalOperation;

	/** The transactional compensation. */
	private Supplier<F> transactionalCompensation;

	/**
	 * Instantiates a new transaction step.
	 *
	 * @param idOperation               the id operation
	 * @param transactionalOperation    the transactional operation
	 * @param transactionalCompensation the transactional compensation
	 */
	public TransactionStep(String idOperation, Supplier<T> transactionalOperation,
			Supplier<F> transactionalCompensation) {
		this.idOperation = idOperation;
		this.transactionalOperation = transactionalOperation;
		this.transactionalCompensation = transactionalCompensation;
	}

	/**
	 * Instantiates a new transaction step.
	 *
	 * @param transactionalOperation    the transactional operation
	 * @param transactionalCompensation the transactional compensation
	 */
	public TransactionStep(Supplier<T> transactionalOperation, Supplier<F> transactionalCompensation) {
		this.idOperation = UUID.randomUUID().toString();
		this.transactionalOperation = transactionalOperation;
		this.transactionalCompensation = transactionalCompensation;
	}

	/**
	 * Gets the id operation.
	 *
	 * @return the id operation
	 */
	public String getIdOperation() {
		return this.idOperation;
	}

	/**
	 * Gets the transactional operation.
	 *
	 * @return the transactional operation
	 */
	public Supplier<T> getTransactionalOperation() {
		return this.transactionalOperation;
	}

	/**
	 * Gets the transactional compensation.
	 *
	 * @return the transactional compensation
	 */
	public Supplier<F> getTransactionalCompensation() {
		return this.transactionalCompensation;
	}

	/**
	 * Sets the id operation.
	 *
	 * @param idOperation the new id operation
	 */
	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}

	/**
	 * Sets the transactional operation.
	 *
	 * @param transactionalOperation the new transactional operation
	 */
	public void setTransactionalOperation(Supplier<T> transactionalOperation) {
		this.transactionalOperation = transactionalOperation;
	}

	/**
	 * Sets the transactional compensation.
	 *
	 * @param transactionalCompensation the new transactional compensation
	 */
	public void setTransactionalCompensation(Supplier<F> transactionalCompensation) {
		this.transactionalCompensation = transactionalCompensation;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return "TransactionStep(idOperation=" + this.getIdOperation() + ", transactionalOperation="
				+ this.getTransactionalOperation() + ", transactionalCompensation="
				+ this.getTransactionalCompensation() + ")";
	}
}

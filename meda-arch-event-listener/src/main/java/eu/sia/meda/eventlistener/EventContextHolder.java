package eu.sia.meda.eventlistener;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import java.util.Objects;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;

/**
 * The Class EventContextHolder.
 */
public class EventContextHolder extends BaseContextHolder {

	/** The Constant log. */
	private static final Logger log = LoggerUtils.getLogger(EventContextHolder.class);

	/** The Constant record. */
	private static final InheritableThreadLocal<ConsumerRecord> record = new InheritableThreadLocal<>();

	/**
	 * Instantiates a new event context holder.
	 */
	protected EventContextHolder() {
	}

	/**
	 * Gets the record.
	 *
	 * @return the record
	 */
	public static ConsumerRecord getRecord() {
		return (ConsumerRecord) record.get();
	}

	/**
	 * Sets the record.
	 *
	 * @param rec the new record
	 */
	public static void setRecord(ConsumerRecord rec) {
		Objects.requireNonNull(rec, "ConsumerRecord cannot be null");
		if (record.get() != null) {
			throw new IllegalStateException("ConsumerRecord already initialized");
		} else {
			if (log.isDebugEnabled()) {
				log.debug(LoggerUtils.formatArchRow("Thread {}-{}, init ConsumerRecord variable"),
						Thread.currentThread().getName(), Thread.currentThread().getId());
			}
			record.set(rec);
		}
	}

	/**
	 * Force set record.
	 *
	 * @param rec the rec
	 */
	public static void forceSetRecord(ConsumerRecord rec) {
		Objects.requireNonNull(rec, "ConsumerRecord cannot be null");
		if (log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Forcing record setting on thread Thread {}-{}"),
					Thread.currentThread().getName(), Thread.currentThread().getId());
		}
		record.set(rec);
	}

	/**
	 * Clear.
	 */
	public static void clear() {
		try {
			BaseContextHolder.clear();
		} catch (Exception var2) {
			if (log.isWarnEnabled()) {
				log.warn(LoggerUtils.formatArchRow("Swallowed error while clearing User Context"), var2);
			}
		}

		try {
			record.remove();
		} catch (Exception var1) {
			if (log.isWarnEnabled()) {
				log.warn(LoggerUtils.formatArchRow("Swallowed error while clearing ConsumerRecord"), var1);
			}
		}
	}
}

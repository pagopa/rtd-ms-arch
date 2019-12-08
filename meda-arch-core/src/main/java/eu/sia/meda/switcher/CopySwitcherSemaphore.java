package eu.sia.meda.switcher;

/**
 * The Class CopySwitcherSemaphore.
 */
public class CopySwitcherSemaphore {

	/** The Constant MAX_LATENCY. */
	public static final Long MAX_LATENCY = Long.MAX_VALUE;

	/** The Constant DEFAULT_VALIDITY. */
	public static final long DEFAULT_VALIDITY = 60000L;

	/** The name. */
	private String name;

	/** The latency. */
	private long latency;

	/** The last update timestamp. */
	private long lastUpdateTimestamp;

	/** The enabled. */
	private boolean enabled;

	/** The millis of validity. */
	private long millisOfValidity;

	/**
	 * Instantiates a new copy switcher semaphore.
	 */
	public CopySwitcherSemaphore() {
		this.latency = MAX_LATENCY;
		this.lastUpdateTimestamp = System.currentTimeMillis();
		this.enabled = false;
		this.millisOfValidity = 60000L;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the latency.
	 *
	 * @return the latency
	 */
	public long getLatency() {
		return this.latency;
	}

	/**
	 * Sets the latency.
	 *
	 * @param latency the new latency
	 */
	public void setLatency(long latency) {
		this.latency = latency;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the last update timestamp.
	 *
	 * @return the last update timestamp
	 */
	public long getLastUpdateTimestamp() {
		return this.lastUpdateTimestamp;
	}

	/**
	 * Sets the last update timestamp.
	 *
	 * @param lastUpdateTimestamp the new last update timestamp
	 */
	public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("CopySwitcherSemaphore{");
		sb.append("name='").append(this.name).append('\'');
		sb.append(", latency=").append(this.latency);
		sb.append(", lastUpdateTimestamp=").append(this.lastUpdateTimestamp);
		sb.append(", enabled=").append(this.enabled);
		sb.append(", millisOfValidity=").append(this.millisOfValidity);
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Gets the millis of validity.
	 *
	 * @return the millis of validity
	 */
	public long getMillisOfValidity() {
		return this.millisOfValidity;
	}

	/**
	 * Sets the millis of validity.
	 *
	 * @param millisOfValidity the new millis of validity
	 */
	public void setMillisOfValidity(long millisOfValidity) {
		this.millisOfValidity = millisOfValidity;
	}
}

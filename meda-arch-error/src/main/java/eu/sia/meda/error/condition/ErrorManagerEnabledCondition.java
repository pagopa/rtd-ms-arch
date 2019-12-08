package eu.sia.meda.error.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * The Class ErrorManagerEnabledCondition.
 */
public class ErrorManagerEnabledCondition extends AllNestedConditions {

	/**
	 * Instantiates a new error manager enabled condition.
	 */
	public ErrorManagerEnabledCondition() {
		super(ConfigurationPhase.REGISTER_BEAN);
	}

	/**
	 * The Class ErrorManagerEnabled.
	 */
	@ConditionalOnProperty(prefix = "error-manager", name = { "enabled" }, havingValue = "true")
	private static class ErrorManagerEnabled {
	}
}

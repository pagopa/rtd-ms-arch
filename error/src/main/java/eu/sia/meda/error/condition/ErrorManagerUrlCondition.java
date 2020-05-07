package eu.sia.meda.error.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * The Class ErrorManagerUrlCondition.
 */
public class ErrorManagerUrlCondition extends AllNestedConditions {

	/**
	 * Instantiates a new error manager url condition.
	 */
	public ErrorManagerUrlCondition() {
		super(ConfigurationPhase.REGISTER_BEAN);
	}

	/**
	 * The Class ErrorManagerUrl.
	 */
	@ConditionalOnProperty(prefix = "error-manager", name = { "url" })
	private static class ErrorManagerUrl {
	}
}

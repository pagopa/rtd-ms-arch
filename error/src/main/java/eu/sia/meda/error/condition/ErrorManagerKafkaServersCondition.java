package eu.sia.meda.error.condition;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;

/**
 * The Class ErrorManagerKafkaServersCondition.
 */
public class ErrorManagerKafkaServersCondition extends AllNestedConditions {

	/**
	 * Instantiates a new error manager kafka servers condition.
	 */
	public ErrorManagerKafkaServersCondition() {
		super(ConfigurationPhase.REGISTER_BEAN);
	}

	/**
	 * The Class ErrorManagerKafkaServer.
	 */
	@ConditionalOnProperty(prefix = "error-manager", name = { "kafkaServers" })
	private static class ErrorManagerKafkaServer {
	}

	/**
	 * The Class RemoteErrorManager.
	 */
	@Conditional({ RemoteErrorManagerCondition.class })
	private static class RemoteErrorManager {
	}
}

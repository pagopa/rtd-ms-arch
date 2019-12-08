package eu.sia.meda.error.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import eu.sia.meda.exceptions.model.ErrorMessage;
import eu.sia.meda.service.BaseService;

/**
 * The Class ErrorManagerService.
 */
public abstract class ErrorManagerService extends BaseService {

	/** The Constant DEFAULT_MESSAGE. */
	private static final String DEFAULT_MESSAGE = "Unknown";

	/** The Constant DEFAULT_MESSAGE_TITLE. */
	private static final String DEFAULT_MESSAGE_TITLE = "Unknown";

	/** The Constant DEFAULT_ERROR_CODE. */
	private static final String DEFAULT_ERROR_CODE = "UNKNOWN";

	/**
	 * Gets the error messages.
	 *
	 * @param errorKeys       the error keys
	 * @param defaultSeverity the default severity
	 * @return the error messages
	 */
	public abstract Map<String, List<ErrorMessage>> getErrorMessages(Set<String> errorKeys, String defaultSeverity);

	/**
	 * To severity map.
	 *
	 * @param errorKeys       the error keys
	 * @param errorMap        the error map
	 * @param defaultSeverity the default severity
	 * @return the map
	 */
	protected Map<String, List<ErrorMessage>> toSeverityMap(Set<String> errorKeys, Map<String, ErrorMessage> errorMap,
			String defaultSeverity) {
		return (Map) errorKeys.stream().map((k) -> {
			return this.getErrorMessageOrDefault(k, errorMap, defaultSeverity);
		}).collect(Collectors.groupingBy(ErrorMessage::getSeverity));
	}

	/**
	 * Gets the error message or default.
	 *
	 * @param errorKey        the error key
	 * @param errorMap        the error map
	 * @param defaultSeverity the default severity
	 * @return the error message or default
	 */
	private ErrorMessage getErrorMessageOrDefault(String errorKey, Map<String, ErrorMessage> errorMap,
			String defaultSeverity) {
		Objects.requireNonNull(errorKey, "null errorKey");
		Objects.requireNonNull(errorMap, "null errorMap");
		return errorMap.containsKey(errorKey.toLowerCase()) ? (ErrorMessage) errorMap.get(errorKey.toLowerCase())
				: this.getDefaultErrorMessage(errorKey.toLowerCase(), defaultSeverity);
	}

	/**
	 * Gets the default error message.
	 *
	 * @param errorKey        the error key
	 * @param defaultSeverity the default severity
	 * @return the default error message
	 */
	private ErrorMessage getDefaultErrorMessage(String errorKey, String defaultSeverity) {
		ErrorMessage message = new ErrorMessage();
		message.setMessageKey(errorKey);
		message.setSeverity(defaultSeverity);
		message.setMessage(DEFAULT_MESSAGE);
		message.setMessageTitle(DEFAULT_MESSAGE_TITLE);
		message.setErrorCode(DEFAULT_ERROR_CODE);
		message.setRetry(false);
		return message;
	}
}

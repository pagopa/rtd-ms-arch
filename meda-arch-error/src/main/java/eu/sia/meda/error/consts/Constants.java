package eu.sia.meda.error.consts;

/**
 * The Class Constants.
 */
public class Constants {

	/**
	 * Avoid instantiation
	 */
	private Constants() {
	}

	/** The Constant GENERIC_ERROR_KEY. */
	public static final String GENERIC_ERROR_KEY = "generic.error";

	/** The Constant MISSING_PARAMETER_ERROR_KEY. */
	public static final String MISSING_PARAMETER_ERROR_KEY = "missing.parameter.error";

	/** The Constant MISSING_REQUEST_PART_ERROR_KEY. */
	public static final String MISSING_REQUEST_PART_ERROR_KEY = "missing.request.part.error";

	/** The Constant MISSING_PATH_VARIABLE_ERROR_KEY. */
	public static final String MISSING_PATH_VARIABLE_ERROR_KEY = "missing.path.variable.error";

	/** The Constant MISSING_BODY_ERROR_KEY. */
	public static final String MISSING_BODY_ERROR_KEY = "missing.body.error";

	/** The Constant UNSUPPORTED_MEDIA_TYPE_ERROR_KEY. */
	public static final String UNSUPPORTED_MEDIA_TYPE_ERROR_KEY = "unsupported.media.type.error";

	/** The Constant UNSUPPORTED_METHOD_ERROR_KEY. */
	public static final String UNSUPPORTED_METHOD_ERROR_KEY = "unsupported.method.error";

	/** The Constant NOT_ACCEPTABLE_MEDIA_TYPE_ERROR_KEY. */
	public static final String NOT_ACCEPTABLE_MEDIA_TYPE_ERROR_KEY = "not.acceptable.media.type.error";

	/** The Constant ERROR_MANAGER_PROPERTY_PREFIX. */
	public static final String ERROR_MANAGER_PROPERTY_PREFIX = "error-manager";

	/** The Constant ERROR_MANAGER_ENABLED_SUBPROP. */
	public static final String ERROR_MANAGER_ENABLED_SUBPROP = "enabled";

	/** The Constant ERROR_MANAGER_URL_SUBPROP. */
	public static final String ERROR_MANAGER_URL_SUBPROP = "url";

	/** The Constant ERROR_MANAGER_KAFKA_SERVERS_SUBPROP. */
	public static final String ERROR_MANAGER_KAFKA_SERVERS_SUBPROP = "kafkaServers";
}

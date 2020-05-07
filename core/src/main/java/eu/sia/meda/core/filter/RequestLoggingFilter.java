package eu.sia.meda.core.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import eu.sia.meda.config.LoggerUtils;

/**
 * The Class RequestLoggingFilter.
 */
@Configuration
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

	/** The Constant log. */
	private static final Logger log = LoggerUtils.getLogger(RequestLoggingFilter.class);

	/** The Constant PRE. */
	public static final String PRE = "pre";

	/** The Constant POST. */
	public static final String POST = "post";

	/** The Constant BOTH. */
	public static final String BOTH = "both";

	/** The enable request logging. */
	@Value("${meda.core.enable.request.logging}")
	private String enableRequestLogging;

	/** The enable request event. */
	@Value("${meda.core.enable.request.event}")
	private String enableRequestEvent;

	/** The include body. */
	@Value("${meda.core.request.logging.body}")
	private boolean includeBody;

	/** The max payload length. */
	@Value("${meda.request.logging.max.length:300}")
	private int maxPayloadLength;

	/**
	 * Inits the filter bean.
	 *
	 * @throws ServletException the servlet exception
	 */
	@Override
	protected void initFilterBean() throws ServletException {
		if (log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Initializing filter {} - Logging Enabled: {} Event Enabled: {}"),
					RequestLoggingFilter.class, this.enableRequestLogging, this.enableRequestEvent);
		}
		this.setIncludeHeaders(true);
		this.setIncludePayload(this.includeBody);
		this.setIncludeQueryString(true);
		this.setMaxPayloadLength(this.maxPayloadLength);
	}

	/**
	 * Before request.
	 *
	 * @param request the request
	 * @param message the message
	 */
	protected void beforeRequest(HttpServletRequest request, String message) {
		if (this.isRequestLoggingEnabled() && log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Processing incoming message: {}"), message);
		}

		if (this.isRequestEventEnabled() && log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Processing incoming event: {}"), message);
		}

	}

	/**
	 * After request.
	 *
	 * @param request the request
	 * @param message the message
	 */
	protected void afterRequest(HttpServletRequest request, String message) {
		if (this.isResponseLoggingEnabled() && log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Processing outgoing message: {}"), message);
		}

		if (this.isResponseEventEnabled() && log.isDebugEnabled()) {
			log.debug(LoggerUtils.formatArchRow("Processing outgoing event: {}"), message);
		}

	}

	/**
	 * Checks if is request logging enabled.
	 *
	 * @return true, if is request logging enabled
	 */
	private boolean isRequestLoggingEnabled() {
		return "pre".equals(this.enableRequestLogging) || "both".equals(this.enableRequestLogging);
	}

	/**
	 * Checks if is response logging enabled.
	 *
	 * @return true, if is response logging enabled
	 */
	private boolean isResponseLoggingEnabled() {
		return "post".equals(this.enableRequestLogging) || "both".equals(this.enableRequestLogging);
	}

	/**
	 * Checks if is request event enabled.
	 *
	 * @return true, if is request event enabled
	 */
	private boolean isRequestEventEnabled() {
		return "pre".equals(this.enableRequestEvent) || "both".equals(this.enableRequestEvent);
	}

	/**
	 * Checks if is response event enabled.
	 *
	 * @return true, if is response event enabled
	 */
	private boolean isResponseEventEnabled() {
		return "post".equals(this.enableRequestEvent) || "both".equals(this.enableRequestEvent);
	}
}

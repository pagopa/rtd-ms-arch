package eu.sia.meda.core.interceptors.utils;

import com.google.common.base.Strings;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

/**
 * The Class MedaRequestAttributes.
 */
public class MedaRequestAttributes {

	/**
	 * Avoid instantiation
	 */
	private MedaRequestAttributes() {

	}

	/** The Constant REQUEST_ID. */
	public static final String REQUEST_ID = "x-request-id";

	/** The Constant TRANSACTION_ID. */
	public static final String TRANSACTION_ID = "x-transaction-id";
	public static final String ORIGIN_APP = "x-originapp";

	/**
	 * Gets the request id.
	 *
	 * @param request the request
	 * @return the request id
	 */
	public static String getRequestId(HttpServletRequest request) {
		ensureRequestId(request);
		return String.valueOf(request.getAttribute(REQUEST_ID));
	}

	/**
	 * Should generate request id.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	public static boolean shouldGenerateRequestId(HttpServletRequest request) {
		return Strings.isNullOrEmpty(request.getHeader(REQUEST_ID));
	}

	/**
	 * Ensure request id.
	 *
	 * @param request the request
	 */
	private static void ensureRequestId(HttpServletRequest request) {
		if (request.getAttribute(REQUEST_ID) == null) {
			String requestId = shouldGenerateRequestId(request) ? UUID.randomUUID().toString()
					: request.getHeader(REQUEST_ID);
			request.setAttribute(REQUEST_ID, requestId);
		}

	}

	/**
	 * Should generate transaction id.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	public static boolean shouldGenerateTransactionId(HttpServletRequest request) {
		return Strings.isNullOrEmpty(request.getHeader(TRANSACTION_ID));
	}

	/**
	 * Gets the transaction id.
	 *
	 * @param request the request
	 * @return the transaction id
	 */
	public static String getTransactionId(HttpServletRequest request) {
		ensureTransactionId(request);
		return String.valueOf(request.getAttribute(TRANSACTION_ID));
	}

	/**
	 * Ensure transaction id.
	 *
	 * @param request the request
	 */
	private static void ensureTransactionId(HttpServletRequest request) {
		if (request.getAttribute(TRANSACTION_ID) == null) {
			String requestId = shouldGenerateTransactionId(request) ? UUID.randomUUID().toString()
					: request.getHeader(TRANSACTION_ID);
			request.setAttribute(TRANSACTION_ID, requestId);
		}

	}

	/**
	 * Gets the originApp.
	 *
	 * @param request the request
	 * @return the application that orginated the request
	 */
	public static String getOriginApp(HttpServletRequest request) {
		ensureOriginApp(request);
		return String.valueOf(request.getAttribute(ORIGIN_APP));
	}

	/**
	 * Ensure transaction id.
	 *
	 * @param request the request
	 */
	private static void ensureOriginApp(HttpServletRequest request) {
		if (request.getAttribute(ORIGIN_APP) == null) {
			String originApp = shouldGenerateOriginApp(request) ? "UNKNOWN"
					: request.getHeader(ORIGIN_APP);
			request.setAttribute(ORIGIN_APP, originApp);
		}

	}

	/**
	 * Should generate transaction id.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	public static boolean shouldGenerateOriginApp(HttpServletRequest request) {
		return Strings.isNullOrEmpty(request.getHeader(ORIGIN_APP));
	}
}

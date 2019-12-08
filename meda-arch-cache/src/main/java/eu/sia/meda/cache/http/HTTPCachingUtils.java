package eu.sia.meda.cache.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class HTTPCachingUtils.
 */
public class HTTPCachingUtils {

	/** The Constant SET_E_TAG. */
	public static final String SET_E_TAG = "SET_E_TAG";

	/** The Constant CACHE_CONTROL_HEADER. */
	private static final String CACHE_CONTROL_HEADER = "Cache-Control";

	/** The Constant EXPIRES_HEADER. */
	private static final String EXPIRES_HEADER = "Expires";

	/**
	 * Instantiates a new HTTP caching utils.
	 */
	private HTTPCachingUtils() {
	}

	/**
	 * Sets the cache and etag.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param seconds  the seconds
	 */
	public static void setCacheAndEtag(HttpServletRequest request, HttpServletResponse response, long seconds) {
		request.setAttribute(SET_E_TAG, true);
		response.setHeader(CACHE_CONTROL_HEADER, "max-age=" + seconds + ", public");
		response.setDateHeader(EXPIRES_HEADER, System.currentTimeMillis() + seconds * 1000L);
	}

	/**
	 * Sets the no cache and etag.
	 *
	 * @param request  the request
	 * @param response the response
	 */
	public static void setNoCacheAndEtag(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(SET_E_TAG, true);
		response.setHeader(CACHE_CONTROL_HEADER, "no-http, max-age=0, must-revalidate");
		response.setHeader(EXPIRES_HEADER, "0");
	}

	/**
	 * Sets the cache and no etag.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param seconds  the seconds
	 */
	public static void setCacheAndNoEtag(HttpServletRequest request, HttpServletResponse response, long seconds) {
		request.setAttribute(SET_E_TAG, false);
		response.setHeader(CACHE_CONTROL_HEADER, "max-age=" + seconds + ", public");
		response.setDateHeader(EXPIRES_HEADER, System.currentTimeMillis() + seconds * 1000L);
	}

	/**
	 * Sets the no cache and no E tag.
	 *
	 * @param request  the request
	 * @param response the response
	 */
	public static void setNoCacheAndNoETag(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(SET_E_TAG, false);
		response.setHeader(CACHE_CONTROL_HEADER, "no-http, max-age=0, must-revalidate");
		response.setHeader(EXPIRES_HEADER, "0");
	}
}

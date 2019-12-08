package eu.sia.meda.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * The Class CookieUtils.
 */
public class CookieUtils {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

	/** The Constant DEFAULT_PATH. */
	private static final String DEFAULT_PATH = "/";

	/**
	 * Prevent instantiation of a new cookie utils.
	 */
	private CookieUtils() {
	}

	/**
	 * Gets the base 64 decoded cookie value.
	 *
	 * @param name        the name
	 * @param httpHeaders the http headers
	 * @return the base 64 decoded cookie value
	 */
	public static String getBase64DecodedCookieValue(String name, HttpHeaders httpHeaders) {
		String base64Cookie = getCookieValue(name, httpHeaders);
		if (base64Cookie == null) {
			return null;
		} else {
			byte[] decodedCookie = Base64.getDecoder().decode(base64Cookie);
			return new String(decodedCookie, StandardCharsets.UTF_8);
		}
	}

	/**
	 * Gets the cookie value.
	 *
	 * @param name        the name
	 * @param httpHeaders the http headers
	 * @return the cookie value
	 */
	public static String getCookieValue(String name, HttpHeaders httpHeaders) {
		List<String> cookies = httpHeaders.get("Cookie");
		return cookies != null && !cookies.isEmpty() ? getCookieValue(name, cookies) : null;
	}

	/**
	 * Gets the cookie value.
	 *
	 * @param name          the name
	 * @param cookieHeaders the cookie headers
	 * @return the cookie value
	 */
	public static String getCookieValue(String name, List<String> cookieHeaders) {
		return getCookieValue(name, cookieHeaders.stream().flatMap(CookieUtils::splitCookieFields));
	}

	/**
	 * Gets the cookie value.
	 *
	 * @param name         the name
	 * @param cookieHeader the cookie header
	 * @return the cookie value
	 */
	public static String getCookieValue(String name, String cookieHeader) {
		return getCookieValue(name, splitCookieFields(cookieHeader));
	}

	/**
	 * Delete cookie.
	 *
	 * @param name        the name
	 * @param httpHeaders the http headers
	 * @return the http headers
	 */
	public static HttpHeaders deleteCookie(String name, HttpHeaders httpHeaders) {
		return deleteCookie(name, DEFAULT_PATH, httpHeaders);
	}

	/**
	 * Delete cookie.
	 *
	 * @param name        the name
	 * @param path        the path
	 * @param httpHeaders the http headers
	 * @return the http headers
	 */
	public static HttpHeaders deleteCookie(String name, String path, HttpHeaders httpHeaders) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		httpHeaders.add("Set-Cookie", createSetCookieDeleteContent(name, path));
		return httpHeaders;
	}

	/**
	 * Delete cookie.
	 *
	 * @param name     the name
	 * @param response the response
	 */
	public static void deleteCookie(String name, HttpServletResponse response) {
		deleteCookie(name, DEFAULT_PATH, response);
	}

	/**
	 * Delete cookie.
	 *
	 * @param name     the name
	 * @param path     the path
	 * @param response the response
	 */
	public static void deleteCookie(String name, String path, HttpServletResponse response) {
		response.setHeader("Set-Cookie", createSetCookieDeleteContent(name, path));
	}

	/**
	 * Creates the set cookie delete content.
	 *
	 * @param name the name
	 * @param path the path
	 * @return the string
	 */
	private static String createSetCookieDeleteContent(String name, String path) {
		return name + "={}; Path=" + path + "; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Max-Age=0";
	}

	/**
	 * Gets the cookie value.
	 *
	 * @param name         the name
	 * @param cookieFields the cookie fields
	 * @return the cookie value
	 */
	private static String getCookieValue(String name, Stream<String> cookieFields) {
		String encodedCookie = cookieFields.filter((cookie) -> 
			cookie.contains("=")
		).map(cookie -> cookie.split("=", 2))
				.filter(cookieMap -> cookieMap.length == 2)
				.filter(cookieMap -> name.equalsIgnoreCase(cookieMap[0]))
				.map(cookieMap -> cookieMap[1]).findAny()
				.orElse(null);
		if (encodedCookie == null) {
			return null;
		} else {
			String decodedCookie = null;

			try {
				decodedCookie = URLDecoder.decode(encodedCookie, "UTF-8");
			} catch (UnsupportedEncodingException var5) {
				logger.error("Error with the encoding");
			}

			return decodedCookie;
		}
	}

	/**
	 * Split cookie fields.
	 *
	 * @param cookieHeader the cookie header
	 * @return the stream
	 */
	private static Stream<String> splitCookieFields(String cookieHeader) {
		return Arrays.stream(cookieHeader.split(";[ ]?"));
	}
}

package eu.sia.meda.core.interceptors.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * The Class MedaRequestAttributes.
 */
public class MedaRequestAttributes {

    /**
     * Avoid instantiation
     */
    private MedaRequestAttributes() {

    }

    /**
     * The Constant REQUEST_ID.
     */
    public static final String REQUEST_ID = "x-request-id";
    public static final String APIM_REQUEST_ID = "x-apim-request-id";

    /**
     * The Constant TRANSACTION_ID.
     */
    public static final String TRANSACTION_ID = "x-transaction-id";
    public static final String ORIGIN_APP = "x-originapp";
    public static final String USER_ID = "x-user-id";

    /**
     * Gets the app request id.
     *
     * @param request the request
     * @return the app request id
     */
    public static String getRequestId(HttpServletRequest request) {
        ensureRequestId(request);
        return String.valueOf(request.getAttribute(REQUEST_ID));
    }

    /**
     * Should generate app request id.
     *
     * @param request the request
     * @return true, if successful
     */
    public static boolean shouldGenerateRequestId(HttpServletRequest request) {
        return !StringUtils.hasLength(request.getHeader(REQUEST_ID));
    }

    /**
     * Ensure app request id.
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
     * Gets the APIM request id.
     *
     * @param request the request
     * @return the APIM request id
     */
    public static String getApimRequestId(HttpServletRequest request) {
        ensureApimRequestId(request);
        return String.valueOf(request.getAttribute(APIM_REQUEST_ID));
    }

    /**
     * Should generate APIM request id.
     *
     * @param request the request
     * @return true, if successful
     */
    public static boolean shouldGenerateApimRequestId(HttpServletRequest request) {
        return !StringUtils.hasLength(request.getHeader(APIM_REQUEST_ID));
    }

    /**
     * Ensure APIM request id.
     *
     * @param request the request
     */
    private static void ensureApimRequestId(HttpServletRequest request) {
        if (request.getAttribute(APIM_REQUEST_ID) == null) {
            String requestId = shouldGenerateApimRequestId(request) ? "NONE"
                    : request.getHeader(APIM_REQUEST_ID);
            request.setAttribute(APIM_REQUEST_ID, requestId);
        }
    }

    /**
     * Should generate transaction id.
     *
     * @param request the request
     * @return true, if successful
     */
    public static boolean shouldGenerateTransactionId(HttpServletRequest request) {
        return !StringUtils.hasLength(request.getHeader(TRANSACTION_ID));
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
        return !StringUtils.hasLength(request.getHeader(ORIGIN_APP));
    }

    /**
     * Gets the user id.
     *
     * @param request the request
     * @return the application that orginated the request
     */
    public static String getUserId(HttpServletRequest request) {
        ensureUserId(request);
        return String.valueOf(request.getAttribute(USER_ID));
    }

    /**
     * Ensure user id.
     *
     * @param request the request
     */
    private static void ensureUserId(HttpServletRequest request) {
        if (request.getAttribute(USER_ID) == null) {
            String originApp = shouldGenerateUserId(request) ? "anonymousUser"
                    : request.getHeader(USER_ID);
            request.setAttribute(USER_ID, originApp);
        }

    }

    /**
     * Should generate user id.
     *
     * @param request the request
     * @return true, if successful
     */
    public static boolean shouldGenerateUserId(HttpServletRequest request) {
        return !StringUtils.hasLength(request.getHeader(USER_ID));
    }
}

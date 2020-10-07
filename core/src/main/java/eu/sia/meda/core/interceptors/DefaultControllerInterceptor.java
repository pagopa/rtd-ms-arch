package eu.sia.meda.core.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.controller.BaseController;
import eu.sia.meda.core.interceptors.utils.MedaRequestAttributes;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.AuthorizationContext;
import eu.sia.meda.core.model.ErrorContext;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;
import springfox.documentation.swagger.web.ApiResourceController;

/**
 * The Class DefaultControllerInterceptor.
 */
@Component
public class DefaultControllerInterceptor implements HandlerInterceptor {

	/** The Constant CALLER. */
	public static final String CALLER = "caller";

	/** The Constant APPLICATION_NAME. */
	public static final String APPLICATION_NAME = "applicationname";

	/** The Constant CLIENT_VERSION. */
	public static final String CLIENT_VERSION = "clientversion";

	/** The Constant CHANNEL. */
	public static final String CHANNEL = "channel";

	/** The Constant LANG. */
	public static final String LANG = "lang";

	/** The Constant log. */
	private static final Logger log = LoggerUtils.getLogger(DefaultControllerInterceptor.class);

	/** The load session context. */
	@Value("${meda.core.sessioncontext.enabled:true}")
	private boolean loadSessionContext;

	/** The is security enabled. */
	@Value("${keycloak.enabled:false}")
	private boolean isSecurityEnabled;

	/** The project id. */
	@Value("${spring.application.name}")
	private String projectId;


	/**
	 * Pre handle.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			if (log.isDebugEnabled()) {
				log.debug(LoggerUtils.formatArchRow("Not a valid method handler {}"), handler.getClass().getName());
			}
			return true;
		} else {
			Object controller = ((HandlerMethod) handler).getBean();
			if (!this.isValidRequest(request, (HandlerMethod) handler)) {
				BaseContextHolder.setErrorContext(new ErrorContext(this.projectId));
				throw new MedaDomainRuntimeException("Request not valid", "ERR001", HttpStatus.BAD_REQUEST);
			} else {
				try {
					RequestContextHolder.setRequest(request);
					BaseContextHolder.setApplicationContext(this.loadApplicationContext(request));
					BaseContextHolder.setAuthorizationContext(this.loadAuthorizationContext(request));
					BaseContextHolder.setErrorContext(new ErrorContext(this.projectId));

					return true;
				} catch (Exception var6) {
					BaseContextHolder.clear();
					throw var6;
				}
			}
		}
	}

	/**
	 * After completion.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @param ex       the ex
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		RequestContextHolder.clear();
		if (MedaRequestAttributes.shouldGenerateRequestId(request)) {
			response.setHeader("x-request-id", String.valueOf(request.getAttribute("x-request-id")));
		}

	}

	/**
	 * Checks if is valid request.
	 *
	 * @param request the request
	 * @param handler the handler
	 * @return true, if is valid request
	 */
	private boolean isValidRequest(HttpServletRequest request, HandlerMethod handler) {
		Object controller = handler.getBean();
		if (!(controller instanceof ErrorController) && !(controller instanceof ApiResourceController)) {
			if (!(controller instanceof BaseController)) {
				if (log.isErrorEnabled()) {
					log.error(LoggerUtils.formatArchRow("Handler {} does not extend {}. Request is suppressed"),
							handler, BaseController.class);
				}
				return false;
			} else {
				if (!MedaRequestAttributes.shouldGenerateTransactionId(request)) {
				}

				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Load application context.
	 *
	 * @param request the request
	 * @return the application context
	 */
	private ApplicationContext loadApplicationContext(HttpServletRequest request) {
		ApplicationContext applicationContext = new ApplicationContext();
		applicationContext.setRequestId(MedaRequestAttributes.getRequestId(request));
		applicationContext.setTransactionId(MedaRequestAttributes.getTransactionId(request));
		applicationContext.setOriginApp(MedaRequestAttributes.getOriginApp(request));

		applicationContext.buildDefaultCopyHeader();
		return applicationContext;
	}

	/**
	 * Load authorization context.
	 *
	 * @param request the request
	 * @return the authorization context
	 */
	private AuthorizationContext loadAuthorizationContext(HttpServletRequest request) {
		AuthorizationContext authorizationContext = new AuthorizationContext();
		if (this.isSecurityEnabled) {
			String authorization = request.getHeader("authorization");
			if (authorization != null) {
//            KeycloakAuthenticationToken authenticationToken = (KeycloakAuthenticationToken)request.getUserPrincipal();
//            KeycloakPrincipal principal = (KeycloakPrincipal)authenticationToken.getPrincipal();
//            KeycloakSecurityContext securityContext = principal.getKeycloakSecurityContext();
//            AccessToken accessToken = securityContext.getToken();
//            Map<String, Object> claims = accessToken.getOtherClaims();
//            authorizationContext.setAuthorizationHeader(authorization);
//            authorizationContext.setAdditionalClaims(claims);
				// log.debug("Additional claims: {}", claims);
			}
		}

		return authorizationContext;
	}

}

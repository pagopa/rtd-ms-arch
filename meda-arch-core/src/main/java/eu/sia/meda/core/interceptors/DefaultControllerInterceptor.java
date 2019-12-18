package eu.sia.meda.core.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.controller.BaseController;
import eu.sia.meda.core.controller.BaseStatefulController;
import eu.sia.meda.core.interceptors.utils.MedaRequestAttributes;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.AuthorizationContext;
import eu.sia.meda.core.model.ErrorContext;
import eu.sia.meda.core.model.SIAContext;
import eu.sia.meda.core.model.siaHeaders.ParamList;
import eu.sia.meda.core.model.siaHeaders.SIAWebservicesHeaderType;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;
import eu.sia.meda.service.SessionContextRetriever;
import springfox.documentation.swagger.web.ApiResourceController;

/**
 * The Class DefaultControllerInterceptor.
 */
@Component
public class DefaultControllerInterceptor implements HandlerInterceptor {

	/** The Constant SIA_TRANSACTION_ID. */
	public static final String SIA_TRANSACTION_ID = "SIAWebservicesHeader.RequestInfo.TransactionId";

	/** The Constant SIA_TIMESTAMP. */
	public static final String SIA_TIMESTAMP = "SIAWebservicesHeader.RequestInfo.Timestamp";

	/** The Constant SIA_SERVICE_VERSION. */
	public static final String SIA_SERVICE_VERSION = "SIAWebservicesHeader.RequestInfo.ServiceVersion";

	/** The Constant SIA_SERVICE_ID. */
	public static final String SIA_SERVICE_ID = "SIAWebservicesHeader.RequestInfo.ServiceID";

	/** The Constant SIA_LANGUAGE. */
	public static final String SIA_LANGUAGE = "SIAWebservicesHeader.RequestInfo.Language";

	/** The Constant SIA_IS_VIRTUAL_USER. */
	public static final String SIA_IS_VIRTUAL_USER = "SIAWebservicesHeader.OperatorInfo.ISVirtualUser";

	/** The Constant SIA_USER_ID. */
	public static final String SIA_USER_ID = "SIAWebservicesHeader.OperatorInfo.UserID";

	/** The Constant SIA_CUSTOMER_ID. */
	public static final String SIA_CUSTOMER_ID = "SIAWebservicesHeader.BusinessInfo.CustomerID";

	/** The Constant SIA_BUSINESS_FILE_ID. */
	public static final String SIA_BUSINESS_FILE_ID = "SIAWebservicesHeader.BusinessInfo.BusinessFileID";

	/** The Constant SIA_BUSINESS_OPERATION. */
	public static final String SIA_BUSINESS_OPERATION = "SIAWebservicesHeader.BusinessInfo.BusinessOperation";

	/** The Constant SIA_BUSINESS_PROCESS_ID. */
	public static final String SIA_BUSINESS_PROCESS_ID = "SIAWebservicesHeader.BusinessInfo.BusinessProcessID";

	/** The Constant SIA_BUSINESS_PROCESS_NAME. */
	public static final String SIA_BUSINESS_PROCESS_NAME = "SIAWebservicesHeader.BusinessInfo.BusinessProcessName";

	/** The Constant SIA_CALLER_COMPANY_ID_CODE. */
	public static final String SIA_CALLER_COMPANY_ID_CODE = "SIAWebservicesHeader.CompanyInfo.SIACallerCompanyIDCode";

	/** The Constant SIA_SERVICE_COMPANY_ID_CODE. */
	public static final String SIA_SERVICE_COMPANY_ID_CODE = "SIAWebservicesHeader.CompanyInfo.SIAServiceCompanyIDCode";

	/** The Constant SIA_NOT_SIA_COMPANY_ID_CODE. */
	public static final String SIA_NOT_SIA_COMPANY_ID_CODE = "SIAWebservicesHeader.CompanyInfo.NotSIACompanyIDCode";

	/** The Constant SIA_SIA_BRANCH_CODE. */
	public static final String SIA_SIA_BRANCH_CODE = "SIAWebservicesHeader.CompanyInfo.SIABranchCode";

	/** The Constant SIA_CHANNEL_ID_CODE. */
	public static final String SIA_CHANNEL_ID_CODE = "SIAWebservicesHeader.TechnicalInfo.ChannelIDCode";

	/** The Constant SIA_APPLICATION_ID. */
	public static final String SIA_APPLICATION_ID = "SIAWebservicesHeader.TechnicalInfo.ApplicationID";

	/** The Constant SIA_CALLER_PROGRAM_NAME. */
	public static final String SIA_CALLER_PROGRAM_NAME = "SIAWebservicesHeader.TechnicalInfo.CallerProgramName";

	/** The Constant SIA_CALLER_SERVER_NAME. */
	public static final String SIA_CALLER_SERVER_NAME = "SIAWebservicesHeader.TechnicalInfo.CallerServerName";

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

	/** The session context retriever. */
	@Autowired
	private SessionContextRetriever sessionContextRetriever;

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
					BaseContextHolder.setSiaContext(this.loadSIAContext(request));
					BaseContextHolder.setErrorContext(new ErrorContext(this.projectId));
					if (controller instanceof BaseStatefulController && this.loadSessionContext) {
						BaseContextHolder.setSessionContext(this.sessionContextRetriever.loadSessionContext(request));
					}

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

	/**
	 * Load SIA context.
	 *
	 * @param request the request
	 * @return the SIA context
	 */
	private SIAContext loadSIAContext(HttpServletRequest request) {
		SIAContext siaContext = new SIAContext();
		SIAWebservicesHeaderType.RequestInfo requestInfo = new SIAWebservicesHeaderType.RequestInfo();
		if (request.getHeader("SIAWebservicesHeader.RequestInfo.TransactionId") != null) {
			requestInfo.setTransactionId(
					String.valueOf(request.getHeader("SIAWebservicesHeader.RequestInfo.TransactionId")));
		}

		if (request.getHeader("SIAWebservicesHeader.RequestInfo.Timestamp") != null) {
			requestInfo.setTimestamp(
					Long.parseLong(String.valueOf(request.getHeader("SIAWebservicesHeader.RequestInfo.Timestamp"))));
		}

		if (request.getHeader("SIAWebservicesHeader.RequestInfo.ServiceVersion") != null) {
			requestInfo.setServiceVersion(
					String.valueOf(request.getHeader("SIAWebservicesHeader.RequestInfo.ServiceVersion")));
		}

		if (request.getHeader("SIAWebservicesHeader.RequestInfo.ServiceID") != null) {
			requestInfo.setServiceID(String.valueOf(request.getHeader("SIAWebservicesHeader.RequestInfo.ServiceID")));
		}

		if (request.getHeader("SIAWebservicesHeader.RequestInfo.Language") != null) {
			requestInfo.setLanguage(String.valueOf(request.getHeader("SIAWebservicesHeader.RequestInfo.Language")));
		}

		SIAWebservicesHeaderType.OperatorInfo operatorInfo = new SIAWebservicesHeaderType.OperatorInfo();
		if (request.getHeader("SIAWebservicesHeader.OperatorInfo.ISVirtualUser") != null) {
			operatorInfo.setIsVirtualUser(Boolean
					.valueOf(String.valueOf(request.getHeader("SIAWebservicesHeader.OperatorInfo.ISVirtualUser"))));
		}

		if (request.getHeader("SIAWebservicesHeader.OperatorInfo.UserID") != null) {
			operatorInfo.setUserID(String.valueOf(request.getHeader("SIAWebservicesHeader.OperatorInfo.UserID")));
		}

		SIAWebservicesHeaderType.BusinessInfo businessInfo = new SIAWebservicesHeaderType.BusinessInfo();
		if (request.getHeader("SIAWebservicesHeader.BusinessInfo.CustomerID") != null) {
			businessInfo
					.setCustomerID(String.valueOf(request.getHeader("SIAWebservicesHeader.BusinessInfo.CustomerID")));
		}

		if (request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessFileID") != null) {
			businessInfo.setBusinessFileID(
					String.valueOf(request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessFileID")));
		}

		if (request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessOperation") != null) {
			businessInfo.setBusinessOperation(
					String.valueOf(request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessOperation")));
		}

		if (request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessProcessID") != null) {
			businessInfo.setBusinessProcessID(
					String.valueOf(request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessProcessID")));
		}

		if (request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessProcessName") != null) {
			businessInfo.setBusinessProcessName(
					String.valueOf(request.getHeader("SIAWebservicesHeader.BusinessInfo.BusinessProcessName")));
		}

		SIAWebservicesHeaderType.CompanyInfo companyInfo = new SIAWebservicesHeaderType.CompanyInfo();
		if (request.getHeader("SIAWebservicesHeader.CompanyInfo.SIACallerCompanyIDCode") != null) {
			companyInfo.setSIACallerCompanyIDCode(
					String.valueOf(request.getHeader("SIAWebservicesHeader.CompanyInfo.SIACallerCompanyIDCode")));
		}

		if (request.getHeader("SIAWebservicesHeader.CompanyInfo.SIAServiceCompanyIDCode") != null) {
			companyInfo.setSIAServiceCompanyIDCode(
					String.valueOf(request.getHeader("SIAWebservicesHeader.CompanyInfo.SIAServiceCompanyIDCode")));
		}

		if (request.getHeader("SIAWebservicesHeader.CompanyInfo.NotSIACompanyIDCode") != null) {
			companyInfo.setNotSIACompanyIDCode(
					String.valueOf(request.getHeader("SIAWebservicesHeader.CompanyInfo.NotSIACompanyIDCode")));
		}

		if (request.getHeader("SIAWebservicesHeader.CompanyInfo.SIABranchCode") != null) {
			companyInfo.setSIABranchCode(
					String.valueOf(request.getHeader("SIAWebservicesHeader.CompanyInfo.SIABranchCode")));
		}

		SIAWebservicesHeaderType.TechnicalInfo technicalInfo = new SIAWebservicesHeaderType.TechnicalInfo();
		if (request.getHeader("SIAWebservicesHeader.TechnicalInfo.ChannelIDCode") != null) {
			technicalInfo.setChannelIDCode(
					String.valueOf(request.getHeader("SIAWebservicesHeader.TechnicalInfo.ChannelIDCode")));
		}

		if (request.getHeader("SIAWebservicesHeader.TechnicalInfo.ApplicationID") != null) {
			technicalInfo.setApplicationID(
					String.valueOf(request.getHeader("SIAWebservicesHeader.TechnicalInfo.ApplicationID")));
		}

		if (request.getHeader("SIAWebservicesHeader.TechnicalInfo.CallerProgramName") != null) {
			technicalInfo.setCallerProgramName(
					String.valueOf(request.getHeader("SIAWebservicesHeader.TechnicalInfo.CallerProgramName")));
		}

		if (request.getHeader("SIAWebservicesHeader.TechnicalInfo.CallerServerName") != null) {
			technicalInfo.setCallerServerName(
					String.valueOf(request.getHeader("SIAWebservicesHeader.TechnicalInfo.CallerServerName")));
		}

		SIAWebservicesHeaderType.AdditionalBusinessInfo additionalBusinessInfo = new SIAWebservicesHeaderType.AdditionalBusinessInfo();
		ParamList[] var9 = ParamList.values();
		int var10 = var9.length;

		for (int var11 = 0; var11 < var10; ++var11) {
			ParamList paramList = var9[var11];
			if (request.getHeader("SIAWebservicesHeader.AdditionalBusinessInfo." + paramList.value()) != null) {
				SIAWebservicesHeaderType.AdditionalBusinessInfo.Param param = new SIAWebservicesHeaderType.AdditionalBusinessInfo.Param();
				param.setName(paramList);
				param.setValue(request.getHeader("SIAWebservicesHeader.AdditionalBusinessInfo." + paramList.value()));
				additionalBusinessInfo.getParam().add(param);
			}
		}

		SIAWebservicesHeaderType header = new SIAWebservicesHeaderType();
		header.setAdditionalBusinessInfo(additionalBusinessInfo);
		header.setTechnicalInfo(technicalInfo);
		header.setCompanyInfo(companyInfo);
		header.setBusinessInfo(businessInfo);
		header.setOperatorInfo(operatorInfo);
		header.setRequestInfo(requestInfo);
		siaContext.setHeader(header);
		return siaContext;
	}
}

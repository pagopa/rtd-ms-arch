package eu.sia.meda.error.pointcut;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.error.condition.ErrorManagerEnabledCondition;
import eu.sia.meda.error.service.ErrorManagerService;
import eu.sia.meda.exceptions.model.ErrorMessage;

/**
 * The Class ErrorResourcePointCut.
 */
@Configuration
@Aspect
@Conditional({ ErrorManagerEnabledCondition.class })
public class ErrorResourcePointCut {

	/** The Constant DEFAULT_SEVERITY. */
	private static final String DEFAULT_SEVERITY = "WARNING";

	/** The error manager service. */
	@Autowired
	private ErrorManagerService errorManagerService;

	/**
	 * Populate return messages.
	 *
	 * @param retVal the ret val
	 */
	@AfterReturning(pointcut = "target(eu.sia.meda.core.controller.BaseController)", returning = "retVal")
	public void populateReturnMessages(Object retVal) {
		if (!BaseContextHolder.getErrorContext().getErrorKeys().isEmpty()) {
			BaseResource resource = null;
			if (retVal instanceof BaseResource) {
				resource = (BaseResource) retVal;
			} else if (retVal instanceof ResponseEntity
					&& ((ResponseEntity) retVal).getBody() instanceof BaseResource) {
				resource = (BaseResource) ((ResponseEntity) retVal).getBody();
			}

			if (resource != null) {
				Map<String, List<ErrorMessage>> errorMap = this.errorManagerService
						.getErrorMessages(BaseContextHolder.getErrorContext().getErrorKeys(), DEFAULT_SEVERITY);
				resource.setReturnMessages(errorMap);
			}
		}

	}
}

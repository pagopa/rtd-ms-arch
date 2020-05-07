package eu.sia.meda.performance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Aspect for trace execution of Spring components (Controller, Service,
 * Repository) and add parameter with timestamp in http headers. The parameter
 * format is: [method_name(index)]_[start/end] = [timestamp [start/end] in
 * millis]
 *
 */
@Aspect
@Component
@Conditional(EnabledPerformanceAspect.class)
public class PerformanceAspect {

	private static final String MS_SUFFIX = " ms";
	private static final String END_SUFFIX = "_END";
	private static final String START_SUFFIX = "_START";
	private static final String PERFORMANCE_MAP_KEY = "performanceMap";
	private static final String COUNTER_MAP_KEY = "counterMap";

	private final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut("within(@org.springframework.stereotype.Repository *)"
			+ " || within(@org.springframework.stereotype.Service *)"
			+ " || within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @param e         exception
	 */
	@AfterThrowing(pointcut = "springBeanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		if (log.isDebugEnabled()) {
			log.debug("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
		}
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//				.getRequest();
//
//		if (request.getSession() != null) {
//			HttpHeaders performanceMap = (HttpHeaders) request.getSession().getAttribute(PERFORMANCE_MAP_KEY);
//			HashMap<String, Integer> counterMap = (HashMap<String, Integer>) request.getSession()
//					.getAttribute(COUNTER_MAP_KEY);
//			String methodName = joinPoint.getSignature().getName();
//			Integer index = (Objects.isNull(counterMap.get(methodName)) ? 0 : counterMap.get(methodName));
//			String methodNameIndex = methodName + index;
//			performanceMap.set(methodNameIndex + END_SUFFIX, System.currentTimeMillis() + MS_SUFFIX);
//		}

	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("springBeanPointcut()")
	public Object logHeadersAround(ProceedingJoinPoint joinPoint) throws Throwable {
		if (log.isDebugEnabled()) {
			log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}

		// check for put this information into an application context instead of request
		// session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpHeaders performanceMap = null;
		HashMap<String, Integer> counterMap = null;
		if (request.getSession() != null) {
			performanceMap = (HttpHeaders) request.getSession().getAttribute(PERFORMANCE_MAP_KEY);
			counterMap = (HashMap<String, Integer>) request.getSession().getAttribute(COUNTER_MAP_KEY);
		}
		if (Objects.isNull(performanceMap)) {
			performanceMap = new HttpHeaders();
		}
		if (Objects.isNull(counterMap)) {
			counterMap = new HashMap<>();
		}
		// index and methodname
		String methodName = joinPoint.getSignature().getName();
		Integer index = counterMap.get(methodName);
		index = (index == null) ? 0 : index+1;
		String methodNameIndex = methodName + index;
		performanceMap.set(methodNameIndex + START_SUFFIX, System.currentTimeMillis() + MS_SUFFIX);
		counterMap.put(methodName, index);
		// proceed with execution
		Object result = joinPoint.proceed();
		performanceMap.set(methodNameIndex + END_SUFFIX, System.currentTimeMillis() + MS_SUFFIX);

		// put performanceMap in session for next executions
		request.getSession().setAttribute(PERFORMANCE_MAP_KEY, performanceMap);
		request.getSession().setAttribute(COUNTER_MAP_KEY, counterMap);
		if (result instanceof ResponseEntity<?>) {
			ResponseEntity<?> re = (ResponseEntity<?>) result;

			// sum performance header with actual headers of response
			HttpHeaders newHeaders = new HttpHeaders(performanceMap);
			newHeaders.addAll(re.getHeaders());
			// recreate responseentity to add new performance headers
			result = new ResponseEntity<>(re.getBody(), newHeaders, re.getStatusCode());
		}

		if (log.isDebugEnabled()) {
			log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), result);
		}

		return result;

	}

}
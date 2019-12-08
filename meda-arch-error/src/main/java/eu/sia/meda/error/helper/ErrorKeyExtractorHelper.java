package eu.sia.meda.error.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.error.consts.Constants;
import eu.sia.meda.exceptions.IMedaDomainException;

/**
 * The Class ErrorKeyExtractorHelper.
 */
public class ErrorKeyExtractorHelper {

	/**
	 * Prevent instantiation of ErrorKeyExtractorHelper
	 */
	private ErrorKeyExtractorHelper() {
	}

	/** The Constant logger. */
	private static final Logger logger = LoggerUtils.getLogger(ErrorKeyExtractorHelper.class);

	/**
	 * Gets the constraint violation exception error keys.
	 *
	 * @param e the e
	 * @return the constraint violation exception error keys
	 */
	public static List<String> getConstraintViolationExceptionErrorKeys(ConstraintViolationException e) {
		try {
			if (e.getConstraintViolations() != null && !e.getConstraintViolations().isEmpty()) {
				List<String> errorKeys = new ArrayList<>();
				Iterator var2 = e.getConstraintViolations().iterator();

				while (var2.hasNext()) {
					ConstraintViolation cv = (ConstraintViolation) var2.next();
					errorKeys.add(getConstraintViolationErrorKey(cv));
				}

				return errorKeys;
			} else {
				return Arrays.asList(Constants.GENERIC_ERROR_KEY);
			}
		} catch (Exception var4) {
			logger.error("error in getConstraintViolationExceptionErrorKeys: {}", var4.getMessage());
			return Arrays.asList(Constants.GENERIC_ERROR_KEY);
		}
	}

	/**
	 * Gets the constraint violation error key.
	 *
	 * @param cv the cv
	 * @return the constraint violation error key
	 */
	private static String getConstraintViolationErrorKey(ConstraintViolation cv) {
		if (cv != null && cv.getRootBeanClass() != null && cv.getPropertyPath() != null
				&& cv.getConstraintDescriptor() instanceof ConstraintDescriptorImpl
				&& ((ConstraintDescriptorImpl) cv.getConstraintDescriptor()).getAnnotationDescriptor() != null
				&& ((ConstraintDescriptorImpl) cv.getConstraintDescriptor()).getAnnotationDescriptor()
						.getType() != null) {
			String[] splits = cv.getRootBeanClass().getName().split("\\.");
			String controllerName;
			if (splits.length <= 1) {
				controllerName = cv.getRootBeanClass().getName();
			} else {
				controllerName = splits[splits.length - 1];
			}

			String errorKey = controllerName + "." + cv.getPropertyPath() + "."
					+ getConstraintName(((ConstraintDescriptorImpl) cv.getConstraintDescriptor())
							.getAnnotationDescriptor().getType().getName());
			errorKey = cleanErrorKey(errorKey);
			return errorKey;
		} else {
			logger.error("unhandled ConstraintViolation");
			return Constants.GENERIC_ERROR_KEY;
		}
	}

	/**
	 * Gets the method argument not valid exception error keys.
	 *
	 * @param e the e
	 * @return the method argument not valid exception error keys
	 */
	public static List<String> getMethodArgumentNotValidExceptionErrorKeys(MethodArgumentNotValidException e) {
		try {
			if (e != null && e.getParameter().getMethod() != null && e.getParameter().getMethod().getName() != null) {
				String[] splits = e.getParameter().getContainingClass().getName().split("\\.");
				String controllerName;
				if (splits.length <= 1) {
					controllerName = e.getParameter().getContainingClass().getName();
				} else {
					controllerName = splits[splits.length - 1];
				}

				String methodName = e.getParameter().getMethod().getName();
				return getBindingResultErrorKeys(controllerName, methodName, e.getBindingResult());
			} else {
				logger.error("can not extract key from MethodArgumentNotValidException");
				return Arrays.asList(Constants.GENERIC_ERROR_KEY);
			}
		} catch (Exception var4) {
			logger.error("error in getMethodArgumentNotValidExceptionErrorKeys: {}", var4.getMessage());
			return Arrays.asList(Constants.GENERIC_ERROR_KEY);
		}
	}

	/**
	 * Gets the binding result error keys.
	 *
	 * @param controllerName the controller name
	 * @param methodName     the method name
	 * @param bindingResult  the binding result
	 * @return the binding result error keys
	 */
	private static List<String> getBindingResultErrorKeys(String controllerName, String methodName,
			BindingResult bindingResult) {
		if (bindingResult != null && !bindingResult.getFieldErrors().isEmpty()) {
			List<String> errorKeys = new ArrayList<>();
			Iterator<FieldError> var4 = bindingResult.getFieldErrors().iterator();

			while (var4.hasNext()) {
				FieldError fe = var4.next();
				errorKeys.add(getFieldErrorErrorKey(controllerName, methodName, fe));
			}

			return errorKeys;
		} else {
			logger.error("can not extract key from BindingResult");
			return Arrays.asList(Constants.GENERIC_ERROR_KEY);
		}
	}

	/**
	 * Gets the field error error key.
	 *
	 * @param controllerName the controller name
	 * @param methodName     the method name
	 * @param fe             the fe
	 * @return the field error error key
	 */
	private static String getFieldErrorErrorKey(String controllerName, String methodName, FieldError fe) {
		if (fe != null && fe.getObjectName() != null && fe.getField() != null) {
			ConstraintViolation cv;
			try {
				cv = (ConstraintViolation) fe.unwrap(ConstraintViolation.class);
			} catch (Exception var5) {
				logger.error("FieldError unwrap failed");
				return Constants.GENERIC_ERROR_KEY;
			}

			if (cv != null && cv.getConstraintDescriptor() instanceof ConstraintDescriptorImpl
					&& ((ConstraintDescriptorImpl) cv.getConstraintDescriptor()).getAnnotationDescriptor() != null
					&& ((ConstraintDescriptorImpl) cv.getConstraintDescriptor()).getAnnotationDescriptor()
							.getType() != null) {
				String errorKey = controllerName + "." + methodName + "." + fe.getObjectName() + "." + fe.getField()
						+ "." + getConstraintName(((ConstraintDescriptorImpl) cv.getConstraintDescriptor())
								.getAnnotationDescriptor().getType().getName());
				errorKey = cleanErrorKey(errorKey);
				return errorKey;
			} else {
				logger.error("can not extract key from ConstraintViolationImpl");
				return Constants.GENERIC_ERROR_KEY;
			}
		} else {
			logger.error("can not extract key from FieldError");
			return Constants.GENERIC_ERROR_KEY;
		}
	}

	/**
	 * Gets the constraint name.
	 *
	 * @param constraintFullName the constraint full name
	 * @return the constraint name
	 */
	private static String getConstraintName(String constraintFullName) {
		if (constraintFullName != null && !constraintFullName.isEmpty()) {
			String[] splits = constraintFullName.split("\\.");
			return splits[splits.length - 1];
		} else {
			return constraintFullName;
		}
	}

	/**
	 * Gets the meda domain exception error key.
	 *
	 * @param e the e
	 * @return the meda domain exception error key
	 */
	public static String getMedaDomainExceptionErrorKey(IMedaDomainException e) {
		if (e != null && e.getCode() != null && !e.getCode().isEmpty()) {
			return e.getCode();
		} else {
			logger.error("IMedaDomainException code not defined");
			return Constants.GENERIC_ERROR_KEY;
		}
	}

	/**
	 * Clean error key.
	 *
	 * @param errorKey the error key
	 * @return the string
	 */
	public static String cleanErrorKey(String errorKey) {
		return errorKey == null ? null : errorKey.replaceAll("\\[[0-9]+\\]", "");
	}
}

package eu.sia.meda.error.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import eu.sia.meda.exceptions.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class ErrorManagerService.
 */
@Slf4j
public abstract class ErrorManagerService {

    /**
     * The Constant DEFAULT_MESSAGE.
     */
    private static final String DEFAULT_MESSAGE = "Unknown";

    /**
     * The Constant DEFAULT_MESSAGE_TITLE.
     */
    private static final String DEFAULT_MESSAGE_TITLE = "Unknown";

    /**
     * The Constant DEFAULT_ERROR_CODE.
     */
    private static final String DEFAULT_ERROR_CODE = "UNKNOWN";

    /**
     * Gets the error messages.
     *
     * @param errorKeys       the error keys
     * @param defaultSeverity the default severity
     * @return the error messages
     */
    public abstract Map<String, List<ErrorMessage>> getErrorMessages(Set<String> errorKeys, String defaultSeverity);

    /**
     * To severity map.
     *
     * @param errorKeys       the error keys
     * @param errorMap        the error map
     * @param defaultSeverity the default severity
     * @return the map
     */
    protected Map<String, List<ErrorMessage>> toSeverityMap(Set<String> errorKeys, Map<String, ErrorMessage> errorMap,
                                                            String defaultSeverity) {
        return errorKeys.stream()
                .map((k) -> this.getErrorMessageOrDefault(k, errorMap, defaultSeverity))
                .collect(Collectors.groupingBy(ErrorMessage::getSeverity));
    }

    /**
     * Gets the error message or default.
     *
     * @param errorKey        the error key
     * @param errorMap        the error map
     * @param defaultSeverity the default severity
     * @return the error message or default
     */
    private ErrorMessage getErrorMessageOrDefault(String errorKey, Map<String, ErrorMessage> errorMap,
                                                  String defaultSeverity) {
        Objects.requireNonNull(errorKey, "null errorKey");
        Objects.requireNonNull(errorMap, "null errorMap");
        String errorKeyLowerCase = errorKey.toLowerCase();
        return errorMap.containsKey(errorKeyLowerCase) ? errorMap.get(errorKeyLowerCase)
                : this.getDefaultErrorMessage(errorKeyLowerCase, defaultSeverity, errorMap);
    }

    /**
     * Gets the default error message.
     *
     * @param errorKey        the error key
     * @param defaultSeverity the default severity
     * @param errorMap
     * @return the default error message
     */
    private ErrorMessage getDefaultErrorMessage(String errorKey, String defaultSeverity, Map<String, ErrorMessage> errorMap) {
        ErrorMessage out = transcodeErrorKey(errorKey, errorMap);
        if (out != null) {
            return out;
        } else {
            out = new ErrorMessage();
            out.setMessageKey(errorKey);
            out.setSeverity(defaultSeverity);
            out.setMessage(DEFAULT_MESSAGE);
            out.setMessageTitle(DEFAULT_MESSAGE_TITLE);
            out.setErrorCode(DEFAULT_ERROR_CODE);
            out.setRetry(false);
            return out;
        }
    }

	private ErrorMessage transcodeErrorKey(String errorKey, Map<String, ErrorMessage> errorMap) {
		if(errorKey.startsWith("invalid.body.error;") || errorKey.startsWith("invalid.param.error;")){
			return transcodeInvalidBody(errorKey, errorMap);
		} else if(errorKey.startsWith("missing.parameter.error;")){
			return transcodeMissingParameter(errorKey, errorMap);
		} else if(errorKey.startsWith("missing.request.part.error;")){
			return transcodeMissingRequestParameter(errorKey, errorMap);
		} else if(errorKey.startsWith("missing.path.variable.error;")){
			return transcodeMissingPathVariable(errorKey, errorMap);
		} else if(errorKey.startsWith("meda.parametric.errormessage;")){
			return transcodeParametricErrorMessage(errorKey, errorMap);
		} else {
			String[] parts = errorKey.split("\\.");
			if(parts.length == 5){
				return transcodeValidationApiError(errorKey, parts, errorMap);
			}
		}

		return null;
	}

	private ErrorMessage transcodeValidationApiError(String errorKey, String[] parts, Map<String, ErrorMessage> errorMap) {
		String entity = parts[2];
		String field = parts[3];
		String constraint = parts[4];

		ErrorMessage defaultEntity = errorMap.get(String.format("*.*.%s.%s.%s", entity, field, constraint));
		if(defaultEntity!=null){
			defaultEntity=clone(defaultEntity);
			defaultEntity.setMessageKey(errorKey);
			return defaultEntity;
		}

		ErrorMessage defaultConstraintMessage = errorMap.get(String.format("*.*.*.*.%s", constraint));
		if(defaultConstraintMessage != null){
			defaultConstraintMessage=clone(defaultConstraintMessage);
			defaultConstraintMessage.setMessageKey(errorKey);

			String entityName = transcodeName(errorMap, "entity", entity);
			String fieldName = transcodeName(errorMap, String.format("entity.%s", entity), field);

			defaultConstraintMessage.setMessageTitle(resolveEntityField(defaultConstraintMessage.getMessageTitle(), entityName, fieldName));
			defaultConstraintMessage.setMessage(resolveEntityField(defaultConstraintMessage.getMessage(), entityName, fieldName));
			return defaultConstraintMessage;
		}

		return null;
	}

	private String transcodeName(Map<String, ErrorMessage> errorMap, String objectType, String objectName) {
        ErrorMessage objectConfig = errorMap.get(String.format("%s.%s", objectType, objectName));
        if (objectConfig != null) {
            objectName = objectConfig.getMessage();
        }
        return objectName;
    }

    private ErrorMessage clone(ErrorMessage defaultEntity) {
        ErrorMessage out = new ErrorMessage();
        BeanUtils.copyProperties(defaultEntity, out);
        return out;
    }

    private String resolveEntityField(String message, String entityName, String fieldName) {
        return message.replaceAll("%ENTITY%", entityName).replaceAll("%FIELD%", fieldName);
    }

	private ErrorMessage transcodeInvalidBody(String errorKey, Map<String, ErrorMessage> errorMap) {
    	ErrorMessage invalidBodyMessage = errorMap.get("invalid.body.error");
    	if(invalidBodyMessage!=null){
    		invalidBodyMessage=clone(invalidBodyMessage);
    		invalidBodyMessage.setMessageKey(errorKey);

    		String[] parts = errorKey.split(";");
    		String field = parts[1];
			String value = parts[2];
			String type = parts[3];

			invalidBodyMessage.setMessageTitle(resolveInvalidFieldValue(invalidBodyMessage.getMessageTitle(), field, value, type));
			invalidBodyMessage.setMessage(resolveInvalidFieldValue(invalidBodyMessage.getMessage(), field, value, type));

    		return invalidBodyMessage;
		} else {
    		return null;
		}
	}

	private String resolveInvalidFieldValue(String message, String field, String value, String type) {
		return message.replaceAll("%FIELD%", field).replaceAll("%VALUE%", value).replaceAll("%TYPE%", type);
	}

	private ErrorMessage transcodeMissingParameter(String errorKey, Map<String, ErrorMessage> errorMap) {
		ErrorMessage missingParamError = errorMap.get("missing.parameter.error");
		if(missingParamError!=null){
			missingParamError=clone(missingParamError);
			missingParamError.setMessageKey(errorKey);

			String[] parts = errorKey.split(";");
			String field = parts[1];
			String type = parts[2];

			missingParamError.setMessageTitle(resolveMissingParameter(missingParamError.getMessageTitle(), field, type));
			missingParamError.setMessage(resolveMissingParameter(missingParamError.getMessage(), field, type));

			return missingParamError;
		} else {
			return null;
		}
	}

	private String resolveMissingParameter(String message, String field, String type) {
		return message.replaceAll("%FIELD%", field).replaceAll("%TYPE%", type);
	}

	private ErrorMessage transcodeMissingRequestParameter(String errorKey, Map<String, ErrorMessage> errorMap) {
		ErrorMessage missingRequestPartError = errorMap.get("missing.request.part.error");
		if(missingRequestPartError!=null){
			missingRequestPartError=clone(missingRequestPartError);
			missingRequestPartError.setMessageKey(errorKey);

			String[] parts = errorKey.split(";");
			String field = parts[1];

			missingRequestPartError.setMessageTitle(missingRequestPartError.getMessageTitle().replaceAll("%FIELD%", field));
			missingRequestPartError.setMessage(missingRequestPartError.getMessage().replaceAll("%FIELD%", field));

			return missingRequestPartError;
		} else {
			return null;
		}
	}

	private ErrorMessage transcodeMissingPathVariable(String errorKey, Map<String, ErrorMessage> errorMap) {
		ErrorMessage missingPathVariableError = errorMap.get("missing.path.variable.error");
		if(missingPathVariableError!=null){
			missingPathVariableError=clone(missingPathVariableError);
			missingPathVariableError.setMessageKey(errorKey);

			String[] parts = errorKey.split(";");
			String field = parts[1];

			missingPathVariableError.setMessageTitle(missingPathVariableError.getMessageTitle().replaceAll("%FIELD%", field));
			missingPathVariableError.setMessage(missingPathVariableError.getMessage().replaceAll("%FIELD%", field));

			return missingPathVariableError;
		} else {
			return null;
		}
	}

	private ErrorMessage transcodeParametricErrorMessage(String errorKey, Map<String, ErrorMessage> errorMap) {
    	String[] splitted = errorKey.split(";");
		ErrorMessage parametricErrorMessage = errorMap.get(splitted[1]);
		if(parametricErrorMessage!=null){
			parametricErrorMessage=clone(parametricErrorMessage);
			parametricErrorMessage.setMessageKey(errorKey);

			String[] params = Arrays.copyOfRange(splitted, 2, splitted.length);

			parametricErrorMessage.setMessageTitle(String.format(parametricErrorMessage.getMessageTitle(), (String[]) params));
			parametricErrorMessage.setMessage(String.format(parametricErrorMessage.getMessage(), (String[]) params));

			return parametricErrorMessage;
		} else {
			return null;
		}
	}
}

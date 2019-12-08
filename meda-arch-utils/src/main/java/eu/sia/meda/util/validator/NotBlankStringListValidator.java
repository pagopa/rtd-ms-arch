package eu.sia.meda.util.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * The Class NotBlankStringListValidator.
 */
public class NotBlankStringListValidator implements ConstraintValidator<NotBlankStringList, List<String>> {
   
   /**
    * Checks if is valid.
    *
    * @param strings the strings
    * @param constraintValidatorContext the constraint validator context
    * @return true, if is valid
    */
   public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
      return !CollectionUtils.isEmpty(strings) && strings.stream().allMatch(StringUtils::isNotBlank);
   }
}

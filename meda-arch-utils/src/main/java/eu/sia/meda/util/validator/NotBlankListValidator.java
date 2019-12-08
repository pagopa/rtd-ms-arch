package eu.sia.meda.util.validator;

import java.util.List;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

/**
 * The Class NotBlankListValidator.
 */
public class NotBlankListValidator implements ConstraintValidator<NotBlankList, List<?>> {
   
   /**
    * Checks if the list is valid (not empty and all elements not null).
    *
    * @param objects the objects
    * @param context the context
    * @return true, if is valid
    */
   public boolean isValid(List<?> objects, ConstraintValidatorContext context) {
      return !CollectionUtils.isEmpty(objects) && objects.stream().allMatch(Objects::nonNull);
   }
}

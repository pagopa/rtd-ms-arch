package eu.sia.meda.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface NotBlankList.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
   validatedBy = {NotBlankListValidator.class}
)
public @interface NotBlankList {
   
   /**
    * Message.
    *
    * @return the string
    */
   String message() default "List of objects cannot be of size 0 or contain null objects";

   /**
    * Groups.
    *
    * @return the class[]
    */
   Class<?>[] groups() default {};

   /**
    * Payload.
    *
    * @return the class<? extends payload>[]
    */
   Class<? extends Payload>[] payload() default {};
}

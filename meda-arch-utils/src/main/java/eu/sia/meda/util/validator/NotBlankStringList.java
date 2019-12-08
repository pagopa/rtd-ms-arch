package eu.sia.meda.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface NotBlankStringList.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
   validatedBy = {NotBlankStringListValidator.class}
)
public @interface NotBlankStringList {
   
   /**
    * Message.
    *
    * @return the string
    */
   String message() default "List of strings cannot be empty or contain empty values";

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

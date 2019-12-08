package eu.sia.meda.switcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface CopyExecution.
 *
 * @deprecated 
 */
@Deprecated
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyExecution {
   
   /**
    * Value.
    *
    * @return the string
    */
   String value();
}

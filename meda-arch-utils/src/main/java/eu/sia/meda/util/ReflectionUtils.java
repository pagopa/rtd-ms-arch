package eu.sia.meda.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * The Class ReflectionUtils.
 */
public class ReflectionUtils {
   
   /**
    * Sets the field value.
    *
    * @param object the object
    * @param field the field
    * @param value the value
    * @return true, if successful
    * @throws IllegalAccessException the illegal access exception
    */
   public boolean setFieldValue(Object object, Field field, Object value) throws IllegalAccessException {
      try {
         if (field != null && object != null && value != null) {
            field.set(object, value);
            return true;
         } else {
            return false;
         }
      } catch (Exception var5) {
         return false;
      }
   }

   /**
    * Gets the field.
    *
    * @param name the name
    * @param clazz the clazz
    * @return the field
    */
   public Field getField(String name, Class clazz) {
      if (name != null && clazz != null) {
         try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return f;
         } catch (Exception var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   /**
    * New instance.
    *
    * @param clazz the clazz
    * @return the object
    */
   public Object newInstance(Class clazz) {
      if (clazz != null) {
         try {
            return clazz.newInstance();
         } catch (Exception var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   /**
    * Checks if is instance of.
    *
    * @param aClassOrigin the a class origin
    * @param aClass the a class
    * @return true, if is instance of
    */
   public boolean isInstanceOf(Class<?> aClassOrigin, Class<?> aClass) {
      boolean isInstanceOf;
      for(isInstanceOf = false; !aClassOrigin.equals(Object.class); aClassOrigin = aClassOrigin.getSuperclass()) {
         if (aClassOrigin.equals(aClass)) {
            isInstanceOf = true;
            break;
         }
      }

      return isInstanceOf;
   }

   /**
    * Gets the generic type class.
    *
    * @param myclass the myclass
    * @param genericIndex the generic index
    * @return the generic type class
    */
   public static final Object getGenericTypeClass(Class myclass, int genericIndex) {
      try {
         Type actualType = ((ParameterizedType)myclass.getGenericSuperclass()).getActualTypeArguments()[genericIndex];
         if (actualType instanceof Class) {
            return actualType;
         } else if (actualType instanceof TypeVariable) {
            return Object.class;
         } else if (actualType instanceof ParameterizedType) {
            return actualType;
         } else {
            throw new IllegalArgumentException();
         }
      } catch (Exception var3) {
         throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
      }
   }
}

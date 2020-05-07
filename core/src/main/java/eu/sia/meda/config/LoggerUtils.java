package eu.sia.meda.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class LoggerUtils.
 */
public class LoggerUtils {
   
   /** The Constant MEDA_ARCH. */
   private static final String MEDA_ARCH = "MEDA-ARCH";

   /**
    * Avoid instantiation of a new logger utils.
    */
   private LoggerUtils() {
   }

   /**
    * Format arch row.
    *
    * @param text the text
    * @return the string
    */
   public static String formatArchRow(String text) {
      return formatRow(MEDA_ARCH, text);
   }

   /**
    * Format row.
    *
    * @param label the label
    * @param text the text
    * @return the string
    */
   public static String formatRow(String label, String text) {
      return String.format("[%s] %s", label, text);
   }

   /**
    * Gets the logger.
    *
    * @param name the name
    * @return the logger
    */
   public static Logger getLogger(String name) {
      return LoggerFactory.getLogger(name);
   }

   /**
    * Gets the logger.
    *
    * @param clazz the clazz
    * @return the logger
    */
   public static Logger getLogger(Class clazz) {
      return LoggerFactory.getLogger(clazz);
   }
}

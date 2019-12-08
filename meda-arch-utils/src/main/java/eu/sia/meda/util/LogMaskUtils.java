package eu.sia.meda.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * The Class LogMaskUtils.
 */
public class LogMaskUtils {
   
   /**
    * Prevent instantiation of  a new log mask utils.
    */
   private LogMaskUtils() {
   }

   /**
    * Mask fields.
    *
    * @param args the args
    * @return the string
    */
   public static String maskFields(Object[] args) {
      Object[] objects = Arrays.stream(args).map((o) -> {
         return o != null ? o.toString() : o;
      }).filter(Objects::nonNull).toArray();
      String s = Arrays.toString(objects);
      return maskFields(s);
   }

   /**
    * Mask fields.
    *
    * @param s the s
    * @return the string
    */
   public static String maskFields(String s) {
      return s == null ? "" : s.replaceAll("(?<=\"password\":)\"[^\"]+\"", "\"********\"").replaceAll("\"([^\"]+)\":\"([^\"]{44})[^\"]{3}[^\"]+([^\"]{3})\"", "\"$1\":\"$2...$3\"").replaceAll("(?<=authorization=)[^]]+]", "[*****]").replaceAll("(?<=[aA]uthorization-[tT]oken=)[^]]+]", "[*****]").replaceAll("(?<=user-session=)[^]]+]", "[*****]");
   }
}

package eu.sia.meda.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.StreamUtils;

/**
 * The Class StringUtils.
 */
public class StringUtils {
   
   /**
    * Prevent instantiation of  a new string utils.
    */
   private StringUtils() {
   }

   /**
    * Lower case first letter.
    *
    * @param wordToCapitalize the word to capitalize
    * @return the string
    */
   public static String lowerCaseFirstLetter(String wordToCapitalize) {
      return wordToCapitalize != null ? wordToCapitalize.replaceFirst(wordToCapitalize.substring(0, 1), wordToCapitalize.substring(0, 1).toLowerCase()) : wordToCapitalize;
   }

   /**
    * To hex list string.
    *
    * @param listToConvert the list to convert
    * @return the string
    */
   public static String toHexListString(List<Long> listToConvert) {
      StringBuilder builder = new StringBuilder();
      Iterator it = listToConvert.iterator();

      while(it.hasNext()) {
         builder.append(Long.toHexString((Long)it.next()));
         if (it.hasNext()) {
            builder.append("->");
         }
      }

      return builder.toString() != null ? builder.toString() : null;
   }

   /**
    * Load resource as string.
    *
    * @param mockResource the mock resource
    * @return the string
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public static String loadResourceAsString(String mockResource) throws IOException {
      DefaultResourceLoader loader = new DefaultResourceLoader();
      InputStream data = loader.getResource(mockResource).getInputStream();
      return StreamUtils.copyToString(data, Charsets.UTF_8);
   }

   /**
    * Replace in order.
    *
    * @param <T> the generic type
    * @param original the original
    * @param pattern the pattern
    * @param values the values
    * @return the string
    */
   public static <T> String replaceInOrder(String original, Pattern pattern, T[] values) {
      if (original != null && pattern != null && values != null) {
         Matcher matcher = pattern.matcher(original);
         StringBuffer sb = new StringBuffer();
         int cnt = 0;

         while(matcher.find() && cnt < values.length) {
            matcher.appendReplacement(sb, values[cnt++].toString());
         }

         matcher.appendTail(sb);
         return sb.toString();
      } else {
         return original;
      }
   }

   /**
    * Calculate md5 algorithm of an input string
    *
    */

   public static String md5(String input) {
      try {
         return MessageDigest.getInstance("MD5").digest(input.getBytes()).toString();
      } catch (NoSuchAlgorithmException e) {
         throw new IllegalArgumentException("Method MD5 not found");
      }
   }
}

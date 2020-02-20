package eu.sia.meda.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Interface NumberUtils.
 */
public interface NumberUtils {
   
   /** The logger. */
   Logger logger = LoggerFactory.getLogger(NumberUtils.class);

   /**
    * Format to it 2 decimal.
    *
    * @param number the number
    * @return the string
    */
   static String formatToIt2Decimal(String number) {
      String numberFormatted = null;

      try {
         BigDecimal bigDecimal = new BigDecimal(number);
         numberFormatted = formatToIt2Decimal(bigDecimal);
      } catch (NumberFormatException var3) {
         logger.error("Unable to parse number: {}", number);
      }

      return numberFormatted;
   }

   /**
    * Format to it 2 decimal.
    *
    * @param number the number
    * @return the string
    */
   static String formatToIt2Decimal(BigDecimal number) {
      String numberFormatted = null;
      if (number != null) {
         NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ITALIAN);
         numberFormat.setMinimumFractionDigits(2);
         numberFormat.setMaximumFractionDigits(2);
         DecimalFormat decimalFormat = (DecimalFormat)numberFormat;

         try {
            numberFormatted = decimalFormat.format(number);
         } catch (IllegalArgumentException var5) {
            logger.error("Unable to format number: {}", number);
         }
      }

      return numberFormatted;
   }

   /**
    * Format to integer.
    *
    * @param number the number
    * @return the string
    */
   static String formatToInteger(String number) {
      if (number != null) {
         String[] split;
         if (number.contains(".")) {
            split = number.split("\\.");
            return split.length > 0 ? split[0] : null;
         } else if (number.contains(",")) {
            split = number.split(",");
            return split.length > 0 ? split[0] : null;
         } else {
            return number;
         }
      } else {
         return null;
      }
   }

   /** An equals implementation using {@link BigDecimal#compareTo(BigDecimal)} method */
   static boolean equals(BigDecimal v1, BigDecimal v2){
      return Objects.equals(v1, v2) || (v1!=null && v2!=null && v1.compareTo(v2) == 0);
   }
}

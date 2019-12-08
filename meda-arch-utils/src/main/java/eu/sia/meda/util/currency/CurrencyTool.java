package eu.sia.meda.util.currency;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * The Class CurrencyTool.
 */
public class CurrencyTool {
   
   /** The decimal format. */
   private DecimalFormat decimalFormat;

   /**
    * Prevent instantiation of  a new currency tool.
    *
    * @param localeCode the locale code
    */
   public CurrencyTool(String localeCode) {
      Locale locale = Locale.forLanguageTag(localeCode);
      NumberFormat numberFormat = NumberFormat.getInstance(locale);
      this.decimalFormat = (DecimalFormat)numberFormat;
   }

   /**
    * Return a BigDeciaml from a string.
    *
    * @param amount the amount
    * @return the big decimal
    * @throws NumberFormatException the parse exception
    */
   public BigDecimal fromString(String amount) throws NumberFormatException {
      return new BigDecimal(amount);
   }
}

package eu.sia.meda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The Class DateUtils.
 */
public class DateUtils {
   
   /** The Constant FORMAT_SHORT. */
   private static final String FORMAT_SHORT = "yyyy-MM-dd";
   
   /** The Constant FORMATTER_SHORT. */
   private static final DateTimeFormatter FORMATTER_SHORT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   
   /** The Constant FORMAT. */
   private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
   
   /** The Constant FORMATTER. */
   private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
   
   /** The Constant FORMAT_FULL. */
   private static final String FORMAT_FULL = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
   
   /** The Constant FORMATTER_FULL. */
   private static final DateTimeFormatter FORMATTER_FULL = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

   /**
    * Prevent instantiation of  a new date utils.
    */
   private DateUtils() {
   }

   /**
    * Parses the string to local date.
    *
    * @param dateAsString the date as string
    * @return the local date
    */
   public static LocalDate parseStringToLocalDate(String dateAsString) {
      return LocalDate.parse(dateAsString, FORMATTER_SHORT);
   }

   /**
    * Format date to string.
    *
    * @param date the date
    * @return the string
    */
   public static String formatDateToString(LocalDate date) {
      return FORMATTER_SHORT.format(date);
   }

   /**
    * Parses the string to local date time.
    *
    * @param dateTimeAsString the date time as string
    * @return the local date time
    */
   public static LocalDateTime parseStringToLocalDateTime(String dateTimeAsString) {
      return LocalDateTime.parse(dateTimeAsString, FORMATTER_FULL);
   }

   /**
    * Format date to string.
    *
    * @param date the date
    * @return the string
    */
   public static String formatDateToString(LocalDateTime date) {
      return FORMATTER.format(date);
   }

   /**
    * Format date to string.
    *
    * @param date the date
    * @return the string
    */
   public static String formatDateToString(OffsetDateTime date) {
      return FORMATTER_FULL.format(date);
   }

   /**
    * Parses the string to date.
    *
    * @param dateAsString the date as string
    * @return the date
    */
   public static Date parseStringToDate(String dateAsString) {
      try {
         return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")).parse(dateAsString);
      } catch (ParseException var2) {
         throw new IllegalArgumentException("Can not parse: " + dateAsString + " with format " + "yyyy-MM-dd'T'HH:mm:ss.SSSX");
      }
   }

   /**
    * Format date to string.
    *
    * @param date the date
    * @return the string
    */
   public static String formatDateToString(Date date) {
      return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")).format(date);
   }

   /**
    * Format date to day.
    *
    * @param date the date
    * @return the string
    */
   public static String formatDateToDay(Date date) {
      return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
   }
}

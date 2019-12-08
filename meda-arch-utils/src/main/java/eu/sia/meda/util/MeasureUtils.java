package eu.sia.meda.util;

import java.text.DecimalFormat;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class MeasureUtils.
 */
public class MeasureUtils {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(MeasureUtils.class);
   
   /** The decimal format. */
   private static DecimalFormat decimalFormat = new DecimalFormat("#.00");

   /**
    * Prevent instantiation of  a new measure utils.
    */
   private MeasureUtils() {
   }

   /**
    * Format to MB.
    *
    * @param value the value
    * @return the string
    */
   private static String formatToMB(long value) {
      return decimalFormat.format((double)value / 1048576.0D);
   }

   /**
    * Measure.
    *
    * @param <T> the generic type
    * @param callable the callable
    * @return the t
    */
   public static <T> T measure(Callable<T> callable) {
      long maxMemory = Runtime.getRuntime().maxMemory();
      long value = Runtime.getRuntime().totalMemory();
      long diff = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      String o1 = formatToMB(maxMemory);
      logger.info("Max Memory designata per jvm Xmx : {} bytes = {} M", maxMemory, o1);
      String o2 = formatToMB(value);
      logger.info("Memoria totale assegnata al processo java: {}, bytes =  {} M", value, o2);
      String o3 = formatToMB(diff);
      logger.info("Memory realmente utilizzata dal processo java: {}, bytes =  {} M", diff, o3);
      org.springframework.util.StopWatch stopWatch = new org.springframework.util.StopWatch();
      stopWatch.start();

      T result;
      try {
         result = callable.call();
      } catch (Exception var26) {
         throw new UnsupportedOperationException(var26);
      }

      stopWatch.stop();
      long totalTimeMillis = stopWatch.getTotalTimeMillis();
      long maxMemory2 = Runtime.getRuntime().maxMemory();
      long value2 = Runtime.getRuntime().totalMemory();
      long diff2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      long memoryUsed = diff2 - diff;
      String a1 = formatToMB(maxMemory2);
      String a2 = formatToMB(value2);
      String a3 = formatToMB(diff2);
      String a4 = formatToMB(memoryUsed);
      logger.info("Executed in: {} ms", totalTimeMillis);
      logger.info("Max Memory designata per jvm Xmx dopo l'esecuzione del ciclo: {} bytes = {} M", maxMemory2, a1);
      logger.info("Memoria totale assegnata al processo java dopo l'esecuzione  del ciclo: {} bytes = {} M", value2, a2);
      logger.info("Memory realmente utilizzata dal processo java dopo l'esecuzione del ciclo: {} bytes = {} M", diff2, a3);
      logger.info("Incremento memoria in seguito all'esecuzione del ciclo: {} bytes = {} M", memoryUsed, a4);
      return result;
   }
}

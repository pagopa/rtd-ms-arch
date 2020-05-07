package eu.sia.meda.async;

import java.util.Map;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/**
 * The Class MdcTaskDecorator.
 */
public class MdcTaskDecorator implements TaskDecorator {
   
   /**
    * Decorate.
    *
    * @param runnable the runnable
    * @return the runnable
    */
   public Runnable decorate(Runnable runnable) {
      Map<String, String> contextMap = MDC.getCopyOfContextMap();
      return () -> {
         try {
            MDC.setContextMap(contextMap);
            runnable.run();
         } finally {
            MDC.clear();
         }

      };
   }
}

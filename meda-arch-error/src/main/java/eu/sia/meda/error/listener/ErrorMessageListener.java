/*
 * 
 */
package eu.sia.meda.error.listener;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.error.cache.MedaRemoteErrorCache;
import eu.sia.meda.error.condition.ErrorManagerKafkaServersCondition;
import eu.sia.meda.eventlistener.BaseEventListener;
import java.nio.charset.Charset;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * The listener interface for receiving errorMessage events.
 * The class that is interested in processing a errorMessage
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addErrorMessageListener<code> method. When
 * the errorMessage event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ErrorMessageEvent
 */
@Component
@Conditional({ErrorManagerKafkaServersCondition.class})
public class ErrorMessageListener extends BaseEventListener {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerUtils.getLogger(ErrorMessageListener.class);
   
   /** The Constant UTF8_CHARSET. */
   private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
   
   /** The meda remote error cache. */
   @Autowired
   private MedaRemoteErrorCache medaRemoteErrorCache;

   /**
    * On received.
    *
    * @param payload the payload
    * @param headers the headers
    */
   public void onReceived(byte[] payload, Headers headers) {
      if (payload != null) {
         try {
            String errorKey = new String(payload, UTF8_CHARSET);
            logger.info("removing error key from cache: {}", errorKey);
            this.medaRemoteErrorCache.remove(errorKey);
         } catch (Exception var4) {
            logger.error("error removing error key from cache: {}", var4);
         }

      }
   }
}

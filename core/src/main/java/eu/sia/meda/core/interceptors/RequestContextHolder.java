package eu.sia.meda.core.interceptors;

import eu.sia.meda.config.LoggerUtils;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

/**
 * The Class RequestContextHolder.
 */
public class RequestContextHolder extends BaseContextHolder {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(RequestContextHolder.class);
   
   /** The Constant request. */
   private static final InheritableThreadLocal<HttpServletRequest> request = new InheritableThreadLocal<>();

   /**
    * Instantiates a new request context holder.
    */
   protected RequestContextHolder() {
   }

   /**
    * Gets the request.
    *
    * @return the request
    */
   public static HttpServletRequest getRequest() {
      return request.get();
   }

   /**
    * Sets the request.
    *
    * @param req the new request
    */
   public static void setRequest(HttpServletRequest req) {
      Objects.requireNonNull(req, "HttpServletRequest cannot be null");
      if (request.get() != null) {
         throw new IllegalStateException("HttpRequest already initialized");
      } else {
    	 if(log.isDebugEnabled()) {
    		 log.debug(LoggerUtils.formatArchRow("Thread {}-{}, init HttpRequest variable"), Thread.currentThread().getName(), Thread.currentThread().getId());    		  
    	 }
         request.set(req);
      }
   }

   /**
    * Force set request.
    *
    * @param req the req
    */
   public static void forceSetRequest(HttpServletRequest req) {
	   if(log.isDebugEnabled()) {
		      log.debug(LoggerUtils.formatArchRow("Forcing request setting on thread Thread {}-{}"), Thread.currentThread().getName(), Thread.currentThread().getId());		   
	   }
      request.set(req);
   }

   /**
    * Clear.
    */
   public static void clear() {
      BaseContextHolder.clear();

      try {
         request.remove();
      } catch (Exception var1) {
    	  if(log.isWarnEnabled()) {
    		  log.warn(LoggerUtils.formatArchRow("Swallowed error while clearing HttpServletRequest"), var1);
    	  }
      }

   }
}

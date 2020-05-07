package eu.sia.meda.core.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.utils.MedaRequestAttributes;

/**
 * The Class PopulateMDCInterceptor.
 */
@Component
public class PopulateMDCInterceptor implements HandlerInterceptor {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(PopulateMDCInterceptor.class);

   /**
    * Pre handle.
    *
    * @param httpServletRequest the http servlet request
    * @param httpServletResponse the http servlet response
    * @param controller the controller
    * @return true, if successful
    */
   public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object controller) {
      log.debug(LoggerUtils.formatArchRow("Populating MDC"));
      MDC.put("request-id", MedaRequestAttributes.getRequestId(httpServletRequest));
      String sessionId = httpServletRequest.getSession(false) != null ? httpServletRequest.getSession(false).getId() : "NONE";
      MDC.put("session-id", sessionId);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null) {
         MDC.put("user-id", "");
      } else {
         MDC.put("user-id", authentication.getName());
      }

      return true;
   }

   /**
    * Post handle.
    *
    * @param httpServletRequest the http servlet request
    * @param httpServletResponse the http servlet response
    * @param o the o
    * @param modelAndView the model and view
    */
   @Override
   public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
      MDC.clear();
      log.debug(LoggerUtils.formatArchRow("Cleared MDC"));
   }
}

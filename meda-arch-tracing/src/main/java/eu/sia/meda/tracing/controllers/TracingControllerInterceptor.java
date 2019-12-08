package eu.sia.meda.tracing.controllers;

import eu.sia.meda.core.controller.BaseController;
import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.controllers.utils.TraceableRequest;
import eu.sia.meda.tracing.controllers.utils.TraceableResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * The Class TracingControllerInterceptor.
 */
@Conditional({ControllerTracingCondition.class})
@Component
@Order(Integer.MAX_VALUE)
public class TracingControllerInterceptor implements HandlerInterceptor {
   
   /** The tracing manager. */
   private final TracingManager tracingManager;

   /**
    * Instantiates a new tracing controller interceptor.
    *
    * @param tracingManager the tracing manager
    */
   @Autowired
   public TracingControllerInterceptor(TracingManager tracingManager) {
      this.tracingManager = tracingManager;
   }

   /**
    * Pre handle.
    *
    * @param request the request
    * @param response the response
    * @param handler the handler
    * @return true, if successful
    * @throws Exception the exception
    */
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      if (this.shouldTrace(handler) && request instanceof TraceableRequest && response instanceof TraceableResponse) {
         TraceableRequest traceableRequest = (TraceableRequest)request;
         TraceableResponse traceableResponse = (TraceableResponse)response;
         TracingControllerUtils.traceRequest(traceableRequest, this.tracingManager);
         traceableResponse.setShouldBeTraced(true);
      }

      return true;
   }

   /**
    * Should trace.
    *
    * @param handler the handler
    * @return true, if successful
    */
   private boolean shouldTrace(Object handler) {
      boolean shouldTrace = false;
      if (handler instanceof HandlerMethod) {
         Object controller = ((HandlerMethod)handler).getBean();
         shouldTrace = controller instanceof BaseController;
      }

      return shouldTrace;
   }
}

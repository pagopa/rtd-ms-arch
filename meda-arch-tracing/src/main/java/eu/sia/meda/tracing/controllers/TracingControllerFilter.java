package eu.sia.meda.tracing.controllers;

import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.controllers.utils.TraceableRequest;
import eu.sia.meda.tracing.controllers.utils.TraceableResponse;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The Class TracingControllerFilter.
 */
@Conditional({ControllerTracingCondition.class})
@Component
@Order(Integer.MAX_VALUE)
public class TracingControllerFilter extends OncePerRequestFilter {
   
   /** The max length. */
   private final int maxLength;
   
   /** The tracing manager. */
   private final TracingManager tracingManager;

   /**
    * Instantiates a new tracing controller filter.
    *
    * @param propertiesManager the properties manager
    * @param tracingManager the tracing manager
    */
   @Autowired
   public TracingControllerFilter(PropertiesManager propertiesManager, TracingManager tracingManager) {
      this.maxLength = propertiesManager.get("controllers.tracing.bodyMaxLength", 10000);
      this.tracingManager = tracingManager;
   }

   /**
    * Do filter internal.
    *
    * @param httpServletRequest the http servlet request
    * @param httpServletResponse the http servlet response
    * @param filterChain the filter chain
    * @throws ServletException the servlet exception
    * @throws IOException Signals that an I/O exception has occurred.
    */
   protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
      TraceableRequest traceableRequest = new TraceableRequest(httpServletRequest, this.maxLength);
      TraceableResponse traceableResponse = new TraceableResponse(httpServletResponse, this.maxLength);
      filterChain.doFilter(traceableRequest, traceableResponse);
      if (traceableResponse.shouldBeTraced()) {
         TracingControllerUtils.traceResponse(traceableRequest, traceableResponse, this.tracingManager);
      }

      traceableResponse.copyBodyToResponse();
   }
}

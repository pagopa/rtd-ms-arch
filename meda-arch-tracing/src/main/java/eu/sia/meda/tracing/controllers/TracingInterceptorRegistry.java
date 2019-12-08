package eu.sia.meda.tracing.controllers;

import eu.sia.meda.core.interceptors.DefaultControllerInterceptor;
import eu.sia.meda.core.interceptors.PopulateMDCInterceptor;
import eu.sia.meda.core.interceptors.utils.MedaInterceptorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * The Class TracingInterceptorRegistry.
 */
@Primary
@Component
@Conditional({ControllerTracingCondition.class})
public class TracingInterceptorRegistry extends MedaInterceptorRegistry {
   
   /**
    * Instantiates a new tracing interceptor registry.
    *
    * @param populateMDCInterceptor the populate MDC interceptor
    * @param defaultControllerInterceptor the default controller interceptor
    * @param tracingControllerInterceptor the tracing controller interceptor
    */
   @Autowired
   public TracingInterceptorRegistry(PopulateMDCInterceptor populateMDCInterceptor, DefaultControllerInterceptor defaultControllerInterceptor, TracingControllerInterceptor tracingControllerInterceptor) {
      super(populateMDCInterceptor, defaultControllerInterceptor);
      this.getInterceptors().add(tracingControllerInterceptor);
   }
}

package eu.sia.meda.core.interceptors.utils;

import eu.sia.meda.core.interceptors.DefaultControllerInterceptor;
import eu.sia.meda.core.interceptors.PopulateMDCInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * The Class MedaInterceptorRegistry.
 */
@Component
public class MedaInterceptorRegistry {
   
   /** The interceptors. */
   private final List<HandlerInterceptor> interceptors = new ArrayList<>();

   /**
    * Instantiates a new meda interceptor registry.
    *
    * @param populateMDCInterceptor the populate MDC interceptor
    * @param defaultControllerInterceptor the default controller interceptor
    */
   @Autowired
   public MedaInterceptorRegistry(PopulateMDCInterceptor populateMDCInterceptor, DefaultControllerInterceptor defaultControllerInterceptor) {
      this.getInterceptors().add(populateMDCInterceptor);
      this.getInterceptors().add(defaultControllerInterceptor);
   }

   /**
    * Gets the interceptors.
    *
    * @return the interceptors
    */
   public List<HandlerInterceptor> getInterceptors() {
      return this.interceptors;
   }
}

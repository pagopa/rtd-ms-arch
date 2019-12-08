package eu.sia.meda.config;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import eu.sia.meda.core.interceptors.utils.MedaInterceptorRegistry;

/**
 * The Class BaseInterceptorConfiguration.
 */
@Configuration
public class BaseInterceptorConfiguration implements WebMvcConfigurer {
   
   /** The meda interceptor registry. */
   private final MedaInterceptorRegistry medaInterceptorRegistry;

   /**
    * Instantiates a new base interceptor configuration.
    *
    * @param medaInterceptorRegistry the meda interceptor registry
    */
   @Autowired
   public BaseInterceptorConfiguration(MedaInterceptorRegistry medaInterceptorRegistry) {
      this.medaInterceptorRegistry = medaInterceptorRegistry;
   }

   /**
    * Adds the interceptors.
    *
    * @param registry the registry
    */
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      Iterator<HandlerInterceptor> var2 = this.medaInterceptorRegistry.getInterceptors().iterator();

      while(var2.hasNext()) {
         HandlerInterceptor interceptor = var2.next();
         registry.addInterceptor(interceptor).addPathPatterns("/**");
      }

   }
}

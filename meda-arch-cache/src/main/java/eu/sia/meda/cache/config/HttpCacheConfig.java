package eu.sia.meda.cache.config;

import eu.sia.meda.cache.http.ForcingEtagHeaderFilter;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class HttpCacheConfig.
 */
@Configuration
public class HttpCacheConfig {
   
   /**
    * Shallow E tag header filter.
    *
    * @return the filter
    */
   public Filter shallowETagHeaderFilter() {
      return new ForcingEtagHeaderFilter();
   }

   /**
    * Formatted CRLF registration.
    *
    * @return the filter registration bean
    */
   @Bean
   public FilterRegistrationBean formattedCRLFRegistration() {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      registration.setFilter(this.shallowETagHeaderFilter());
      registration.addUrlPatterns("*");
      registration.setName("ForcingEtagHeaderFilter");
      registration.setOrder(1);
      return registration;
   }
}

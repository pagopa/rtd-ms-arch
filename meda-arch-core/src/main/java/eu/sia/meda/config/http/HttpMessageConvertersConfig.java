package eu.sia.meda.config.http;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Class HttpMessageConvertersConfig.
 */
@Primary
@Configuration
public class HttpMessageConvertersConfig implements WebMvcConfigurer {
   
   /** The default converter. */
   private final MappingJackson2HttpMessageConverter defaultConverter;
   
   /** The custom converters configurer. */
   private CustomConvertersConfigurer customConvertersConfigurer;

   /**
    * Instantiates a new http message converters config.
    *
    * @param defaultConverter the default converter
    */
   @Autowired
   public HttpMessageConvertersConfig(MappingJackson2HttpMessageConverter defaultConverter) {
      this.defaultConverter = defaultConverter;
   }

   /**
    * Sets the custom converters configurer.
    *
    * @param customConvertersConfigurer the new custom converters configurer
    */
   @Autowired(
      required = false
   )
   public void setCustomConvertersConfigurer(CustomConvertersConfigurer customConvertersConfigurer) {
      this.customConvertersConfigurer = customConvertersConfigurer;
   }

   /**
    * Configure message converters.
    *
    * @param converters the converters
    */
   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.add(this.defaultConverter);
      if (this.customConvertersConfigurer != null) {
         List<HttpMessageConverter<?>> customConverters = this.customConvertersConfigurer.customConverters();
         if (customConverters != null) {
            converters.addAll(customConverters);
         }
      }

   }
}

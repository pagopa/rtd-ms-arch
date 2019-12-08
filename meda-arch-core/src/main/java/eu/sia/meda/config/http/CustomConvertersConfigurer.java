package eu.sia.meda.config.http;

import java.util.List;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * The Interface CustomConvertersConfigurer.
 */
public interface CustomConvertersConfigurer {
   
   /**
    * Custom converters.
    *
    * @return the list
    */
   List<HttpMessageConverter<?>> customConverters();
}

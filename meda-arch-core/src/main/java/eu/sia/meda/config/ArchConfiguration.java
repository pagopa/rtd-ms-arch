package eu.sia.meda.config;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.BaseContext;

/**
 * The Class ArchConfiguration.
 */
@EnableConfigurationProperties
@ComponentScan({"eu.sia.meda.core"})
@Configuration
@PropertySource(
   value = {"classpath:core-config.properties"},
   name = "localCoreConfig"
)
public class ArchConfiguration {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(ArchConfiguration.class);

   /**
    * Object mapper.
    *
    * @return the object mapper
    */
   @Bean
   public ObjectMapper objectMapper() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.registerModule(new Jdk8Module());
      mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      mapper.setSerializationInclusion(Include.NON_NULL);
      SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
      simpleFilterProvider.setFailOnUnknownId(false);
      mapper.setFilterProvider(simpleFilterProvider);
      return mapper;
   }

   /**
    * Mapping jackson 2 http message converter.
    *
    * @param objectMapper the object mapper
    * @param builder the builder
    * @return the mapping jackson 2 http message converter
    */
   @Bean
   public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper, Jackson2ObjectMapperBuilder builder) {
      builder.configure(objectMapper);
      MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
      jsonConverter.setObjectMapper(objectMapper);
      return jsonConverter;
   }

   /**
    * Application context.
    *
    * @return the application context
    */
   @Bean
   @Scope("prototype")
   public ApplicationContext applicationContext() {
	  if(log.isDebugEnabled()) {
		  log.debug(LoggerUtils.formatArchRow("Thread {}-{}, return ApplicationContext bean"), Thread.currentThread().getName(), Thread.currentThread().getId());
	  }
      return BaseContextHolder.getApplicationContext();
   }

   /**
    * Session context.
    *
    * @return the base context
    */
   @Bean
   @Scope("prototype")
   public BaseContext sessionContext() {
      BaseContext sc = BaseContextHolder.getSessionContext();
      if (sc == null) {
         sc = BaseContext.EMPTY_SESSION;
      }
      if(log.isDebugEnabled()) {
          log.debug(LoggerUtils.formatArchRow("Thread {}-{}, return SessionContext {}"), Thread.currentThread().getName(), Thread.currentThread().getId(), sc);
      }
      return sc;
   }
}

package eu.sia.meda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;

import eu.sia.meda.exceptions.response.MicroServiceExceptionResponse;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwagggerConfig.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
   
   /** The title. */
   @Value("${swagger.title:${spring.application.name}}")
   private String title;
   
   /** The description. */
   @Value("${swagger.description:Api and Models}")
   private String description;
   
   /** The version. */
   @Value("${swagger.version:${spring.application.version}}")
   private String version;
   
   /**
    * Swagger spring mvc plugin old.
    *
    * @param typeResolver the type resolver
    * @return the docket
    */
   @Bean
	public Docket swaggerSpringMvcPluginOld(@Autowired TypeResolver typeResolver) {
		return (new Docket(DocumentationType.SWAGGER_2)).select().apis(RequestHandlerSelectors.any())
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))).build()
				.additionalModels(typeResolver.resolve(MicroServiceExceptionResponse.class)).apiInfo(this.metadata());
	}

   /**
    * Metadata.
    *
    * @return the api info
    */
   private ApiInfo metadata() {
      return (new ApiInfoBuilder()).title(this.title).description(this.description).version(this.version).build();
   }
}

package eu.sia.meda.config;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;

import springfox.documentation.builders.AlternateTypeBuilder;
import springfox.documentation.builders.AlternateTypePropertyBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
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
	 * Swagger spring.
	 *
	 * @param typeResolver the type resolver
	 * @return the docket
	 */
	@Bean
	public Docket swaggerSpringPlugin(@Autowired TypeResolver typeResolver) {
		return (new Docket(DocumentationType.SWAGGER_2)).select().apis(RequestHandlerSelectors.any())
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.hateoas"))).build()
//				.ignoredParameterTypes(BaseResource.class)
				.alternateTypeRules(
						newRule(typeResolver.resolve(Pageable.class), pageableMixin(), Ordered.HIGHEST_PRECEDENCE))
				.directModelSubstitute(LocalTime.class, String.class)
				/*.additionalModels(typeResolver.resolve(MicroServiceExceptionResponse.class))*/.apiInfo(this.metadata());
	}

	/**
	 * Metadata.
	 *
	 * @return the api info
	 */
	private ApiInfo metadata() {
		return (new ApiInfoBuilder()).title(this.title).description(this.description).version(this.version).build();
	}

	private Type pageableMixin() {
		return new AlternateTypeBuilder()
				.fullyQualifiedClassName(String.format("%s.generated.%s", Pageable.class.getPackage().getName(),
						Pageable.class.getSimpleName()))
				.withProperties(Arrays.asList(property(Integer.class, "page"), property(Integer.class, "size"),
						property(String.class, "sort")))
				.build();
	}

	private AlternateTypePropertyBuilder property(Class<?> type, String name) {
		return new AlternateTypePropertyBuilder().withName(name).withType(type).withCanRead(true).withCanWrite(true);
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().scopeSeparator(",")
				.additionalQueryStringParams(null)
				.useBasicAuthenticationWithAccessCodeGrant(false).build();
	}

	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(
				"global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(new SecurityReference("apiKey",
				authorizationScopes));
	}

}

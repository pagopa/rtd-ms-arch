package eu.sia.meda.config;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.util.ConcurrentTypeCache;
import com.fasterxml.classmate.util.ResolvedTypeCache;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

	private SwaggerConfig swaggerConfigUnderTest;

	@BeforeEach
	void setUp() {
		swaggerConfigUnderTest = new SwaggerConfig();
	}

	@Test
	void testSwaggerSpringMvcPluginOld() {
		// Setup
		final ResolvedTypeCache cache = new ConcurrentTypeCache(4);
		final TypeResolver typeResolver = new TypeResolver(cache);

		// Run the test
		final Docket result = swaggerConfigUnderTest.swaggerSpringPlugin(typeResolver);

		// Verify the results
		assertNotNull(result);
	}

	@Test
	public void testSecurity(){
		SecurityConfiguration security = swaggerConfigUnderTest.security();
		Assert.assertNotNull(security);
	}

}

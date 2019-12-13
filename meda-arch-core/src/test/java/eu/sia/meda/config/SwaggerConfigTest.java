package eu.sia.meda.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.util.ConcurrentTypeCache;
import com.fasterxml.classmate.util.ResolvedTypeCache;

import springfox.documentation.spring.web.plugins.Docket;

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
		final Docket result = swaggerConfigUnderTest.swaggerSpringMvcPluginOld(typeResolver);

		// Verify the results
		assertNotNull(result);
	}

}

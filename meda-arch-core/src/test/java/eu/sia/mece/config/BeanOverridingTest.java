package eu.sia.meda.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

class BeanOverridingTest {

	private BeanOverriding beanOverridingUnderTest;

	@BeforeEach
	void setUp() {
		beanOverridingUnderTest = new BeanOverriding();
	}

	@Test
	void testPostProcessEnvironment() {
		// Setup
		final ConfigurableEnvironment environment = null;
		final SpringApplication application = new SpringApplication(Object.class);

		// Run the test
		try {
			beanOverridingUnderTest.postProcessEnvironment(environment, application);
			assertTrue(Boolean.TRUE);
		} catch (Exception e) {
			fail();
		}
	}
}

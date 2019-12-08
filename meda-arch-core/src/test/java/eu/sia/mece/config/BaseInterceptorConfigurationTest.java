package eu.sia.meda.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import eu.sia.meda.core.interceptors.utils.MedaInterceptorRegistry;

class BaseInterceptorConfigurationTest {

	@Mock
	private MedaInterceptorRegistry mockMedaInterceptorRegistry;

	private BaseInterceptorConfiguration baseInterceptorConfigurationUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
		baseInterceptorConfigurationUnderTest = new BaseInterceptorConfiguration(mockMedaInterceptorRegistry);
	}

	@Test
	void testAddInterceptors() {
		// Setup
		final InterceptorRegistry registry = new InterceptorRegistry();
		when(mockMedaInterceptorRegistry.getInterceptors()).thenReturn(Arrays.asList());

		// Run the test
		try {
			baseInterceptorConfigurationUnderTest.addInterceptors(registry);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
		// Verify the results

	}
}

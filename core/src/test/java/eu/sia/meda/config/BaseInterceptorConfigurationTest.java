package eu.sia.meda.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.servlet.HandlerInterceptor;
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
		HandlerInterceptor interceptor = Mockito.mock(HandlerInterceptor.class);
		when(mockMedaInterceptorRegistry.getInterceptors()).thenReturn(Collections.singletonList(interceptor));

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

package eu.sia.meda.tracing.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import eu.sia.meda.tracing.TracingManager;

class TracingControllerInterceptorTest {

	@Mock
	private TracingManager mockTracingManager;

	private TracingControllerInterceptor tracingControllerInterceptorUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
		tracingControllerInterceptorUnderTest = new TracingControllerInterceptor(mockTracingManager);
	}

	@Test
	void testPreHandle() throws Exception {
		// Setup
		final HttpServletRequest request = null;
		final HttpServletResponse response = null;

		// Run the test
		final boolean result = tracingControllerInterceptorUnderTest.preHandle(request, response, "handler");

		// Verify the results
		assertTrue(result);
	}
}

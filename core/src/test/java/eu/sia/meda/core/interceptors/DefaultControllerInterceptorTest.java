package eu.sia.meda.core.interceptors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.service.SessionContextRetriever;

class DefaultControllerInterceptorTest {

	@Mock
	private SessionContextRetriever mockSessionContextRetriever;

	@InjectMocks
	private DefaultControllerInterceptor defaultControllerInterceptorUnderTest;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@BeforeEach
	void setUp() {
		initMocks(this);
	}

	@Test
	void testPreHandle() throws Exception {
		// Setup
		final HttpServletRequest request = null;
		final HttpServletResponse response = null;

		// Configure SessionContextRetriever.loadSessionContext(...).
		final BaseContext baseContext = new BaseContext<>();
		when(mockSessionContextRetriever.loadSessionContext(any(HttpServletRequest.class))).thenReturn(baseContext);

		// Run the test
		final boolean result = defaultControllerInterceptorUnderTest.preHandle(request, response, "handler");

		// Verify the results
		assertTrue(result);
	}

	@Test
	void testAfterCompletion() {
		// Setup
		final Exception ex = new Exception("message");

		// Run the test
		try {
			defaultControllerInterceptorUnderTest.afterCompletion(request, response, "handler", ex);
			assertTrue(Boolean.TRUE);
		} catch (Exception e) {
			fail();
		}
	}
}

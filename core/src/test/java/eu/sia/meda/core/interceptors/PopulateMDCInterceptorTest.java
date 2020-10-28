package eu.sia.meda.core.interceptors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

class PopulateMDCInterceptorTest {

	private PopulateMDCInterceptor populateMDCInterceptorUnderTest;

	@Mock
	private HttpServletRequest httpServletRequest;
	@Mock
	private HttpServletResponse httpServletResponse;
	
	@BeforeEach
	void setUp() {
		initMocks(this);
		populateMDCInterceptorUnderTest = new PopulateMDCInterceptor();
	}

	@Test
	void testPreHandle() {
		// Setup
		// Run the test
		final boolean result = populateMDCInterceptorUnderTest.preHandle(httpServletRequest, httpServletResponse,
				"controller");

		// Verify the results
		assertTrue(result);
	}

	@Test
    void testPostHandle() {
        // Setup
        final ModelAndView modelAndView = new ModelAndView("viewName", new HashMap<>(), HttpStatus.CONTINUE);

        // Run the test
        try {
            populateMDCInterceptorUnderTest.postHandle(httpServletRequest, httpServletResponse, "o", modelAndView);
            assertTrue(Boolean.TRUE);
        }catch(Exception e) {
        	fail();
        }

        // Verify the results
    }


	@Test
	public void testPreHandleAuthenticationNotNull(){
		Authentication authenticationMock = Mockito.mock(Authentication.class);
		Mockito.when(authenticationMock.getName()).thenReturn("testValue");
		SecurityContextHolder.getContext().setAuthentication(authenticationMock);

		testPreHandle();

		Assert.assertEquals("testValue", MDC.get("user-id"));

	}
}

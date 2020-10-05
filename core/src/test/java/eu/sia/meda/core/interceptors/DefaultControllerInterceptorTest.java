package eu.sia.meda.core.interceptors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.sia.meda.core.controller.BaseController;
import eu.sia.meda.core.controller.StatelessController;
import eu.sia.meda.core.model.AuthorizationContext;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.service.SessionContextRetriever;
import org.mockito.Mockito;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.method.HandlerMethod;

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
		BaseContextHolder.clear();
		RequestContextHolder.clear();
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


	@Test
	void testPreHandleBadRequest() {
		// Setup
		final HttpServletRequest request = null;
		final HttpServletResponse response = null;
		HandlerMethod handler = Mockito.mock(HandlerMethod.class);

		// Run the test
		try{
			defaultControllerInterceptorUnderTest.preHandle(request, response, handler);
			Assert.fail("expected exception");
		}catch(Exception e){
			Assert.assertTrue(e instanceof MedaDomainRuntimeException);
		}
	}

	@Test
	void testPreHandleWithBaseController() throws Exception {
		// Setup
		ReflectionTestUtils.setField(defaultControllerInterceptorUnderTest,"isSecurityEnabled",true);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse response = null;
		HandlerMethod handler = Mockito.mock(HandlerMethod.class);
		Authentication authentication = Mockito.mock(Authentication.class);
		BaseController controller = new StatelessController();

		Mockito.when(handler.getBean()).thenReturn(controller);
		Mockito.when(request.getHeader(Mockito.eq("authorization"))).thenReturn("Authorization");
		Mockito.when(authentication.getName()).thenReturn("testAuthorization");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Run the test
		boolean result = defaultControllerInterceptorUnderTest.preHandle(request, response, handler);
		Assert.assertTrue(result);
		AuthorizationContext authorizationContext = BaseContextHolder.getAuthorizationContext();
		Assert.assertNull(authorizationContext.getAuthorizationHeader());

	}

	@Test
	void testPreHandleWithErrorController() throws Exception {
		// Setup
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse response = null;
		HandlerMethod handler = Mockito.mock(HandlerMethod.class);
		Authentication authentication = Mockito.mock(Authentication.class);
		ErrorController controller = Mockito.mock(ErrorController.class);

		Mockito.when(handler.getBean()).thenReturn(controller);

		// Run the test
		boolean result = defaultControllerInterceptorUnderTest.preHandle(request, response, handler);
		Assert.assertTrue(result);

	}
}

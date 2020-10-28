package eu.sia.meda.core.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.servlet.http.HttpServletRequest;

import eu.sia.meda.util.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RequestContextHolderTest {

	@Test
	void testGetRequest() {
		// Run the test
		try {
			RequestContextHolder.getRequest();
			assertTrue(Boolean.TRUE);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testSetRequestNull() {
		// Run the test
		try {
			RequestContextHolder.setRequest(null);
			fail();
		} catch (Exception e) {
			assertTrue(Boolean.TRUE);
		}
	}

	@Test
	void testForceSetRequest() {
		// Run the test
		try {
			RequestContextHolder.forceSetRequest(null);
			assertTrue(Boolean.TRUE);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testClear() {
		// Run the test
		try {
			RequestContextHolder.clear();
			assertTrue(Boolean.TRUE);
		} catch (Exception e) {
			fail();
		}
	}


	@Test
	void testSetRequest() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		// Run the test
		RequestContextHolder.setRequest(request);

		Assert.assertNotNull(RequestContextHolder.getRequest());
		TestUtils.reflectionEqualsByName(request,RequestContextHolder.getRequest());

		try {
			RequestContextHolder.setRequest(request);
			Assert.fail("expected exception");
		} catch (Exception e) {
			Assert.assertTrue( e instanceof IllegalStateException);
		}
	}

}

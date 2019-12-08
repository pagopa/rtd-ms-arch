package eu.sia.meda.core.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;

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
}

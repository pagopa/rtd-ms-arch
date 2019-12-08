package eu.sia.meda.core.filter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestLoggingFilterTest {

	private RequestLoggingFilter requestLoggingFilterUnderTest;

	@BeforeEach
	void setUp() {
		requestLoggingFilterUnderTest = new RequestLoggingFilter();
	}

	@Test
	protected void TestInitFilterBean() {
		try {
			requestLoggingFilterUnderTest.initFilterBean();
			assertTrue(Boolean.TRUE);
		}catch(Exception e) {
			fail();
		}
	}

	@Test
	protected void TestBeforeRequest() {
		try {
			requestLoggingFilterUnderTest.beforeRequest(null, "msg");
			assertTrue(Boolean.TRUE);
		}catch(Exception e) {
			fail();
		}
	}

	@Test
	protected void TestAfterRequest() {
		try {
			requestLoggingFilterUnderTest.afterRequest(null, "msg");
			assertTrue(Boolean.TRUE);
		}catch(Exception e) {
			fail();
		}
	}
}

package eu.sia.meda.core.controller;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class StatelessControllerTest {

	@Mock
	private StatelessController statelessControllerUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
	}

	@Test
	void testGetForwardedFor() {
		// Setup
		when(statelessControllerUnderTest.getRequest()).thenReturn(mock(HttpServletRequest.class));
		// Run the test - empty request
		final String result = statelessControllerUnderTest.getForwardedFor();

		// Verify the results
		assertNull(result);
	}

	@Test
	void testGetFunctionName() {
		// Setup
		when(statelessControllerUnderTest.getRequest()).thenReturn(mock(HttpServletRequest.class));
		// Run the test - empty request
		final String result = statelessControllerUnderTest.getFunctionName();

		// Verify the results
		assertNull(result);
	}
}

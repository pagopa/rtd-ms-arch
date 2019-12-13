package eu.sia.meda.cache.http;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ForcingEtagHeaderFilterTest {

	private ForcingEtagHeaderFilter forcingEtagHeaderFilterUnderTest;

	@BeforeEach
	void setUp() {
		forcingEtagHeaderFilterUnderTest = new ForcingEtagHeaderFilter();
	}

	@Test
	void testSetCacheAndEtag() {
		// Verify the results
		boolean result = forcingEtagHeaderFilterUnderTest.isEligibleForEtag(mock(HttpServletRequest.class), null, 200, null);
		assertFalse(result);
	}
}

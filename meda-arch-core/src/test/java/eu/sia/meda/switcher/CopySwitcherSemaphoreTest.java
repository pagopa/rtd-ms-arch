package eu.sia.meda.switcher;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CopySwitcherSemaphoreTest {

	private CopySwitcherSemaphore copySwitcherSemaphoreUnderTest;

	@BeforeEach
	void setUp() {
		copySwitcherSemaphoreUnderTest = new CopySwitcherSemaphore();
	}

	@Test
	void testToString() {
		// Verify the results
		assertNotNull(copySwitcherSemaphoreUnderTest.toString());
	}
}

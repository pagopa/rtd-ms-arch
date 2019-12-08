package eu.sia.meda.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CopySwitcherDefaultSemaphoreConfigTest {

	private CopySwitcherDefaultSemaphoreConfig copySwitcherDefaultSemaphoreConfigUnderTest;

	@BeforeEach
	void setUp() {
		copySwitcherDefaultSemaphoreConfigUnderTest = new CopySwitcherDefaultSemaphoreConfig();
	}

	@Test
	void testConfigBaseUrl() {
		// Setup
		// Run the test
		copySwitcherDefaultSemaphoreConfigUnderTest.setConfigBaseUrl("configBaseUrl");
		assertEquals("configBaseUrl", copySwitcherDefaultSemaphoreConfigUnderTest.getConfigBaseUrl());
	}

	@Test
	void testRefreshDelaySeconds() {
		// Setup
		// Run the test
		copySwitcherDefaultSemaphoreConfigUnderTest.setRefreshDelaySeconds(123L);
		assertEquals(Long.valueOf(123), copySwitcherDefaultSemaphoreConfigUnderTest.getRefreshDelaySeconds());
	}

	@Test
	void testSemaphores() {
		// Setup
		// Run the test
		assertNotNull(copySwitcherDefaultSemaphoreConfigUnderTest.getSemaphores());
		copySwitcherDefaultSemaphoreConfigUnderTest.getSemaphores().add("test");
		assertEquals("test", copySwitcherDefaultSemaphoreConfigUnderTest.getSemaphores().get(0));
	}

	@Test
	void testValidities() {
		// Setup
		// Run the test
		assertNotNull(copySwitcherDefaultSemaphoreConfigUnderTest.getValidities());
		copySwitcherDefaultSemaphoreConfigUnderTest.getValidities().put("test", Long.valueOf(123));
		assertEquals(Long.valueOf(123), copySwitcherDefaultSemaphoreConfigUnderTest.getValidities().get("test"));
	}
}

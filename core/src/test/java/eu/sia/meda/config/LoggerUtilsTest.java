package eu.sia.meda.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class LoggerUtilsTest {

	@Test
	void testFormatArchRow() {
		assertEquals("[MEDA-ARCH] msg", LoggerUtils.formatArchRow("msg"));
	}

	@Test
	void testFormatRow() {
		assertEquals("[label] msg", LoggerUtils.formatRow("label", "msg"));
	}

	@Test
	void testGetLogger() {
		// Run the test
		final Logger result = LoggerUtils.getLogger("name");

		// Verify the results
		assertNotNull(result);
	}

	@Test
	void testGetLogger1() {
		// Run the test
		final Logger result = LoggerUtils.getLogger(Object.class);

		// Verify the results
		assertNotNull(result);
	}
}

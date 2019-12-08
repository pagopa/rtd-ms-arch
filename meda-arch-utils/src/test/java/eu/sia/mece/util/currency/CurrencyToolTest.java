package eu.sia.meda.util.currency;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyToolTest {

	private CurrencyTool currencyToolUnderTest;

	@BeforeEach
	void setUp() {
		currencyToolUnderTest = new CurrencyTool("IT");
	}

	@Test
	void testFromString() throws Exception {
		// Setup
		final BigDecimal expectedResult = new BigDecimal("10.00");

		// Run the test
		final BigDecimal result = currencyToolUnderTest.fromString("10.00");

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testFromStringThrowsNumberFormatException() {
		// Run the test
		assertThrows(NumberFormatException.class, () -> {
			currencyToolUnderTest.fromString("10,0");
		});
	}

	@Test
	public void testFromStringThrowsNullPointerException() {

		try {
			currencyToolUnderTest.fromString((String) null);
			fail("Expecting exception: NullPointerException");

		} catch (NullPointerException e) {
			//
			// no message in exception (getMessage() returned null)
			//
			assertTrue(Boolean.TRUE);
		}
	}
}

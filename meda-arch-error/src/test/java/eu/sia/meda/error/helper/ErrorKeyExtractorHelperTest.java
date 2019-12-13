package eu.sia.meda.error.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;

import eu.sia.meda.exceptions.IMedaDomainException;

class ErrorKeyExtractorHelperTest {

	@Test
	void testGetConstraintViolationExceptionErrorKeys() {
		// Setup
		final ConstraintViolationException e = new ConstraintViolationException("message", new HashSet<>());

		// Run the test
		final List<String> result = ErrorKeyExtractorHelper.getConstraintViolationExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));
	}

	@Test
	void testGetMethodArgumentNotValidExceptionErrorKeys() {
		// Setup
		final MethodArgumentNotValidException e = new MethodArgumentNotValidException(null, null);
		// Run the test
		final List<String> result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));
	}

	@Test
	void testGetMedaDomainExceptionErrorKey() {
		// Setup
		final IMedaDomainException e = null;

		// Run the test
		final String result = ErrorKeyExtractorHelper.getMedaDomainExceptionErrorKey(e);

		// Verify the results
		assertEquals("generic.error", result);
	}

	@Test
	void testCleanErrorKey() {
		assertEquals("errorKey", ErrorKeyExtractorHelper.cleanErrorKey("errorKey"));
	}
}

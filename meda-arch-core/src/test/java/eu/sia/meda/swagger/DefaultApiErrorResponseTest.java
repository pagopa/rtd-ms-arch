package eu.sia.meda.swagger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.OperationNameGenerator;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;

class DefaultApiErrorResponseTest {

	@Mock
	private TypeResolver mockResolver;

	private DefaultApiErrorResponse defaultApiErrorResponseUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
		defaultApiErrorResponseUnderTest = new DefaultApiErrorResponse(mockResolver);
	}

	@Test
	void testApply() {
		// Setup
		final OperationContext operationContext = new OperationContext(
				new OperationBuilder(new OperationNameGenerator() {

					@Override
					public String startingWith(String prefix) {
						return prefix;
					}
				}), RequestMethod.GET, null, 0);

		// Run the test
		try {
			defaultApiErrorResponseUnderTest.apply(operationContext);
			assertTrue(Boolean.TRUE);
		} catch (Exception e) {
			fail();
		}

		// Verify the results
	}

	@Test
	void testSupports() {
		// Setup
		final DocumentationType documentationType = new DocumentationType("name", "version");

		// Run the test
		final boolean result = defaultApiErrorResponseUnderTest.supports(documentationType);

		// Verify the results
		assertTrue(result);
	}
}

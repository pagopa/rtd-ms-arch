package eu.sia.meda.error.connector.transformer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.sia.meda.connector.rest.model.RestConnectorRequest;

class GetErrorMessagesRestRequestTransformerTest {

	private GetErrorMessagesRestRequestTransformer getErrorMessagesRestRequestTransformerUnderTest;

	@BeforeEach
	void setUp() {
		getErrorMessagesRestRequestTransformerUnderTest = new GetErrorMessagesRestRequestTransformer();
	}

	@Test
	void testTransform() {
		// Setup
		final Collection<String> params = Arrays.asList();

		// Run the test
		final RestConnectorRequest<Collection<String>> result = getErrorMessagesRestRequestTransformerUnderTest
				.transform(params, "args");

		// Verify the results
		assertNotNull(result);
	}
}

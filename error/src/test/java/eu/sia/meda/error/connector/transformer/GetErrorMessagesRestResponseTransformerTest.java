package eu.sia.meda.error.connector.transformer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.error.resource.ExtendedErrorMessageResource;
import eu.sia.meda.exceptions.model.ErrorMessage;

class GetErrorMessagesRestResponseTransformerTest {

	private GetErrorMessagesRestResponseTransformer getErrorMessagesRestResponseTransformerUnderTest;

	@BeforeEach
	void setUp() {
		getErrorMessagesRestResponseTransformerUnderTest = new GetErrorMessagesRestResponseTransformer();
	}

	@Test
	void testTransform() {
		// Setup
		final RestConnectorResponse<Map<String, List<ExtendedErrorMessageResource>>> restConnectorResponse = new RestConnectorResponse<>();
		restConnectorResponse.setResponse(ResponseEntity.ok().build());
		// Run the test
		final Map<String, List<ErrorMessage>> result = getErrorMessagesRestResponseTransformerUnderTest
				.transform(restConnectorResponse);

		// Verify the results
		assertNotNull(result);
	}
}

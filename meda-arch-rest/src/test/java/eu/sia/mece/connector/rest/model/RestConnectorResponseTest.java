package eu.sia.meda.connector.rest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestConnectorResponseTest {

    private RestConnectorResponse<T> restConnectorResponseUnderTest;

    @BeforeEach
    void setUp() {
        restConnectorResponseUnderTest = new RestConnectorResponse<>();
    }

    @Test
    void testGetResult() {
        // Setup
        final RestConnectorResponse<T> expectedResult = new RestConnectorResponse<>();

        // Run the test
        final RestConnectorResponse<T> result = restConnectorResponseUnderTest.getResult();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

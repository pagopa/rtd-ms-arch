package eu.sia.meda.connector.rest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class RestConnectorRequestTest {

    @Mock
    private HttpHeaders mockHttpHeaders;

    private RestConnectorRequest<INPUT> restConnectorRequestUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        restConnectorRequestUnderTest = new RestConnectorRequest<>(mockHttpHeaders, null, new HashMap<>(), new HashMap<>(), HttpMethod.GET);
    }

    @Test
    void testAddParams() {
        // Setup

        // Run the test
        restConnectorRequestUnderTest.addParams("key", "value");

        // Verify the results
    }

    @Test
    void testGetQueryParams() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, String> result = restConnectorRequestUnderTest.getQueryParams();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSetQueryParams() {
        // Setup
        final Map<String, String> queryParams = new HashMap<>();

        // Run the test
        restConnectorRequestUnderTest.setQueryParams(queryParams);

        // Verify the results
    }

    @Test
    void testAddHeader() {
        // Setup

        // Run the test
        restConnectorRequestUnderTest.addHeader("key", "value");

        // Verify the results
        verify(mockHttpHeaders).add("headerName", "headerValue");
    }

    @Test
    void testAddHeaderIfExists() {
        // Setup

        // Run the test
        final boolean result = restConnectorRequestUnderTest.addHeaderIfExists("key", "value");

        // Verify the results
        assertTrue(result);
        verify(mockHttpHeaders).add("headerName", "headerValue");
    }
}

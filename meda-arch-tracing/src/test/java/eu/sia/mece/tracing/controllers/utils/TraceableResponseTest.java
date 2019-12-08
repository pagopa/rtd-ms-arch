package eu.sia.meda.tracing.controllers.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TraceableResponseTest {

    @Mock
    private HttpServletResponse mockResponse;

    private TraceableResponse traceableResponseUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        traceableResponseUnderTest = new TraceableResponse(mockResponse, 0);
    }

    @Test
    void testGetBody() {
        // Setup
        final byte[] expectedResult = "content".getBytes();

        // Run the test
        final byte[] result = traceableResponseUnderTest.getBody();

        // Verify the results
        assertArrayEquals(expectedResult, result);
    }

    @Test
    void testGetHeaderNamesIterator() {
        // Setup
        final Iterator<String> expectedResult = null;

        // Run the test
        final Iterator<String> result = traceableResponseUnderTest.getHeaderNamesIterator();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetHeadersIterator() {
        // Setup
        final Iterator<String> expectedResult = null;

        // Run the test
        final Iterator<String> result = traceableResponseUnderTest.getHeadersIterator("name");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testShouldBeTraced() {
        // Setup

        // Run the test
        final boolean result = traceableResponseUnderTest.shouldBeTraced();

        // Verify the results
        assertTrue(result);
    }
}

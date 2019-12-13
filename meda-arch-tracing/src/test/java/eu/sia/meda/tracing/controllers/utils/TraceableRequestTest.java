package eu.sia.meda.tracing.controllers.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

class TraceableRequestTest {

    @Mock
    private HttpServletRequest mockRequest;

    private TraceableRequest traceableRequestUnderTest;

    @BeforeEach
    void setUp() throws IOException {
        initMocks(this);
        traceableRequestUnderTest = new TraceableRequest(mockRequest, 0);
    }

    @Test
    void testGetBody() {
        // Setup
        final byte[] expectedResult = "content".getBytes();

        // Run the test
        final byte[] result = traceableRequestUnderTest.getBody();

        // Verify the results
        assertArrayEquals(expectedResult, result);
    }

    @Test
    void testGetInputStream() {
        // Setup
        final ServletInputStream expectedResult = null;

        // Run the test
        final ServletInputStream result = traceableRequestUnderTest.getInputStream();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetHeaderNamesIterator() {
        // Setup
        final Iterator<String> expectedResult = null;

        // Run the test
        final Iterator<String> result = traceableRequestUnderTest.getHeaderNamesIterator();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetHeadersIterator() {
        // Setup
        final Iterator<String> expectedResult = null;

        // Run the test
        final Iterator<String> result = traceableRequestUnderTest.getHeadersIterator("name");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

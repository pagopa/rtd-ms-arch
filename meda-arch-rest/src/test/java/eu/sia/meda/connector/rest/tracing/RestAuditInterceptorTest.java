package eu.sia.meda.connector.rest.tracing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestAuditInterceptorTest {

    private RestAuditInterceptor restAuditInterceptorUnderTest;

    @BeforeEach
    void setUp() {
        restAuditInterceptorUnderTest = new RestAuditInterceptor("codService");
    }

    @Test
    void testIntercept() throws Exception {
        // Setup
        final HttpRequest request = null;
        final ClientHttpRequestExecution execution = null;
        final ClientHttpResponse expectedResult = null;

        // Run the test
        final ClientHttpResponse result = restAuditInterceptorUnderTest.intercept(request, "content".getBytes(), execution);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testIntercept_ThrowsIOException() {
        // Setup
        final HttpRequest request = null;
        final ClientHttpRequestExecution execution = null;

        // Run the test
        assertThrows(IOException.class, () -> {
            restAuditInterceptorUnderTest.intercept(request, "content".getBytes(), execution);
        });
    }
}

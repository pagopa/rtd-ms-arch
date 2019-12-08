package eu.sia.meda.connector.rest.tracing;

import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.TracingMessageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class RestTracingInterceptorTest {

    @Mock
    private TracingManager mockTracingManager;

    private RestTracingInterceptor restTracingInterceptorUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        restTracingInterceptorUnderTest = new RestTracingInterceptor(mockTracingManager);
    }

    @Test
    void testIntercept() throws Exception {
        // Setup
        final HttpRequest request = null;
        final ClientHttpRequestExecution execution = null;
        final ClientHttpResponse expectedResult = null;

        // Run the test
        final ClientHttpResponse result = restTracingInterceptorUnderTest.intercept(request, "content".getBytes(), execution);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockTracingManager).trace(any(TracingMessageBuilder.class));
    }

    @Test
    void testIntercept_ThrowsIOException() {
        // Setup
        final HttpRequest request = null;
        final ClientHttpRequestExecution execution = null;

        // Run the test
        assertThrows(IOException.class, () -> {
            restTracingInterceptorUnderTest.intercept(request, "content".getBytes(), execution);
        });
        verify(mockTracingManager).trace(any(TracingMessageBuilder.class));
    }
}

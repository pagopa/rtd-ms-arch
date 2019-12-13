package eu.sia.meda.tracing.event;

import eu.sia.meda.event.request.EventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TracingEventRequestTransformerTest {

    private TracingEventRequestTransformer tracingEventRequestTransformerUnderTest;

    @BeforeEach
    void setUp() {
        tracingEventRequestTransformerUnderTest = new TracingEventRequestTransformer();
    }

    @Test
    void testTransform() {
        // Setup
        final EventRequest<String> expectedResult = new EventRequest<>();
        expectedResult.setKey("key");
        expectedResult.setPayload("content".getBytes());
        expectedResult.setHeaders(null);
        expectedResult.setTopic("topic");

        // Run the test
        final EventRequest<String> result = tracingEventRequestTransformerUnderTest.transform("message", "args");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

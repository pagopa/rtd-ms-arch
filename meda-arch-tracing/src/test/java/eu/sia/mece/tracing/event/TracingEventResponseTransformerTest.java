package eu.sia.meda.tracing.event;

import eu.sia.meda.event.response.EventResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TracingEventResponseTransformerTest {

    private TracingEventResponseTransformer tracingEventResponseTransformerUnderTest;

    @BeforeEach
    void setUp() {
        tracingEventResponseTransformerUnderTest = new TracingEventResponseTransformer();
    }

    @Test
    void testTransform() {
        // Setup
        final EventResponse<Void> eventResponse = new EventResponse<>(false, "message");

        // Run the test
        final Boolean result = tracingEventResponseTransformerUnderTest.transform(eventResponse);

        // Verify the results
        assertTrue(result);
    }
}

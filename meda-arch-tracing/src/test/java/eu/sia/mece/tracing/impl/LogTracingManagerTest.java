package eu.sia.meda.tracing.impl;

import eu.sia.meda.tracing.TracingMessageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogTracingManagerTest {

    private LogTracingManager logTracingManagerUnderTest;

    @BeforeEach
    void setUp() {
        logTracingManagerUnderTest = new LogTracingManager();
    }

    @Test
    void testTrace() {
        // Setup
        final TracingMessageBuilder message = new TracingMessageBuilder("title");

        // Run the test
        logTracingManagerUnderTest.trace(message);

        // Verify the results
    }
}

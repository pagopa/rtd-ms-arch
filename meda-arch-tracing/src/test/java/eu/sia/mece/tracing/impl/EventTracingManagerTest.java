package eu.sia.meda.tracing.impl;

import eu.sia.meda.event.transformer.IEventRequestTransformer;
import eu.sia.meda.event.transformer.IEventResponseTransformer;
import eu.sia.meda.tracing.TracingMessageBuilder;
import eu.sia.meda.tracing.event.TracingEventConnector;
import eu.sia.meda.tracing.event.TracingEventRequestTransformer;
import eu.sia.meda.tracing.event.TracingEventResponseTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class EventTracingManagerTest {

    @Mock
    private TracingEventConnector mockTracingEventConnector;
    @Mock
    private TracingEventRequestTransformer mockRequestTransformer;
    @Mock
    private TracingEventResponseTransformer mockResponseTransformer;

    private EventTracingManager eventTracingManagerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        eventTracingManagerUnderTest = new EventTracingManager(mockTracingEventConnector, mockRequestTransformer, mockResponseTransformer);
    }

    @Test
    void testTrace() {
        // Setup
        final TracingMessageBuilder message = new TracingMessageBuilder("title");
        when(mockTracingEventConnector.call(eq("input"), any(IEventRequestTransformer.class), any(IEventResponseTransformer.class), any(Object.class))).thenReturn(false);

        // Run the test
        eventTracingManagerUnderTest.trace(message);

        // Verify the results
    }
}

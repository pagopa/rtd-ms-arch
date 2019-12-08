package eu.sia.meda.tracing.controllers;

import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.tracing.TracingManager;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

class TracingControllerFilterTest {

    @Mock
    private PropertiesManager mockPropertiesManager;
    @Mock
    private TracingManager mockTracingManager;

    private TracingControllerFilter tracingControllerFilterUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        tracingControllerFilterUnderTest = new TracingControllerFilter(mockPropertiesManager, mockTracingManager);
    }
}

package eu.sia.meda.tracing.controllers;

import eu.sia.meda.core.interceptors.DefaultControllerInterceptor;
import eu.sia.meda.core.interceptors.PopulateMDCInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

class TracingInterceptorRegistryTest {

    @Mock
    private PopulateMDCInterceptor mockPopulateMDCInterceptor;
    @Mock
    private DefaultControllerInterceptor mockDefaultControllerInterceptor;
    @Mock
    private TracingControllerInterceptor mockTracingControllerInterceptor;

    private TracingInterceptorRegistry tracingInterceptorRegistryUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        tracingInterceptorRegistryUnderTest = new TracingInterceptorRegistry(mockPopulateMDCInterceptor, mockDefaultControllerInterceptor, mockTracingControllerInterceptor);
    }
}

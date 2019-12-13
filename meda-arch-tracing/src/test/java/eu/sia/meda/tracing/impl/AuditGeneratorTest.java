package eu.sia.meda.tracing.impl;

import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.domain.model.be4fe.TraceGen;
import eu.sia.meda.service.SessionContextAuditMapper;
import eu.sia.meda.tracing.AuditManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.ws.context.MessageContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class AuditGeneratorTest {

    @Mock
    private AuditManager mockAuditManager;
    @Mock
    private SessionContextAuditMapper mockSessionContextAuditMapper;

    @InjectMocks
    private AuditGenerator auditGeneratorUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testPostConstruct() {
        // Setup

        // Run the test
        auditGeneratorUnderTest.postConstruct();

        // Verify the results
    }

    @Test
    void testTraceRest() {
        // Setup
        final HttpRequest request = null;
        final Throwable t = new Throwable("message");

        // Run the test
        auditGeneratorUnderTest.traceRest("codService", request, "content".getBytes(), t);

        // Verify the results
        verify(mockSessionContextAuditMapper).mapSessionContextToTraceGen(any(TraceGen.class), any(BaseContext.class));
        verify(mockAuditManager).trace(any(TraceGen.class));
    }

    @Test
    void testTraceRest1() {
        // Setup
        final HttpRequest request = null;
        final ClientHttpResponse response = null;

        // Run the test
        auditGeneratorUnderTest.traceRest("codService", request, "content".getBytes(), response);

        // Verify the results
        verify(mockSessionContextAuditMapper).mapSessionContextToTraceGen(any(TraceGen.class), any(BaseContext.class));
        verify(mockAuditManager).trace(any(TraceGen.class));
    }

    @Test
    void testTraceSoap() {
        // Setup
        final MessageContext messageContext = null;

        // Run the test
        auditGeneratorUnderTest.traceSoap("codService", messageContext, false);

        // Verify the results
        verify(mockSessionContextAuditMapper).mapSessionContextToTraceGen(any(TraceGen.class), any(BaseContext.class));
        verify(mockAuditManager).trace(any(TraceGen.class));
    }

    @Test
    void testTraceSoap1() {
        // Setup
        final MessageContext messageContext = null;
        final Exception ex = new Exception("message");

        // Run the test
        auditGeneratorUnderTest.traceSoap("codService", messageContext, ex);

        // Verify the results
        verify(mockSessionContextAuditMapper).mapSessionContextToTraceGen(any(TraceGen.class), any(BaseContext.class));
        verify(mockAuditManager).trace(any(TraceGen.class));
    }
}

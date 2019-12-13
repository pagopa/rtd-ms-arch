package eu.sia.meda.audit.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.sia.meda.audit.connector.rest.AuditRestConnector;
import eu.sia.meda.audit.connector.rest.transformer.AuditRestRequestTransformer;
import eu.sia.meda.audit.connector.rest.transformer.AuditRestResponseTransformer;
import eu.sia.meda.domain.model.be4fe.TraceGen;

class RestAuditManagerTest {

	private RestAuditManager restAuditManagerUnderTest;

	@BeforeEach
	void setUp() {
		restAuditManagerUnderTest = new RestAuditManager();
		restAuditManagerUnderTest.auditRestConnector = mock(AuditRestConnector.class);
		restAuditManagerUnderTest.auditRestRequestTransformer = mock(AuditRestRequestTransformer.class);
		restAuditManagerUnderTest.auditRestResponseTransformer = mock(AuditRestResponseTransformer.class);
	}

	@Test
    void testTrace() {
        // Setup
        final TraceGen traceGen = new TraceGen();
        traceGen.setCodService("codService");
        traceGen.setDataInoltro(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        traceGen.setDataRicezione(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        traceGen.setPrefixMessage("prefixMessage");
        traceGen.setReturnCode("returnCode");
        traceGen.setReturnDesc("returnDesc");

        // Run the test
        try {
        	restAuditManagerUnderTest.trace(traceGen);
        	assertTrue(Boolean.TRUE);
        } catch(Exception e) {
        	fail();
        }
    }
}

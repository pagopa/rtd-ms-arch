package eu.sia.meda.tracing.impl;

import eu.sia.meda.domain.model.be4fe.TraceGen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

class LogAuditManagerTest {

    private LogAuditManager logAuditManagerUnderTest;

    @BeforeEach
    void setUp() {
        logAuditManagerUnderTest = new LogAuditManager();
    }

    @Test
    void testTrace() {
        // Setup
        final TraceGen traceGen = new TraceGen();
        traceGen.setCodAbi("codAbi");
        traceGen.setCodChannel("codChannel");
        traceGen.setCodService("codService");
        traceGen.setDataInoltro(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        traceGen.setDataRicezione(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        traceGen.setPrefixMessage("prefixMessage");
        traceGen.setReturnCode("returnCode");
        traceGen.setReturnDesc("returnDesc");

        // Run the test
        logAuditManagerUnderTest.trace(traceGen);

        // Verify the results
    }
}

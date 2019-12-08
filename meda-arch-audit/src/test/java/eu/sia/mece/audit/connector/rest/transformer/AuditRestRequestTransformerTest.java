package eu.sia.meda.audit.connector.rest.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.sia.meda.audit.dto.TraceGenDto;
import eu.sia.meda.connector.rest.model.RestConnectorRequest;
import eu.sia.meda.domain.model.be4fe.TraceGen;

class AuditRestRequestTransformerTest {

    private AuditRestRequestTransformer auditRestRequestTransformerUnderTest;

    @BeforeEach
    void setUp() {
        auditRestRequestTransformerUnderTest = new AuditRestRequestTransformer();
    }

    @Test
    void testTransform() {
        // Setup
        final TraceGen om = new TraceGen();
        om.setCodService("codService");
        om.setDataInoltro(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        om.setDataRicezione(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        om.setPrefixMessage("prefixMessage");
        om.setReturnCode("returnCode");
        om.setReturnDesc("returnDesc");

        // Run the test
        final RestConnectorRequest<TraceGenDto> result = auditRestRequestTransformerUnderTest.transform(om);

        // Verify the results
        assertNotNull(result);
        assertEquals(result.getRequest().getCodService(), om.getCodService());
        assertEquals(result.getRequest().getPrefixMessage(), om.getPrefixMessage());
        assertEquals(result.getRequest().getReturnCode(), om.getReturnCode());
        assertEquals(result.getRequest().getReturnDesc(), om.getReturnDesc());
        assertEquals(result.getRequest().getDataInoltro(), om.getDataInoltro());
        assertEquals(result.getRequest().getDataRicezione(), om.getDataRicezione());
    }
}

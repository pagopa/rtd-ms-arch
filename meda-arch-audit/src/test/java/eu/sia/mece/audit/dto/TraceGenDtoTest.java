package eu.sia.meda.audit.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraceGenDtoTest {

    private TraceGenDto traceGenDtoUnderTest;

    @BeforeEach
    void setUp() {
        traceGenDtoUnderTest = new TraceGenDto();
        traceGenDtoUnderTest.setCodService("codService");
        traceGenDtoUnderTest.setDataInoltro(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        traceGenDtoUnderTest.setDataRicezione(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        traceGenDtoUnderTest.setPrefixMessage("prefixMessage");
        traceGenDtoUnderTest.setReturnCode("returnCode");
        traceGenDtoUnderTest.setReturnDesc("returnDesc");

    }

    @Test
    void testToString() {
        // Run the test
        final String result = traceGenDtoUnderTest.toString();

        // Verify the results
        assertNotNull(result);
    }
}

package eu.sia.meda.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

public class DateUtilsTest {

//    @Test
//    void testParseStringToLocalDate() {
//        assertEquals(LocalDate.of(2019, 1, 1), DateUtils.parseStringToLocalDate("dateAsString"));
//    }

    @Test
    void testFormatDateToString() {
        assertEquals("2019-01-01", DateUtils.formatDateToString(LocalDate.of(2019, 1, 1)));
    }

//    @Test
//    void testParseStringToLocalDateTime() {
//        assertEquals(LocalDateTime.of(2019, 1, 1, 0, 0, 0), DateUtils.parseStringToLocalDateTime("dateTimeAsString"));
//    }

    @Test
    void testFormatDateToString1() {
        assertEquals("2019-01-01T00:00:00", DateUtils.formatDateToString(LocalDateTime.of(2019, 1, 1, 0, 0, 0)));
    }

//    @Test
//    void testFormatDateToString2() {
//        // Setup
//        final OffsetDateTime date = OffsetDateTime.parse("2019-01-01");
//        final String expectedResult = "2019-01-01";
//
//        // Run the test
//        final String result = DateUtils.formatDateToString(date);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }

//    @Test
//    void testParseStringToDate() {
//        assertEquals(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), DateUtils.parseStringToDate("2019-01-01T00:00:00"));
//    }

    @Test
    void testFormatDateToString3() {
        assertEquals("2019-01-01T00:00:00.000+01", DateUtils.formatDateToString(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    void testFormatDateToDay() {
        assertEquals("2019-01-01", DateUtils.formatDateToDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }
}

package eu.sia.meda.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogMaskUtilsTest {

    @Test
    void testMaskFields() {
        assertEquals("result", LogMaskUtils.maskFields(new Object[]{}));
    }

    @Test
    void testMaskFields1() {
        assertEquals("result", LogMaskUtils.maskFields("s"));
    }
}

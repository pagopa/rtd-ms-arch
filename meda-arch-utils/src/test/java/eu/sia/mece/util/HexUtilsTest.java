package eu.sia.meda.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HexUtilsTest {

    @Test
    void testToHexString() {
        assertEquals("result", HexUtils.toHexString("content".getBytes()));
    }

    @Test
    void testFromHexString() {
        assertArrayEquals("content".getBytes(), HexUtils.fromHexString("string"));
    }
}

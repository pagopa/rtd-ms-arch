package eu.sia.meda.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HashUtilsTest {

    private HashUtils hashUtilsUnderTest;

    @BeforeEach
    void setUp() {
        hashUtilsUnderTest = new HashUtils();
    }

    @Test
    void testHash() {
        // Setup
        final String expectedResult = "result";

        // Run the test
        final String result = hashUtilsUnderTest.hash("hexString");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testHash1() {
        // Setup
        final byte[] expectedResult = "content".getBytes();

        // Run the test
        final byte[] result = hashUtilsUnderTest.hash("content".getBytes());

        // Verify the results
        assertArrayEquals(expectedResult, result);
    }
}

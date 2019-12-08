package eu.sia.meda.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StopWatchTest {

    private StopWatch stopWatchUnderTest;

    @BeforeEach
    void setUp() {
        stopWatchUnderTest = new StopWatch();
    }

    @Test
    void testStart() {
        // Setup

        // Run the test
        stopWatchUnderTest.start();

        // Verify the results
    }

    @Test
    void testPause() {
        // Setup

        // Run the test
        stopWatchUnderTest.pause();

        // Verify the results
    }

    @Test
    void testReset() {
        // Setup

        // Run the test
        stopWatchUnderTest.reset();

        // Verify the results
    }

    @Test
    void testGetElapsedTime() {
        // Setup
        final long expectedResult = 0L;

        // Run the test
        final long result = stopWatchUnderTest.getElapsedTime();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToString() {
        // Setup
        final String expectedResult = "result";

        // Run the test
        final String result = stopWatchUnderTest.toString();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

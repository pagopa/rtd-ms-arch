package eu.sia.meda.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

class MeasureUtilsTest {

    @Test
    void testMeasure() {
        // Setup
        final Callable<T> callable = null;
        final T expectedResult = null;

        // Run the test
        final T result = MeasureUtils.measure(callable);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

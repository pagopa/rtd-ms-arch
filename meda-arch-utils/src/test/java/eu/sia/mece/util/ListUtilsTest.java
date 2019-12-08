package eu.sia.meda.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListUtilsTest {

    @Test
    void testSubList() {
        // Setup
        final List<T> list = Arrays.asList();
        final List<T> expectedResult = Arrays.asList();

        // Run the test
        final List<T> result = ListUtils.subList(list, 0L, 0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

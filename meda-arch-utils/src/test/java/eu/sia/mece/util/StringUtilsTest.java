package eu.sia.meda.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilsTest {

    @Test
    void testLowerCaseFirstLetter() {
        assertEquals("result", StringUtils.lowerCaseFirstLetter("wordToCapitalize"));
    }

    @Test
    void testToHexListString() {
        // Setup
        final List<Long> listToConvert = Arrays.asList();
        final String expectedResult = "result";

        // Run the test
        final String result = StringUtils.toHexListString(listToConvert);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testLoadResourceAsString() throws Exception {
        assertEquals("result", StringUtils.loadResourceAsString("mockResource"));
        assertThrows(IOException.class, () -> {
            StringUtils.loadResourceAsString("mockResource");
        });
    }

    @Test
    void testReplaceInOrder() {
        // Setup
        final Pattern pattern = Pattern.compile("regex");
        final T[] values = new T[]{};
        final String expectedResult = "result";

        // Run the test
        final String result = StringUtils.replaceInOrder("original", pattern, values);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

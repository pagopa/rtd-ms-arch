package eu.sia.meda.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MockUtilsTest {

    @Test
    void testGetMockResourcePath() throws Exception {
        // Setup
        final List<String> files = Arrays.asList();
        final List<String> expectedResult = Arrays.asList();

        // Run the test
        final List<String> result = MockUtils.getMockResourcePath("resourcePath", files, "extension");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetMockResourcePath_ThrowsIOException() {
        // Setup
        final List<String> files = Arrays.asList();

        // Run the test
        assertThrows(IOException.class, () -> {
            MockUtils.getMockResourcePath("resourcePath", files, "extension");
        });
    }

    @Test
    void testSelectRandomMockLocation() {
        // Setup
        final List<String> mockLocations = Arrays.asList();
        final String expectedResult = "result";

        // Run the test
        final String result = MockUtils.selectRandomMockLocation(mockLocations);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

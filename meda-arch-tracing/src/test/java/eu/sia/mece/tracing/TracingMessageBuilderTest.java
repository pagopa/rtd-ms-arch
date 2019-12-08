package eu.sia.meda.tracing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TracingMessageBuilderTest {

    private TracingMessageBuilder tracingMessageBuilderUnderTest;

    @BeforeEach
    void setUp() {
        tracingMessageBuilderUnderTest = new TracingMessageBuilder("title");
    }

    @Test
    void testNewSection() {
        // Setup
        final TracingMessageBuilder expectedResult = new TracingMessageBuilder("title");

        // Run the test
        final TracingMessageBuilder result = tracingMessageBuilderUnderTest.newSection("name");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testNewEntry() {
        // Setup
        final T value = null;
        final TracingMessageBuilder expectedResult = new TracingMessageBuilder("title");

        // Run the test
        final TracingMessageBuilder result = tracingMessageBuilderUnderTest.newEntry("key", value);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testNewListEntry() {
        // Setup
        final List<T> listValue = Arrays.asList();
        final TracingMessageBuilder expectedResult = new TracingMessageBuilder("title");

        // Run the test
        final TracingMessageBuilder result = tracingMessageBuilderUnderTest.newListEntry("key", listValue);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testNewMapEntry() {
        // Setup
        final Map<String, T> mapValue = new HashMap<>();
        final TracingMessageBuilder expectedResult = new TracingMessageBuilder("title");

        // Run the test
        final TracingMessageBuilder result = tracingMessageBuilderUnderTest.newMapEntry("key", mapValue);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testNewMapArrayEntry() {
        // Setup
        final Map<String, T[]> mapValue = new HashMap<>();
        final TracingMessageBuilder expectedResult = new TracingMessageBuilder("title");

        // Run the test
        final TracingMessageBuilder result = tracingMessageBuilderUnderTest.newMapArrayEntry("key", mapValue);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToString() {
        // Setup
        final String expectedResult = "result";

        // Run the test
        final String result = tracingMessageBuilderUnderTest.toString();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testAppending() {
        // Setup
        final Throwable t = new Throwable("message");
        final Consumer<TracingMessageBuilder> expectedResult = null;

        // Run the test
        final Consumer<TracingMessageBuilder> result = TracingMessageBuilder.appending(t);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

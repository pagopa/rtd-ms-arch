package eu.sia.meda.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ErrorContextTest {

    private ErrorContext errorContextUnderTest;

    @BeforeEach
    void setUp() {
        errorContextUnderTest = new ErrorContext("projectId");
    }

    @Test
    void testGetErrorKeys() {
        // Run the test
        final Set<String> result = errorContextUnderTest.getErrorKeys();

        // Verify the results
        assertEquals(0, result.size());
    }

    @Test
    void testAddErrorKey() {
         // Run the test
        errorContextUnderTest.addErrorKey("errorKey");

        // Verify the results
        final Set<String> result = errorContextUnderTest.getErrorKeys();

        // Verify the results
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testAddErrorKeys() {
        // Setup
        final Collection<String> errorKeys = Arrays.asList("errorKey1", "errorKey2");

        // Run the test
        errorContextUnderTest.addErrorKeys(errorKeys);

        // Verify the results
        final Set<String> result = errorContextUnderTest.getErrorKeys();

        // Verify the results
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testRemoveErrorKey() {

        // Run the test
        errorContextUnderTest.addErrorKey("errorKey");

        // Verify the results
        final Set<String> result = errorContextUnderTest.getErrorKeys();

        // Verify the results
        assertNotNull(result);
        assertEquals(1, result.size());
        errorContextUnderTest.removeErrorKey("errorKey");
        final Set<String> result2 = errorContextUnderTest.getErrorKeys();
        assertEquals(0, result2.size());
    }

    @Test
    void testRemoveErrorKeys() {
        // Setup
        final Collection<String> errorKeys = Arrays.asList("errorKey1", "errorKey2");
        
        // Verify the results
        errorContextUnderTest.addErrorKeys(errorKeys);
        final Set<String> result = errorContextUnderTest.getErrorKeys();
        assertNotNull(result);
        assertEquals(2, result.size());
        errorContextUnderTest.removeErrorKeys(errorKeys);
        final Set<String> result2 = errorContextUnderTest.getErrorKeys();
        assertEquals(0, result2.size());

    }
}

package eu.sia.meda.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilsTest {

    private ReflectionUtils reflectionUtilsUnderTest;

    @BeforeEach
    void setUp() {
        reflectionUtilsUnderTest = new ReflectionUtils();
    }

    @Test
    void testSetFieldValue() throws Exception {
        // Setup
        final Field field = null;

        // Run the test
        final boolean result = reflectionUtilsUnderTest.setFieldValue("object", field, "value");

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testSetFieldValue_ThrowsIllegalAccessException() {
        // Setup
        final Field field = null;

        // Run the test
        assertThrows(IllegalAccessException.class, () -> {
            reflectionUtilsUnderTest.setFieldValue("object", field, "value");
        });
    }

    @Test
    void testGetField() {
        // Setup
        final Field expectedResult = null;

        // Run the test
        final Field result = reflectionUtilsUnderTest.getField("name", Object.class);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testNewInstance() {
        // Setup
        final Object expectedResult = "result";

        // Run the test
        final Object result = reflectionUtilsUnderTest.newInstance(Object.class);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testIsInstanceOf() {
        // Setup

        // Run the test
        final boolean result = reflectionUtilsUnderTest.isInstanceOf(Object.class, Object.class);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testGetGenericTypeClass() {
        assertEquals("result", ReflectionUtils.getGenericTypeClass(Object.class, 0));
    }
}

package eu.sia.meda.core.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.ConfigurableEnvironment;

class PropertiesManagerTest {

    @Mock
    private ConfigurableEnvironment mockEnvironment;

    @InjectMocks
    private PropertiesManager propertiesManagerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGet() {
        // Setup
        final String expectedResult = "value";
        when(mockEnvironment.getProperty("key", String.class, "defaultValue")).thenReturn("value");

        // Run the test
        final String result = propertiesManagerUnderTest.get("key", "defaultValue");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGet1() {
        // Setup
        final String expectedResult = "value";
        when(mockEnvironment.getProperty("key", String.class)).thenReturn("value");

        // Run the test
        final String result = propertiesManagerUnderTest.get("key");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGet2() {
        // Setup
        final Integer expectedResult = 0;
        when(mockEnvironment.getProperty("key", Integer.class, 0)).thenReturn(0);

        // Run the test
        final Integer result = propertiesManagerUnderTest.get("key", 0);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGet3() {
        // Setup
        final Long expectedResult = 0L;
        when(mockEnvironment.getProperty("key", Long.class, 0L)).thenReturn(0L);

        // Run the test
        final Long result = propertiesManagerUnderTest.get("key", 0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGet4() {
        // Setup
        final Float expectedResult = 0.0f;
        when(mockEnvironment.getProperty("key", Float.class, 0.0f)).thenReturn(0.0f);

        // Run the test
        final Float result = propertiesManagerUnderTest.get("key", 0.0f);

        // Verify the results
        assertEquals(expectedResult, result, 0.0001);
    }

    @Test
    void testGet5() {
        // Setup
        when(mockEnvironment.getProperty("key", Boolean.class, false)).thenReturn(false);

        // Run the test
        final Boolean result = propertiesManagerUnderTest.get("key", false);

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testGet6() {
        // Setup
    	when(propertiesManagerUnderTest.get("key", String.class, "defaultValue")).thenReturn("defaultValue");
    	
        // Run the test
        final String result = propertiesManagerUnderTest.get("key", String.class, "defaultValue");

        // Verify the results
        assertEquals("defaultValue", result);
    }

    @Test
    void testGet7() {
        // Setup
        when(mockEnvironment.getProperty("key", String.class)).thenReturn("testGet7");

        // Run the test
        final String result = propertiesManagerUnderTest.get("key", String.class);

        // Verify the results
        assertEquals("testGet7", result);
    }

    @Test
    void testContainsProperty() {
        // Setup
        when(mockEnvironment.containsProperty("key")).thenReturn(true);

        // Run the test
        final boolean result = propertiesManagerUnderTest.containsProperty("key");

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testGetConnectorProperty() {
        // Setup
        when(mockEnvironment.containsProperty("connectorType.items.className.propertyName")).thenReturn(true);
        when(mockEnvironment.getProperty("connectorType.items.className.propertyName", String.class)).thenReturn("testGetConnectorProperty");

        // Run the test
        final String result = propertiesManagerUnderTest.getConnectorProperty("connectorType", "className", "propertyName", String.class);

        // Verify the results
        assertEquals("testGetConnectorProperty", result);
    }

    @Test
    void testGetConnectorProperty1() {
        // Setup
        when(mockEnvironment.containsProperty("connectorType.default.className.propertyName")).thenReturn(false);
        when(mockEnvironment.getProperty("connectorType.default.propertyName", String.class, "defaultValue")).thenReturn("defaultValue");

        // Run the test
        final String result = propertiesManagerUnderTest.getConnectorProperty("connectorType", "className", "propertyName", String.class, "defaultValue");

        // Verify the results
        assertEquals("defaultValue", result);
    }

    @Test
    void testContainsConnectorProperty() {
        // Setup
        when(mockEnvironment.containsProperty("connectorType.items.className.propertyName")).thenReturn(true);

        // Run the test
        final boolean result = propertiesManagerUnderTest.containsConnectorProperty("connectorType", "className", "propertyName");

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testGetConnectorPropertyList() {
        // Setup

        // Run the test
        final List<String> result = propertiesManagerUnderTest.getConnectorPropertyList("connectorType", "className", "key", String.class);

        // Verify the results
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetAsList() {
        // Setup
        when(mockEnvironment.containsProperty("key")).thenReturn(false);
  
        // Run the test
        final List<String> result = propertiesManagerUnderTest.getAsList("key", String.class);

        // Verify the results
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}

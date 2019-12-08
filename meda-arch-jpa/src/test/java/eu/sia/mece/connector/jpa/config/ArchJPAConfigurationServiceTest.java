package eu.sia.meda.connector.jpa.config;

import eu.sia.meda.core.properties.PropertiesManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ArchJPAConfigurationServiceTest {

    @Mock
    private PropertiesManager mockPropertiesManager;

    @InjectMocks
    private ArchJPAConfigurationService archJPAConfigurationServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testRetrieveJPAConnection() {
        // Setup
        final ArchJPAConfigurationService.JPAConnection expectedResult = new ArchJPAConfigurationService.JPAConnection();
        when(mockPropertiesManager.containsProperty("key")).thenReturn(false);
        when(mockPropertiesManager.get("key", String.class)).thenReturn("value");
        when(mockPropertiesManager.get("key", Boolean.class, false)).thenReturn(false);
        when(mockPropertiesManager.getAsList("key", String.class)).thenReturn(Arrays.asList());

        // Run the test
        final ArchJPAConfigurationService.JPAConnection result = archJPAConfigurationServiceUnderTest.retrieveJPAConnection("className");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

package eu.sia.meda.rest.configuration;

import eu.sia.meda.core.properties.PropertiesManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ArchRestConfigurationServiceTest {

    @Mock
    private PropertiesManager mockPropertiesManager;

    @InjectMocks
    private ArchRestConfigurationService archRestConfigurationServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testRetrieveRestConfiguration() {
        // Setup
		when(mockPropertiesManager.containsConnectorProperty(ArchRestConfigurationService.CONNECTOR_TYPE, "className", "url")).thenReturn(true);
        when(mockPropertiesManager.getConnectorProperty(ArchRestConfigurationService.CONNECTOR_TYPE, "className", "url", String.class)).thenReturn("urlValue");
        when(mockPropertiesManager.getConnectorProperty(ArchRestConfigurationService.CONNECTOR_TYPE, "className", "propertyName", Boolean.class, false)).thenReturn(false);
        when(mockPropertiesManager.getConnectorPropertyList(ArchRestConfigurationService.CONNECTOR_TYPE, "className", "propertyName", String.class)).thenReturn(Arrays.asList());

        // Run the test
        final ArchRestConfigurationService.RestConfiguration result = archRestConfigurationServiceUnderTest.retrieveRestConfiguration("className");

        // Verify the results
        assertEquals("urlValue", result.getUrl());
    }
}

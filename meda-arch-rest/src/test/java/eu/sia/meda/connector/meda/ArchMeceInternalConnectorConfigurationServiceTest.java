package eu.sia.meda.connector.meda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import eu.sia.meda.core.properties.PropertiesManager;

class ArchMedaInternalConnectorConfigurationServiceTest {

    @Mock
    private PropertiesManager mockPropertiesManager;

    @InjectMocks
    private ArchMedaInternalConnectorConfigurationService archMedaInternalConnectorConfigurationServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testRetrieveRestConfiguration() {
        // Setup
        when(mockPropertiesManager.containsConnectorProperty(ArchMedaInternalConnectorConfigurationService.CONNECTOR_TYPE, "className", "url")).thenReturn(true);
        when(mockPropertiesManager.getConnectorProperty(ArchMedaInternalConnectorConfigurationService.CONNECTOR_TYPE, "className", "url", String.class)).thenReturn("http://url.test/values");
        when(mockPropertiesManager.getConnectorProperty(ArchMedaInternalConnectorConfigurationService.CONNECTOR_TYPE, "className", "auditEnabled", Boolean.class, false)).thenReturn(false);

        // Run the test
        final ArchMedaInternalConnectorConfigurationService.MedaInternalConfiguration result = archMedaInternalConnectorConfigurationServiceUnderTest.retrieveRestConfiguration("className");

		// Verify the results
		assertNotNull(result);
		assertEquals("http://url.test/values", result.getUrl());
    }
    
}

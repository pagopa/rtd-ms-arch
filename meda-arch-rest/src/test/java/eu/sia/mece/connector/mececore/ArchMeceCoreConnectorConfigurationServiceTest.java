package eu.sia.meda.connector.medacore;

import eu.sia.meda.core.properties.PropertiesManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ArchMedaCoreConnectorConfigurationServiceTest {

    @Mock
    private PropertiesManager mockPropertiesManager;

    @InjectMocks
    private ArchMedaCoreConnectorConfigurationService archMedaCoreConnectorConfigurationServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testRetrieveRestConfiguration() {
        // Setup
        when(mockPropertiesManager.containsConnectorProperty(ArchMedaCoreConnectorConfigurationService.CONNECTOR_TYPE, "className", "url")).thenReturn(true);
        when(mockPropertiesManager.getConnectorProperty(ArchMedaCoreConnectorConfigurationService.CONNECTOR_TYPE, "className", "url", String.class)).thenReturn("http://url.test/values");
        when(mockPropertiesManager.getConnectorPropertyList(ArchMedaCoreConnectorConfigurationService.CONNECTOR_TYPE, "className", "propertyName", String.class)).thenReturn(Arrays.asList());

        // Run the test
        final ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration result = archMedaCoreConnectorConfigurationServiceUnderTest.retrieveRestConfiguration("className");

        // Verify the results
		// Verify the results
		assertNotNull(result);
		assertEquals("http://url.test/values", result.getUrl());
    }
}

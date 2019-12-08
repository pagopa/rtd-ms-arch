package eu.sia.meda.connector.meda;

import eu.sia.meda.rest.configuration.ArchRestConfigurationService;
import org.junit.jupiter.api.BeforeEach;

class MedaInternalRestConnectorTest {

    private MedaInternalRestConnector<INPUT, OUTPUT, DTO, RESOURCE> medaInternalRestConnectorUnderTest;

    @BeforeEach
    void setUp() {
        medaInternalRestConnectorUnderTest = new MedaInternalRestConnector<>("connectorName", new ArchRestConfigurationService.RestConfiguration());
    }
}

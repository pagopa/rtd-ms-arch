package eu.sia.meda.connector.medacore;

import eu.sia.meda.rest.configuration.ArchRestConfigurationService;
import org.junit.jupiter.api.BeforeEach;

class MedaCoreRestConnectorTest {

    private MedaCoreRestConnector<INPUT, OUTPUT, DTO, RESOURCE> medaCoreRestConnectorUnderTest;

    @BeforeEach
    void setUp() {
        medaCoreRestConnectorUnderTest = new MedaCoreRestConnector<>("connectorName", new ArchRestConfigurationService.RestConfiguration());
    }
}

package eu.sia.meda.connector.medacore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtTokenProvisionerTest {

    private JwtTokenProvisioner jwtTokenProvisionerUnderTest;

    @BeforeEach
    void setUp() {
        jwtTokenProvisionerUnderTest = new JwtTokenProvisioner();
    }

    @Test
    void testManipulateToken() {
        // Setup
        final ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration configuration = new ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration();
        final String expectedResult = "result";

        // Run the test
        final String result = jwtTokenProvisionerUnderTest.manipulateToken(configuration);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDoRenewal() {
        // Setup
        final ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration configuration = new ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration();

        // Run the test
        jwtTokenProvisionerUnderTest.doRenewal(configuration);

        // Verify the results
    }
}

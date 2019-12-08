package eu.sia.meda.audit.connector.rest;

import org.junit.jupiter.api.BeforeEach;

class AuditRestConnectorTest {

    private AuditRestConnector auditRestConnectorUnderTest;

    @BeforeEach
    void setUp() {
        auditRestConnectorUnderTest = new AuditRestConnector();
    }
}

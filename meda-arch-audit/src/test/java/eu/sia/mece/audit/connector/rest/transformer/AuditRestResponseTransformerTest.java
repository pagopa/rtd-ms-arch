package eu.sia.meda.audit.connector.rest.transformer;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;

class AuditRestResponseTransformerTest {

    private AuditRestResponseTransformer auditRestResponseTransformerUnderTest;

    @BeforeEach
    void setUp() {
        auditRestResponseTransformerUnderTest = new AuditRestResponseTransformer();
    }

    @Test
    void testTransform() {
        // Setup
        final RestConnectorResponse<Void> restConnectorResponse = new RestConnectorResponse<>();

        // Run the test
        final Void result = auditRestResponseTransformerUnderTest.transform(restConnectorResponse);

        // Verify the results
        assertNull(result);
    }
}

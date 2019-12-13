package eu.sia.meda.tracing.config.condition;

import org.junit.jupiter.api.BeforeEach;

class AuditMockedConditionTest {

    private AuditMockedCondition auditMockedConditionUnderTest;

    @BeforeEach
    void setUp() {
        auditMockedConditionUnderTest = new AuditMockedCondition();
    }
}

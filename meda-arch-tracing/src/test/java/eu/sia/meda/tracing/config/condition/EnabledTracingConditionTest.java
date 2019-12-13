package eu.sia.meda.tracing.config.condition;

import org.junit.jupiter.api.BeforeEach;

class EnabledTracingConditionTest {

    private EnabledTracingCondition enabledTracingConditionUnderTest;

    @BeforeEach
    void setUp() {
        enabledTracingConditionUnderTest = new EnabledTracingCondition();
    }
}

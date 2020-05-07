package eu.sia.meda.async;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MdcTaskDecoratorTest {

    private MdcTaskDecorator mdcTaskDecoratorUnderTest;

    @BeforeEach
    void setUp() {
        mdcTaskDecoratorUnderTest = new MdcTaskDecorator();
    }

    @Test
    void testDecorate() {
        // Run the test
        final Runnable result = mdcTaskDecoratorUnderTest.decorate(new Thread("task"));

        // Verify the results
        assertNotNull(result);
    }
}

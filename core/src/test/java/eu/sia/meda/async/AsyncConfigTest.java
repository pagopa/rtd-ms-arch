package eu.sia.meda.async;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

class AsyncConfigTest {

    private AsyncConfig asyncConfigUnderTest;

    @BeforeEach
    void setUp() {
        asyncConfigUnderTest = new AsyncConfig();
    }

    @Test
    void testGetAsyncExecutor() {

        // Run the test
        final Executor result = asyncConfigUnderTest.getAsyncExecutor();

        // Verify the results
        assertEquals(SimpleAsyncTaskExecutor.class, result.getClass());
    }
}

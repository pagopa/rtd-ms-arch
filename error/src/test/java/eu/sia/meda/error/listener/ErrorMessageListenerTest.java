package eu.sia.meda.error.listener;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.MockitoAnnotations.initMocks;

import org.apache.kafka.common.header.Headers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ErrorMessageListenerTest {

    @InjectMocks
    private ErrorMessageListener errorMessageListenerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testOnReceived() {
        // Setup
        final Headers headers = null;

        // Run the test
        try {
        	errorMessageListenerUnderTest.onReceived("content".getBytes(), headers);
        	assertTrue(Boolean.TRUE);
        } catch (Exception e) {
        	fail();
        }
    }
}

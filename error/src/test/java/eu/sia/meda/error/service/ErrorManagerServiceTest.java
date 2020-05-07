package eu.sia.meda.error.service;

import eu.sia.meda.exceptions.model.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Map;
import java.util.Set;

class ErrorManagerServiceTest {

    private ErrorManagerService errorManagerServiceUnderTest;

    @BeforeEach
    void setUp() {
        errorManagerServiceUnderTest = new ErrorManagerService() {
            @Override
            public Map<String, List<ErrorMessage>> getErrorMessages(Set<String> errorKeys, String defaultSeverity) {
                return null;
            }
        };
    }
}

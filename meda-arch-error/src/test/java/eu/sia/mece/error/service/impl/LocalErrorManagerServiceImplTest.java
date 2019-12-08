package eu.sia.meda.error.service.impl;

import eu.sia.meda.error.config.LocalErrorConfig;
import eu.sia.meda.exceptions.model.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class LocalErrorManagerServiceImplTest {

    @Mock
    private LocalErrorConfig mockLocalErrorConfig;

    @InjectMocks
    private LocalErrorManagerServiceImpl localErrorManagerServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetErrorMessages() {
        // Setup
        final Set<String> errorKeys = new HashSet<>();
        final Map<String, List<ErrorMessage>> expectedResult = new HashMap<>();
        when(mockLocalErrorConfig.getMessages()).thenReturn(new HashMap<>());

        // Run the test
        final Map<String, List<ErrorMessage>> result = localErrorManagerServiceImplUnderTest.getErrorMessages(errorKeys, "defaultSeverity");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

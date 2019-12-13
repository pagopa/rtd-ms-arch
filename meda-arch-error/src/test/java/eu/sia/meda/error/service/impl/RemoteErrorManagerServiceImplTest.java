package eu.sia.meda.error.service.impl;

import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.error.cache.MedaRemoteErrorCache;
import eu.sia.meda.error.config.LocalErrorConfig;
import eu.sia.meda.error.connector.GetErrorMessagesRestConnector;
import eu.sia.meda.error.connector.transformer.GetErrorMessagesRestRequestTransformer;
import eu.sia.meda.error.connector.transformer.GetErrorMessagesRestResponseTransformer;
import eu.sia.meda.exceptions.model.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class RemoteErrorManagerServiceImplTest {

    @Mock
    private LocalErrorConfig mockLocalErrorConfig;
    @Mock
    private MedaRemoteErrorCache mockMedaRemoteErrorCache;
    @Mock
    private GetErrorMessagesRestConnector mockGetErrorMessagesRestConnector;
    
    @Mock
    private GetErrorMessagesRestRequestTransformer mockGetErrorMessagesRestRequestTransformer;
    @Mock
    private GetErrorMessagesRestResponseTransformer mockGetErrorMessagesRestResponseTransformer;

    @InjectMocks
    private RemoteErrorManagerServiceImpl remoteErrorManagerServiceImplUnderTest;

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
        when(mockMedaRemoteErrorCache.getAll(new HashSet<>())).thenReturn(new HashMap<>());
//        when(mockGetErrorMessagesRestConnector.call(eq(new HashSet<>()), mockGetErrorMessagesRestRequestTransformer, any(GetErrorMessagesRestResponseTransformer.class), any(Object.class))).thenReturn(new HashMap<>());

        // Run the test
        final Map<String, List<ErrorMessage>> result = remoteErrorManagerServiceImplUnderTest.getErrorMessages(errorKeys, "defaultSeverity");

        // Verify the results
        assertEquals(expectedResult, result);
        //verify(mockMedaRemoteErrorCache).putAll(new HashMap<>());
    }
}

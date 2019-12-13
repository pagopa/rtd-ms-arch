package eu.sia.meda.error.handler;

import eu.sia.meda.exceptions.config.ErrorHandlingConfiguration;
import eu.sia.meda.exceptions.config.ErrorMappingConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

class OldMedaExceptionManagerTest {

    @Mock
    private ErrorHandlingConfiguration mockErrorHandlingConfigurator;
    @Mock
    private ErrorMappingConfiguration mockErrorMappingConfigurator;

    @InjectMocks
    private OldMedaExceptionManager oldMedaExceptionManagerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }
}

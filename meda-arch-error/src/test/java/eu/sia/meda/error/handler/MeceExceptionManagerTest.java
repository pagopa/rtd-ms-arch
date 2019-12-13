package eu.sia.meda.error.handler;

import eu.sia.meda.error.service.ErrorManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

class MedaExceptionManagerTest {

    @Mock
    private ErrorManagerService mockErrorManagerService;

    @InjectMocks
    private MedaExceptionManager medaExceptionManagerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }
}

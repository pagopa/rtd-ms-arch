package eu.sia.meda.connector.meda;

import eu.sia.meda.connector.rest.transformer.IRestRequestTransformer;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import static org.mockito.MockitoAnnotations.initMocks;

class MedaInternalConnectorTest {

    @Mock
    private ArchMedaInternalConnectorConfigurationService mockConfiguration;
    @Mock
    private AutowireCapableBeanFactory mockBeanFactory;

    @InjectMocks
    private MedaInternalConnector<INPUT, OUTPUT, DTO, RESOURCE> medaInternalConnectorUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testCall() {
        // Setup
        final INPUT input = null;
        final IRestRequestTransformer<INPUT, DTO> requestTransformer = null;
        final IRestResponseTransformer<RESOURCE, OUTPUT> responseTransformer = null;
        final OUTPUT expectedResult = null;

        // Run the test
        final OUTPUT result = medaInternalConnectorUnderTest.call(input, requestTransformer, responseTransformer, "args");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

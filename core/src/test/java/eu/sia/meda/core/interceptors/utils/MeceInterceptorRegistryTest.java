package eu.sia.meda.core.interceptors.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.servlet.HandlerInterceptor;

import eu.sia.meda.core.interceptors.DefaultControllerInterceptor;
import eu.sia.meda.core.interceptors.PopulateMDCInterceptor;

class MedaInterceptorRegistryTest {

    @Mock
    private PopulateMDCInterceptor mockPopulateMDCInterceptor;
    @Mock
    private DefaultControllerInterceptor mockDefaultControllerInterceptor;

    private MedaInterceptorRegistry medaInterceptorRegistryUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        medaInterceptorRegistryUnderTest = new MedaInterceptorRegistry(mockPopulateMDCInterceptor, mockDefaultControllerInterceptor);
    }
    
	@Test
	void testGetInterceptors() {
		List<HandlerInterceptor> interceptors = medaInterceptorRegistryUnderTest.getInterceptors();
		assertNotNull(interceptors);
		assertEquals(interceptors.size(), 2);
	}
    
}

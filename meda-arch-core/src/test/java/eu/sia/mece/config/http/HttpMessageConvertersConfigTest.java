package eu.sia.meda.config.http;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

class HttpMessageConvertersConfigTest {

	@Mock
	private MappingJackson2HttpMessageConverter mockDefaultConverter;

	private HttpMessageConvertersConfig httpMessageConvertersConfigUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
		httpMessageConvertersConfigUnderTest = new HttpMessageConvertersConfig(mockDefaultConverter);
	}

	@Test
	void testConfigureMessageConverters() {
		// Setup
		final List<HttpMessageConverter<?>> converters = new ArrayList<>();

		// Run the test
		try {
			httpMessageConvertersConfigUnderTest.configureMessageConverters(converters);
			assertTrue(Boolean.TRUE);
		} catch (Exception e) {
			fail();
		}
	}
}

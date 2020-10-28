package eu.sia.meda.config.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

class HttpMessageConvertersConfigTest {

	@Mock
	private MappingJackson2HttpMessageConverter mockDefaultConverter;

	private HttpMessageConvertersConfig httpMessageConvertersConfigUnderTest;

	@Mock
	private CustomConvertersConfigurer mockCustomConvertersConfigurer;

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

	@Test
	public void testConfigureMessageConvertersWithCustom(){
		final List<HttpMessageConverter<?>> converters = new ArrayList<>();

		HttpMessageConverter mockConverter = Mockito.mock(HttpMessageConverter.class);

		Mockito.when(mockCustomConvertersConfigurer.customConverters()).thenReturn(Collections.singletonList(mockConverter));

		httpMessageConvertersConfigUnderTest.setCustomConvertersConfigurer(mockCustomConvertersConfigurer);

		// Run the test
		try {
			httpMessageConvertersConfigUnderTest.configureMessageConverters(converters);
			assertEquals(2,converters.size());
		} catch (Exception e) {
			fail();
		}
	}
}

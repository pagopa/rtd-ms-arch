package eu.sia.meda.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.BaseContext;

class ArchConfigurationTest {

    private ArchConfiguration archConfigurationUnderTest;

    @BeforeEach
    void setUp() {
        archConfigurationUnderTest = new ArchConfiguration();
    }

    @Test
    void testObjectMapper() {
        // Run the test
        final ObjectMapper result = archConfigurationUnderTest.objectMapper();

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testMappingJackson2HttpMessageConverter() {
        // Setup
        final ObjectMapper objectMapper = new ObjectMapper();
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
 
        // Run the test
        final MappingJackson2HttpMessageConverter result = archConfigurationUnderTest.mappingJackson2HttpMessageConverter(objectMapper, builder);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testApplicationContext() {
        // Run the test
        final ApplicationContext result = archConfigurationUnderTest.applicationContext();

        // Verify the results
        assertNotNull(result);    }

    @Test
    void testSessionContext() {
        // Run the test
        final BaseContext result = archConfigurationUnderTest.sessionContext();

        // Verify the results
        assertNotNull(result);
    }
}

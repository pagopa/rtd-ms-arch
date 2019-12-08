package eu.sia.meda.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class JsonUtilsTest {

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private JsonUtils jsonUtilsUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testToJson() throws Exception {
        // Setup
        final String expectedResult = "result";
        when(mockObjectMapper.writeValueAsString(any(Object.class))).thenReturn("result");

        // Run the test
        final String result = jsonUtilsUnderTest.toJson("object");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToJson_ObjectMapperThrowsJsonProcessingException() throws Exception {
        // Setup
        final String expectedResult = "result";
        when(mockObjectMapper.writeValueAsString(any(Object.class))).thenThrow(JsonProcessingException.class);

        // Run the test
        final String result = jsonUtilsUnderTest.toJson("object");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToObject() {
        // Setup
        final T expectedResult = null;
        when(mockObjectMapper.readValue(any(byte[].class), eq(T.class))).thenReturn(null);

        // Run the test
        final T result = jsonUtilsUnderTest.toObject("jsonInString", Object.class);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToObject_ObjectMapperThrowsIOException() {
        // Setup
        final T expectedResult = null;
        when(mockObjectMapper.readValue(any(byte[].class), eq(T.class))).thenThrow(IOException.class);

        // Run the test
        final T result = jsonUtilsUnderTest.toObject("jsonInString", Object.class);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToObject_ObjectMapperThrowsJsonParseException() {
        // Setup
        final T expectedResult = null;
        when(mockObjectMapper.readValue(any(byte[].class), eq(T.class))).thenThrow(JsonParseException.class);

        // Run the test
        final T result = jsonUtilsUnderTest.toObject("jsonInString", Object.class);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToObject_ObjectMapperThrowsJsonMappingException() {
        // Setup
        final T expectedResult = null;
        when(mockObjectMapper.readValue(any(byte[].class), eq(T.class))).thenThrow(JsonMappingException.class);

        // Run the test
        final T result = jsonUtilsUnderTest.toObject("jsonInString", Object.class);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

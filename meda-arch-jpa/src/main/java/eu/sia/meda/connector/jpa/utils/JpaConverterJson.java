package eu.sia.meda.connector.jpa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.config.ArchConfiguration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

/**
 * This class helps to convert some object to Json string
 */
@Converter(autoApply = false)
public class JpaConverterJson implements AttributeConverter<Object, String> {

    private ObjectMapper objectMapper = new ArchConfiguration().objectMapper();

    @Override
    public String convertToDatabaseColumn(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public Object convertToEntityAttribute(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Object.class);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}

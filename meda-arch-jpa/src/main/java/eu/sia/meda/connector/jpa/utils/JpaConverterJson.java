package eu.sia.meda.connector.jpa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.config.ArchConfiguration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class helps to convert some object to Json string
 */
@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<Object, String> {

    private ObjectMapper objectMapper = new ArchConfiguration().objectMapper();

    private static Pattern pattern = Pattern.compile("^\\[([^]]*)]");

    @Override
    public String convertToDatabaseColumn(Object object) {
        try {
            return object == null ? null : String.format("[%s]%s", object.getClass().getName(), objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public Object convertToEntityAttribute(String jsonString) {
        try {
            if(jsonString != null && !jsonString.equals("")){
                Matcher matcher = pattern.matcher(jsonString);
                if(matcher.find()){
                    String className = matcher.group(1);
                    return objectMapper.readValue(matcher.replaceAll(""), Class.forName(className));
                }
            }
            return jsonString;
        } catch (IOException | ClassNotFoundException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}

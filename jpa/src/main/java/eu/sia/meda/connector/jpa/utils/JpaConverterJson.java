package eu.sia.meda.connector.jpa.utils;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.config.ArchConfiguration;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class helps to convert some object to Json string
 */
@Converter(autoApply = false)
public class JpaConverterJson implements AttributeConverter<Object, String> {

    private ObjectMapper objectMapper = new ArchConfiguration().objectMapper();

    private static Pattern pattern = Pattern.compile("^\\(([^)]*)\\)");

    private static Pattern classNamePattern = Pattern.compile("\\{\"className\":\"([^,]*)\",");
    private static Pattern collectionNamePattern = Pattern.compile("\"collectionName\":\"([^,]*)\",");
    private static Pattern objectPattern = Pattern.compile("\"object\":(.*)\\}");

    @Override
    public String convertToDatabaseColumn(Object object) {
        if(object==null){
            return null;
        }
        /*   String type=object.getClass().getName();
            String value;
            if(object instanceof Collection){
                Collection<?> collection = (Collection<?>)object;
                if(collection.size()==0){
                    return null;
                } else {
                    type= determineTypeToStore(collection.iterator().next().getClass()).getName();
                    String collectionType = collection instanceof List? ArrayList.class.getName() : collection instanceof Set? HashSet.class.getName() : collection.getClass().getName();
                    value= String.format("(%s)[%s]", collectionType, collection.stream().map(this::serializeObject).collect(Collectors.joining(",")));
                }
            } else {
                value=serializeObject(object);
            }
            return String.format("(%s)%s", type, value);
        */
        String type=object.getClass().getName();
        String value;
        if(object instanceof Collection){
            Collection<?> collection = (Collection<?>)object;
            if(collection.size()==0){
                return null;
            } else {
                type= determineTypeToStore(collection.iterator().next().getClass()).getName();
                String collectionType = collection instanceof List? ArrayList.class.getName() : collection instanceof Set? HashSet.class.getName() : collection.getClass().getName();
                value= String.format("\"collectionName\":\"%s\",\"object\":[%s]", collectionType, collection.stream().map(this::serializeObject).collect(Collectors.joining(",")));
            }
        } else {
            value= String.format("\"object\":%s",serializeObject(object));
        }
        return String.format("{\"className\":\"%s\",%s}", type, value);
    }

    public Class<?> determineTypeToStore(Class<?> clazz) {
        Class<?> out = findJsonTypeSuperclass(clazz);
        if(out==null){
            out=clazz;
        }
        return out;
    }

    private Class<?> findJsonTypeSuperclass(Class<?> clazz) {
        if(clazz==null){
            return null;
        } else if(clazz.getAnnotation(JsonTypeInfo.class)!=null){
            return clazz;
        } else {
            Class<?> jsonTypeSuperClass = null;
            for (Class<?> i : clazz.getInterfaces()) {
                jsonTypeSuperClass=findJsonTypeSuperclass(i);
                if(jsonTypeSuperClass!=null){
                    break;
                }
            }
            if(jsonTypeSuperClass != null){
                return jsonTypeSuperClass;
            } else {
                return findJsonTypeSuperclass(clazz.getSuperclass());
            }
        }
    }

    public String serializeObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
        public Object convertToEntityAttribute(String jsonString) {
        try {
            if(StringUtils.isNotEmpty(jsonString)){
                if(jsonString.startsWith("(")) {
                    Matcher matcher = pattern.matcher(jsonString);
                    if (matcher.find()) {
                        String className = matcher.group(1);
                        String value = matcher.replaceAll("");
                        if (value.endsWith("]")) {
                            Class<?> collectionClass = HashSet.class;
                            matcher = pattern.matcher(value);
                            if (matcher.find()) {
                                collectionClass = Class.forName(matcher.group(1));
                                value = matcher.replaceAll("");
                            }
                            return objectMapper.readValue(value, objectMapper.getTypeFactory().constructParametricType(collectionClass, Class.forName(className)));
                        } else {
                            return objectMapper.readValue(value, Class.forName(className));
                        }
                    }
                }else{
                    Matcher matcher = classNamePattern.matcher(jsonString);
                    if (matcher.find()) {
                        String className = matcher.group(1);
                        String value = matcher.replaceAll("");
                        if (jsonString.endsWith("]}")) {
                            Class<?> collectionClass = HashSet.class;
                            matcher = collectionNamePattern.matcher(value);
                            if (matcher.find()) {
                                collectionClass = Class.forName(matcher.group(1));
                                value = matcher.replaceAll("");
                            }
                            matcher = objectPattern.matcher(value);
                            if(matcher.find()){
                                value = matcher.group(1);
                            }
                            return objectMapper.readValue(value, objectMapper.getTypeFactory().constructParametricType(collectionClass, Class.forName(className)));
                        } else {
                            matcher = objectPattern.matcher(value);
                            if(matcher.find()){
                                value = matcher.group(1);
                            }
                            return objectMapper.readValue(value, Class.forName(className));
                        }
                    }
                }
            }
            return jsonString;
        } catch (IOException | ClassNotFoundException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}

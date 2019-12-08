package eu.sia.meda.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class JsonUtils.
 */
@Component
public class JsonUtils {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
   
   /** The object mapper. */
   @Autowired
   private ObjectMapper objectMapper;

   /**
    * To json.
    *
    * @param object the object
    * @return the string
    */
   public String toJson(Object object) {
      String s = null;

      try {
         s = this.objectMapper.writeValueAsString(object);
      } catch (IOException var4) {
         logger.error("Can not convert to json: {}", var4);
      }

      return s;
   }

   /**
    * To object.
    *
    * @param <T> the generic type
    * @param jsonInString the json in string
    * @param aClass the a class
    * @return the t
    */
   public <T> T toObject(String jsonInString, Class<T> aClass) {
      T object = null;

      try {
         object = this.objectMapper.readValue(jsonInString.getBytes(), aClass);
      } catch (IOException var5) {
         logger.error("Can not construct object from json {}", var5);
      }

      return object;
   }
}

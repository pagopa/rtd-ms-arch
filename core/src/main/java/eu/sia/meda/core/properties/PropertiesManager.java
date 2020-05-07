package eu.sia.meda.core.properties;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * The Class PropertiesManager.
 */
@Component
public class PropertiesManager {
   
   /** The Constant ITEMS_KEY. */
   private static final String ITEMS_KEY = "items";
   
   /** The Constant DEFAULT_KEY. */
   private static final String DEFAULT_KEY = "default";
   
   /** The environment. */
   @Autowired
   private ConfigurableEnvironment environment;

   /**
    * Gets the.
    *
    * @param key the key
    * @param defaultValue the default value
    * @return the string
    */
   public String get(String key, String defaultValue) {
      return this.environment.getProperty(key, String.class, defaultValue);
   }

   /**
    * Gets the.
    *
    * @param key the key
    * @return the string
    */
   public String get(String key) {
      return this.environment.getProperty(key, String.class);
   }

   /**
    * Gets the.
    *
    * @param key the key
    * @param defaultValue the default value
    * @return the integer
    */
   public Integer get(String key, Integer defaultValue) {
      return this.environment.getProperty(key, Integer.class, defaultValue);
   }

   /**
    * Gets the.
    *
    * @param key the key
    * @param defaultValue the default value
    * @return the long
    */
   public Long get(String key, Long defaultValue) {
      return this.environment.getProperty(key, Long.class, defaultValue);
   }

   /**
    * Gets the.
    *
    * @param key the key
    * @param defaultValue the default value
    * @return the float
    */
   public Float get(String key, Float defaultValue) {
      return this.environment.getProperty(key, Float.class, defaultValue);
   }

   /**
    * Gets the.
    *
    * @param key the key
    * @param defaultValue the default value
    * @return the boolean
    */
   public Boolean get(String key, Boolean defaultValue) {
      return this.environment.getProperty(key, Boolean.class, defaultValue);
   }

   /**
    * Gets the.
    *
    * @param <T> the generic type
    * @param key the key
    * @param targetClass the target class
    * @param defaultValue the default value
    * @return the t
    */
   public <T> T get(String key, Class<T> targetClass, T defaultValue) {
      return this.environment.getProperty(key, targetClass, defaultValue);
   }

   /**
    * Gets the.
    *
    * @param <T> the generic type
    * @param key the key
    * @param targetClass the target class
    * @return the t
    */
   public <T> T get(String key, Class<T> targetClass) {
      return this.environment.getProperty(key, targetClass);
   }

   /**
    * Contains property.
    *
    * @param key the key
    * @return true, if successful
    */
   public boolean containsProperty(String key) {
      return this.environment.containsProperty(key);
   }

   /**
    * Gets the connector property.
    *
    * @param <T> the generic type
    * @param connectorType the connector type
    * @param className the class name
    * @param propertyName the property name
    * @param targetClass the target class
    * @return the connector property
    */
   public <T> T getConnectorProperty(String connectorType, String className, String propertyName, Class<T> targetClass) {
      String key = this.derivePropertyName(connectorType, className, propertyName);
      if (this.environment.containsProperty(key)) {
         return this.environment.getProperty(key, targetClass);
      } else {
         key = this.deriveDefaultPropertyName(connectorType, propertyName);
         return this.environment.getProperty(key, targetClass);
      }
   }

   /**
    * Gets the connector property.
    *
    * @param <T> the generic type
    * @param connectorType the connector type
    * @param className the class name
    * @param propertyName the property name
    * @param targetClass the target class
    * @param defaultValue the default value
    * @return the connector property
    */
   public <T> T getConnectorProperty(String connectorType, String className, String propertyName, Class<T> targetClass, T defaultValue) {
      String key = this.derivePropertyName(connectorType, className, propertyName);
      if (this.environment.containsProperty(key)) {
         return this.environment.getProperty(key, targetClass);
      } else {
         key = this.deriveDefaultPropertyName(connectorType, propertyName);
         return this.environment.getProperty(key, targetClass, defaultValue);
      }
   }

   /**
    * Contains connector property.
    *
    * @param connectorType the connector type
    * @param className the class name
    * @param propertyName the property name
    * @return true, if successful
    */
   public boolean containsConnectorProperty(String connectorType, String className, String propertyName) {
      String key = this.derivePropertyName(connectorType, className, propertyName);
      return this.environment.containsProperty(key);
   }

   /**
    * Gets the connector property list.
    *
    * @param <T> the generic type
    * @param connectorType the connector type
    * @param className the class name
    * @param propertyName the property name
    * @param targetClass the target class
    * @return the connector property list
    */
   public <T> List<T> getConnectorPropertyList(String connectorType, String className, String propertyName, Class<T> targetClass) {
      String key = this.derivePropertyName(connectorType, className, propertyName);
      return this.getAsList(key, targetClass);
   }

   /**
    * Gets the as list.
    *
    * @param <T> the generic type
    * @param key the key
    * @param targetClass the target class
    * @return the as list
    */
   public <T> List<T> getAsList(String key, Class<T> targetClass) {
      List<T> returnList = new ArrayList<>();
      int i = 0;

      for(String indexedKey = key + "[" + i + "]"; this.environment.containsProperty(indexedKey); indexedKey = key + "[" + i + "]") {
         returnList.add(this.environment.getProperty(indexedKey, targetClass));
         ++i;
      }

      return returnList;
   }

   /**
    * Derive property name.
    *
    * @param connectorType the connector type
    * @param className the class name
    * @param propertyName the property name
    * @return the string
    */
   private String derivePropertyName(String connectorType, String className, String propertyName) {
      return String.format("%s.%s.%s.%s", connectorType, ITEMS_KEY, className, propertyName);
   }

   /**
    * Derive default property name.
    *
    * @param connectorType the connector type
    * @param propertyName the property name
    * @return the string
    */
   private String deriveDefaultPropertyName(String connectorType, String propertyName) {
      return String.format("%s.%s.%s", connectorType, DEFAULT_KEY, propertyName);
   }
}

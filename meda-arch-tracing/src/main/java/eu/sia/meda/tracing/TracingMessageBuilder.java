package eu.sia.meda.tracing;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.validation.constraints.NotNull;

/**
 * The Class TracingMessageBuilder.
 */
public class TracingMessageBuilder {
   
   /** The Constant REQUEST_SECTION. */
   public static final String REQUEST_SECTION = "REQUEST";
   
   /** The Constant RESPONSE_SECTION. */
   public static final String RESPONSE_SECTION = "RESPONSE";
   
   /** The Constant TIME_SECTION. */
   public static final String TIME_SECTION = "TIME";
   
   /** The Constant SECTION_SEPARATOR. */
   private static final String SECTION_SEPARATOR = " --- ";
   
   /** The Constant ENTRY_SEPARATOR. */
   private static final String ENTRY_SEPARATOR = " - ";
   
   /** The Constant KEYVALUE_SEPARATOR. */
   private static final String KEYVALUE_SEPARATOR = ": ";
   
   /** The Constant OPEN_TITLE. */
   private static final String OPEN_TITLE = "[";
   
   /** The Constant CLOSE_TITLE. */
   private static final String CLOSE_TITLE = "]";
   
   /** The string builder. */
   private StringBuilder stringBuilder = new StringBuilder();

   /**
    * Instantiates a new tracing message builder.
    *
    * @param title the title
    */
   public TracingMessageBuilder(String title) {
      this.stringBuilder.append(OPEN_TITLE).append(title).append(CLOSE_TITLE);
   }

   /**
    * Appending.
    *
    * @param t the t
    * @return the consumer
    */
   public static Consumer<TracingMessageBuilder> appending(Throwable t) {
      return (appender) -> {
         appender.newSection(RESPONSE_SECTION).newEntry("Exception", t.getMessage());
      };
   }

   /**
    * New section.
    *
    * @param name the name
    * @return the tracing message builder
    */
   public TracingMessageBuilder newSection(String name) {
      this.stringBuilder.append(SECTION_SEPARATOR).append(OPEN_TITLE).append(name).append(CLOSE_TITLE);
      return this;
   }

   /**
    * New entry.
    *
    * @param <T> the generic type
    * @param key the key
    * @param value the value
    * @return the tracing message builder
    */
   public <T> TracingMessageBuilder newEntry(String key, T value) {
      this.stringBuilder.append(ENTRY_SEPARATOR).append(key).append(KEYVALUE_SEPARATOR).append(value);
      return this;
   }

   /**
    * New list entry.
    *
    * @param <T> the generic type
    * @param key the key
    * @param listValue the list value
    * @return the tracing message builder
    */
   public <T> TracingMessageBuilder newListEntry(String key, @NotNull List<T> listValue) {
      String separator = OPEN_TITLE;
      this.stringBuilder.append(ENTRY_SEPARATOR).append(key).append(KEYVALUE_SEPARATOR);
      if (listValue.isEmpty()) {
         this.stringBuilder.append(separator);
      } else {
         for(Iterator var4 = listValue.iterator(); var4.hasNext(); separator = ", ") {
            T value = (T) var4.next();
            this.stringBuilder.append(separator).append(value);
         }
      }

      this.stringBuilder.append(CLOSE_TITLE);
      return this;
   }

   /**
    * New map entry.
    *
    * @param <T> the generic type
    * @param key the key
    * @param mapValue the map value
    * @return the tracing message builder
    */
   public <T> TracingMessageBuilder newMapEntry(String key, @NotNull Map<String, T> mapValue) {
      String separator = "{";
      this.stringBuilder.append(ENTRY_SEPARATOR).append(key).append(KEYVALUE_SEPARATOR);
      if (mapValue.isEmpty()) {
         this.stringBuilder.append(separator);
      } else {
         for(Iterator var4 = mapValue.entrySet().iterator(); var4.hasNext(); separator = ", ") {
            Entry<String, T> entry = (Entry)var4.next();
            this.stringBuilder.append(separator).append((String)entry.getKey()).append("=").append(entry.getValue());
         }
      }

      this.stringBuilder.append("}");
      return this;
   }

   /**
    * New map array entry.
    *
    * @param <T> the generic type
    * @param key the key
    * @param mapValue the map value
    * @return the tracing message builder
    */
   public <T> TracingMessageBuilder newMapArrayEntry(String key, @NotNull Map<String, T[]> mapValue) {
      String separator = "{";
      this.stringBuilder.append(ENTRY_SEPARATOR).append(key).append(KEYVALUE_SEPARATOR);
      if (mapValue.isEmpty()) {
         this.stringBuilder.append(separator);
      } else {
         for(Iterator var4 = mapValue.entrySet().iterator(); var4.hasNext(); separator = ", ") {
            Entry<String, T[]> entry = (Entry)var4.next();
            this.stringBuilder.append(separator).append((String)entry.getKey()).append("=");
            if (entry.getValue() == null) {
               this.stringBuilder.append("null");
            } else {
               String subSeparator = OPEN_TITLE;
               Object[] var7 = (Object[])entry.getValue();
               int var8 = var7.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  T value = (T) var7[var9];
                  this.stringBuilder.append(subSeparator).append(value);
                  subSeparator = ", ";
               }

               this.stringBuilder.append(CLOSE_TITLE);
            }
         }
      }

      this.stringBuilder.append("}");
      return this;
   }

   /**
    * To string.
    *
    * @return the string
    */
   public String toString() {
      return this.stringBuilder.toString();
   }
}

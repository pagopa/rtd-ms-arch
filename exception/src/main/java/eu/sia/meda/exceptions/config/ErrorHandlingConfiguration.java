/*
 * 
 */
package eu.sia.meda.exceptions.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class ErrorHandlingConfiguration.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(
   prefix = "error-handling"
)
public class ErrorHandlingConfiguration {
   
   /** The enable exception logging. */
   private boolean enableExceptionLogging;
   
   /** The enable stacktrace logging. */
   private boolean enableStacktraceLogging;
   
   /** The enable exception event. */
   private boolean enableExceptionEvent;
   
   /** The enable exception decoding. */
   private boolean enableExceptionDecoding;
   
   /** The enable unknow exception mapping. */
   private boolean enableUnknowExceptionMapping;
   
   /** The unknow exception tag. */
   private String unknowExceptionTag;

   /**
    * Checks if is enable exception logging.
    *
    * @return true, if is enable exception logging
    */
   public boolean isEnableExceptionLogging() {
      return this.enableExceptionLogging;
   }

   /**
    * Sets the enable exception logging.
    *
    * @param enableExceptionLogging the new enable exception logging
    */
   public void setEnableExceptionLogging(boolean enableExceptionLogging) {
      this.enableExceptionLogging = enableExceptionLogging;
   }

   /**
    * Checks if is enable stacktrace logging.
    *
    * @return true, if is enable stacktrace logging
    */
   public boolean isEnableStacktraceLogging() {
      return this.enableStacktraceLogging;
   }

   /**
    * Sets the enable stacktrace logging.
    *
    * @param enableStacktraceLogging the new enable stacktrace logging
    */
   public void setEnableStacktraceLogging(boolean enableStacktraceLogging) {
      this.enableStacktraceLogging = enableStacktraceLogging;
   }

   /**
    * Checks if is enable exception event.
    *
    * @return true, if is enable exception event
    */
   public boolean isEnableExceptionEvent() {
      return this.enableExceptionEvent;
   }

   /**
    * Sets the enable exception event.
    *
    * @param enableExceptionEvent the new enable exception event
    */
   public void setEnableExceptionEvent(boolean enableExceptionEvent) {
      this.enableExceptionEvent = enableExceptionEvent;
   }

   /**
    * Checks if is enable exception decoding.
    *
    * @return true, if is enable exception decoding
    */
   public boolean isEnableExceptionDecoding() {
      return this.enableExceptionDecoding;
   }

   /**
    * Sets the enable exception decoding.
    *
    * @param enableExceptionDecoding the new enable exception decoding
    */
   public void setEnableExceptionDecoding(boolean enableExceptionDecoding) {
      this.enableExceptionDecoding = enableExceptionDecoding;
   }

   /**
    * Checks if is enable unknow exception mapping.
    *
    * @return true, if is enable unknow exception mapping
    */
   public boolean isEnableUnknowExceptionMapping() {
      return this.enableUnknowExceptionMapping;
   }

   /**
    * Sets the enable unknow exception mapping.
    *
    * @param enableUnknowExceptionMapping the new enable unknow exception mapping
    */
   public void setEnableUnknowExceptionMapping(boolean enableUnknowExceptionMapping) {
      this.enableUnknowExceptionMapping = enableUnknowExceptionMapping;
   }

   /**
    * Gets the unknow exception tag.
    *
    * @return the unknow exception tag
    */
   public String getUnknowExceptionTag() {
      return this.unknowExceptionTag;
   }

   /**
    * Sets the unknow exception tag.
    *
    * @param unknowExceptionTag the new unknow exception tag
    */
   public void setUnknowExceptionTag(String unknowExceptionTag) {
      this.unknowExceptionTag = unknowExceptionTag;
   }
}

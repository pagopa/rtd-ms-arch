package eu.sia.meda.tracing.controllers.utils;

import java.util.Iterator;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * The Class TraceableResponse.
 */
public class TraceableResponse extends ContentCachingResponseWrapper implements TraceableHttpMessage {
   
   /** The should be traced. */
   private boolean shouldBeTraced = false;
   
   /** The max length. */
   private final int maxLength;

   /**
    * Instantiates a new traceable response.
    *
    * @param response the response
    * @param maxLength the max length
    */
   public TraceableResponse(HttpServletResponse response, int maxLength) {
      super(response);
      this.maxLength = maxLength;
   }

   /**
    * Gets the body.
    *
    * @return the body
    */
   public byte[] getBody() {
      return super.getContentAsByteArray();
   }

   /**
    * Gets the header names iterator.
    *
    * @return the header names iterator
    */
   public Iterator<String> getHeaderNamesIterator() {
      return this.getHeaderNames().iterator();
   }

   /**
    * Gets the headers iterator.
    *
    * @param name the name
    * @return the headers iterator
    */
   public Iterator<String> getHeadersIterator(String name) {
      return this.getHeaders(name).iterator();
   }

   /**
    * Gets the max lenght.
    *
    * @return the max lenght
    */
   public int getMaxLenght() {
      return this.maxLength;
   }

   /**
    * Should be traced.
    *
    * @return true, if successful
    */
   public boolean shouldBeTraced() {
      return this.shouldBeTraced;
   }

   /**
    * Sets the should be traced.
    *
    * @param shouldBeTraced the new should be traced
    */
   public void setShouldBeTraced(boolean shouldBeTraced) {
      this.shouldBeTraced = shouldBeTraced;
   }
}

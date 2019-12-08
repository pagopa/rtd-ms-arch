package eu.sia.meda.tracing.controllers.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.ribbon.support.ResettableServletInputStreamWrapper;
import org.springframework.web.util.ContentCachingRequestWrapper;

/**
 * The Class TraceableRequest.
 */
public class TraceableRequest extends ContentCachingRequestWrapper implements TraceableHttpMessage {
   
   /** The byte array. */
   private final byte[] byteArray;
   
   /** The max length. */
   private final int maxLength;

   /**
    * Instantiates a new traceable request.
    *
    * @param request the request
    * @param maxLength the max length
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public TraceableRequest(HttpServletRequest request, int maxLength) throws IOException {
      super(request);
      this.maxLength = maxLength;
      ServletInputStream inputStream = request.getInputStream();
      byte[] buffer = new byte[1024];
      ByteArrayOutputStream result = new ByteArrayOutputStream();

      int length;
      while((length = inputStream.read(buffer)) != -1) {
         result.write(buffer, 0, length);
      }

      this.byteArray = result.toByteArray();
   }

   /**
    * Gets the body.
    *
    * @return the body
    */
   public byte[] getBody() {
      return this.byteArray;
   }

   /**
    * Gets the input stream.
    *
    * @return the input stream
    */
   public ServletInputStream getInputStream() {
      return new ResettableServletInputStreamWrapper(this.byteArray);
   }

   /**
    * Gets the header names iterator.
    *
    * @return the header names iterator
    */
   public Iterator<String> getHeaderNamesIterator() {
      return new TraceableRequest.EnumerationToIterator<>(this.getHeaderNames());
   }

   /**
    * Gets the headers iterator.
    *
    * @param name the name
    * @return the headers iterator
    */
   public Iterator<String> getHeadersIterator(String name) {
      return new TraceableRequest.EnumerationToIterator<>(this.getHeaders(name));
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
    * The Class EnumerationToIterator.
    *
    * @param <T> the generic type
    */
   private class EnumerationToIterator<T> implements Iterator<T> {
      
      /** The enumeration. */
      private final Enumeration<T> enumeration;

      /**
       * Instantiates a new enumeration to iterator.
       *
       * @param enumeration the enumeration
       */
      protected EnumerationToIterator(Enumeration<T> enumeration) {
         this.enumeration = enumeration;
      }

      /**
       * Checks for next.
       *
       * @return true, if successful
       */
      public boolean hasNext() {
         return this.enumeration.hasMoreElements();
      }

      /**
       * Next.
       *
       * @return the t
       */
      public T next() {
         return this.enumeration.nextElement();
      }
   }
}

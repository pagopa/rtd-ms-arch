package eu.sia.meda.tracing.controllers.utils;

import eu.sia.meda.tracing.TracingMessageBuilder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The Interface TraceableHttpMessage.
 */
public interface TraceableHttpMessage {
   
   /**
    * Gets the body.
    *
    * @return the body
    */
   byte[] getBody();

   /**
    * Gets the header names iterator.
    *
    * @return the header names iterator
    */
   Iterator<String> getHeaderNamesIterator();

   /**
    * Gets the headers iterator.
    *
    * @param name the name
    * @return the headers iterator
    */
   Iterator<String> getHeadersIterator(String name);

   /**
    * Gets the max lenght.
    *
    * @return the max lenght
    */
   int getMaxLenght();

   /**
    * Append http message.
    *
    * @param messageBuilder the message builder
    * @param traceableHttpMessage the traceable http message
    * @param maxLength the max length
    */
   static void appendHttpMessage(TracingMessageBuilder messageBuilder, TraceableHttpMessage traceableHttpMessage, int maxLength) {
      Map<String, List<String>> headers = new HashMap();
      Iterator headerNames = traceableHttpMessage.getHeaderNamesIterator();

      while(headerNames.hasNext()) {
         String headerName = (String)headerNames.next();
         Iterator headerValues = traceableHttpMessage.getHeadersIterator(headerName);

         while(headerValues.hasNext()) {
            String value = (String)headerValues.next();
            List<String> list = (List)headers.computeIfAbsent(headerName, (key) -> {
               return new ArrayList();
            });
            list.add(value);
         }
      }

      messageBuilder.newMapEntry("Headers", headers);
      appendFromByteArray(messageBuilder, traceableHttpMessage.getBody(), maxLength);
   }

   /**
    * Append from byte array.
    *
    * @param messageBuilder the message builder
    * @param bodyBytes the body bytes
    * @param maxLength the max length
    */
   static void appendFromByteArray(TracingMessageBuilder messageBuilder, byte[] bodyBytes, int maxLength) {
      int bodyLength = bodyBytes == null ? 0 : bodyBytes.length;

      String body;
      try {
         if (bodyLength == 0) {
            body = "[Empty]";
         } else {
            body = new String(bodyBytes, 0, bodyLength < maxLength ? bodyLength : maxLength, StandardCharsets.UTF_8);
         }
      } catch (Throwable var6) {
         body = "[Cannot parse body]";
      }

      if (bodyLength > maxLength) {
         messageBuilder.newEntry("Body", body + "[...]");
      } else {
         messageBuilder.newEntry("Body", body);
      }

   }
}

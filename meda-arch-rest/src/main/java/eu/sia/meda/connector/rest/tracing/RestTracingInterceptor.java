package eu.sia.meda.connector.rest.tracing;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.TracingMessageBuilder;
import eu.sia.meda.tracing.config.condition.EnabledTracingCondition;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

/**
 * The Class RestTracingInterceptor.
 */
@Component
@Conditional({EnabledTracingCondition.class})
public class RestTracingInterceptor implements ClientHttpRequestInterceptor {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerUtils.getLogger(RestTracingInterceptor.class);
   
   /** The tracing manager. */
   private final TracingManager tracingManager;

   /**
    * Instantiates a new rest tracing interceptor.
    *
    * @param tracingManager the tracing manager
    */
   @Autowired
   public RestTracingInterceptor(TracingManager tracingManager) {
      this.tracingManager = tracingManager;
   }

   /**
    * Intercept.
    *
    * @param request the request
    * @param body the body
    * @param execution the execution
    * @return the client http response
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();

      ClientHttpResponse response;
      try {
         response = execution.execute(request, body);
      } catch (Throwable var8) {
         Long elapsedMillis = this.getElapsedMillis(stopWatch);
         this.trace(request, body, var8, elapsedMillis);
         throw var8;
      }

      Long elapsedMillis = this.getElapsedMillis(stopWatch);
      this.trace(request, body, response, elapsedMillis);
      return response;
   }

   /**
    * Trace.
    *
    * @param request the request
    * @param body the body
    * @param response the response
    * @param elapsedMillis the elapsed millis
    * @throws IOException Signals that an I/O exception has occurred.
    */
   private void trace(HttpRequest request, byte[] body, ClientHttpResponse response, Long elapsedMillis) throws IOException {
      this.trace(request, body, (appender) -> {
         appender.newSection("RESPONSE");

         try {
            appender.newEntry("Status", Integer.toString(response.getRawStatusCode()));
         } catch (IOException var3) {
            appender.newEntry("Status", "[Cannot read status]");
         }

         appender.newEntry("Headers", response.getHeaders().toString());

         try {
            String bodyString = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            appender.newEntry("Body", bodyString != null && bodyString.length() > 0 ? bodyString : "[Empty]");
         } catch (IOException var4) {
            appender.newEntry("Body", "[Cannot read body]");
         }

      }, elapsedMillis);
   }

   /**
    * Trace.
    *
    * @param request the request
    * @param body the body
    * @param t the t
    * @param elapsedMillis the elapsed millis
    * @throws IOException Signals that an I/O exception has occurred.
    */
   private void trace(HttpRequest request, byte[] body, Throwable t, Long elapsedMillis) throws IOException {
      this.trace(request, body, TracingMessageBuilder.appending(t), elapsedMillis);
   }

   /**
    * Trace.
    *
    * @param request the request
    * @param body the body
    * @param appending the appending
    * @param elapsedMillis the elapsed millis
    * @throws IOException Signals that an I/O exception has occurred.
    */
   private void trace(HttpRequest request, byte[] body, Consumer<TracingMessageBuilder> appending, Long elapsedMillis) throws IOException {
      TracingMessageBuilder tracingMessage = new TracingMessageBuilder("REST CONNECTOR");
      tracingMessage.newSection("REQUEST").newEntry("Method", request.getMethodValue()).newEntry("URI", request.getURI().toString()).newEntry("Headers", request.getHeaders().toString()).newEntry("Body", body != null && body.length > 0 ? new String(body) : "[Empty]");
      appending.accept(tracingMessage);
      if (elapsedMillis != null) {
         tracingMessage.newSection("TIME").newEntry("Elapsed Time", String.format("%dms", elapsedMillis));
      }

      this.tracingManager.trace(tracingMessage);
   }

   /**
    * Gets the elapsed millis.
    *
    * @param stopWatch the stop watch
    * @return the elapsed millis
    */
   private Long getElapsedMillis(StopWatch stopWatch) {
      try {
         if (stopWatch != null) {
            if (stopWatch.isRunning()) {
               stopWatch.stop();
            }

            return stopWatch.getTotalTimeMillis();
         }

         logger.info("stopWatch is null");
      } catch (Exception var3) {
         logger.info("error getting elapsed time: ", var3);
      }

      return null;
   }
}

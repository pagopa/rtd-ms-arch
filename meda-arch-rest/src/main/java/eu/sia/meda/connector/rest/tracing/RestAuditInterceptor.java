package eu.sia.meda.connector.rest.tracing;

import eu.sia.meda.tracing.config.condition.EnabledTracingCondition;
import eu.sia.meda.tracing.impl.AuditGenerator;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * The Class RestAuditInterceptor.
 */
@Component
@Scope("prototype")
@Conditional({EnabledTracingCondition.class})
public class RestAuditInterceptor implements ClientHttpRequestInterceptor {
   
   /** The audit generator. */
   @Autowired
   private AuditGenerator auditGenerator;
   
   /** The cod service. */
   private String codService;

   /**
    * Instantiates a new rest audit interceptor.
    *
    * @param codService the cod service
    */
   public RestAuditInterceptor(String codService) {
      this.codService = codService;
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
      ClientHttpResponse response;
      try {
         response = execution.execute(request, body);
      } catch (Exception var6) {
         this.auditGenerator.traceRest(this.codService, request, body, var6);
         throw var6;
      }

      this.auditGenerator.traceRest(this.codService, request, body, response);
      return response;
   }
}

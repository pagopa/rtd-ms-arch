package eu.sia.meda.tracing.controllers;

import eu.sia.meda.core.interceptors.utils.MedaRequestAttributes;
import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.TracingMessageBuilder;
import eu.sia.meda.tracing.controllers.utils.TraceableHttpMessage;
import eu.sia.meda.tracing.controllers.utils.TraceableRequest;
import eu.sia.meda.tracing.controllers.utils.TraceableResponse;
import java.io.IOException;

/**
 * The Interface TracingControllerUtils.
 */
public interface TracingControllerUtils {
   
   /**
    * Trace request.
    *
    * @param traceableRequest the traceable request
    * @param tracingManager the tracing manager
    */
   static void traceRequest(TraceableRequest traceableRequest, TracingManager tracingManager) {
      TracingMessageBuilder requestTrace = new TracingMessageBuilder("API REQUEST");
      requestTrace.newSection("HTTP").newEntry("RequestId", MedaRequestAttributes.getRequestId(traceableRequest)).newEntry("Method", traceableRequest.getMethod()).newEntry("URI", traceableRequest.getRequestURI()).newMapArrayEntry("Params", traceableRequest.getParameterMap());
      TraceableHttpMessage.appendHttpMessage(requestTrace, traceableRequest, traceableRequest.getMaxLenght());
      tracingManager.trace(requestTrace);
   }

   /**
    * Trace response.
    *
    * @param traceableRequest the traceable request
    * @param traceableResponse the traceable response
    * @param tracingManager the tracing manager
    * @throws IOException Signals that an I/O exception has occurred.
    */
   static void traceResponse(TraceableRequest traceableRequest, TraceableResponse traceableResponse, TracingManager tracingManager) throws IOException {
      TracingMessageBuilder responseTrace = new TracingMessageBuilder("API RESPONSE");
      responseTrace.newSection("HTTP").newEntry("RequestId", MedaRequestAttributes.getRequestId(traceableRequest)).newEntry("Status", Integer.toString(traceableResponse.getStatusCode()));
      TraceableHttpMessage.appendHttpMessage(responseTrace, traceableResponse, traceableResponse.getMaxLenght());
      tracingManager.trace(responseTrace);
   }
}

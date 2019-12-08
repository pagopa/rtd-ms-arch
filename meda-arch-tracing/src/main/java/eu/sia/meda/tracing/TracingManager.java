package eu.sia.meda.tracing;

/**
 * The Interface TracingManager.
 */
public interface TracingManager {
   
   /**
    * Trace.
    *
    * @param message the message
    */
   void trace(TracingMessageBuilder message);
}

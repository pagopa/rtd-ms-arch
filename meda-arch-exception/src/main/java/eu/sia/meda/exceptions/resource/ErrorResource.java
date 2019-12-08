/*
 * 
 */
package eu.sia.meda.exceptions.resource;

import eu.sia.meda.exceptions.model.ErrorMessage;
import java.util.List;
import java.util.Map;
import org.springframework.hateoas.ResourceSupport;

/**
 * The Class ErrorResource.
 */
public class ErrorResource extends ResourceSupport {
   
   /** The return messages. */
   private Map<String, List<ErrorMessage>> returnMessages;

   /**
    * Gets the return messages.
    *
    * @return the return messages
    */
   public Map<String, List<ErrorMessage>> getReturnMessages() {
      return this.returnMessages;
   }

   /**
    * Sets the return messages.
    *
    * @param returnMessages the return messages
    */
   public void setReturnMessages(Map<String, List<ErrorMessage>> returnMessages) {
      this.returnMessages = returnMessages;
   }
}

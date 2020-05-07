package eu.sia.meda.error.connector.transformer;

import eu.sia.meda.connector.rest.model.RestConnectorResponse;
import eu.sia.meda.connector.rest.transformer.IRestResponseTransformer;
import eu.sia.meda.error.condition.RemoteErrorManagerCondition;
import eu.sia.meda.error.resource.ExtendedErrorMessageResource;
import eu.sia.meda.exceptions.model.ErrorMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class GetErrorMessagesRestResponseTransformer.
 */
@Service
@Conditional({RemoteErrorManagerCondition.class})
public class GetErrorMessagesRestResponseTransformer implements IRestResponseTransformer<Map<String, List<ExtendedErrorMessageResource>>, Map<String, List<ErrorMessage>>> {
   
   /**
    * Transform.
    *
    * @param restConnectorResponse the rest connector response
    * @return the map
    */
   public Map<String, List<ErrorMessage>> transform(RestConnectorResponse<Map<String, List<ExtendedErrorMessageResource>>> restConnectorResponse) {
      if (restConnectorResponse.getResponse().getBody() == null) {
         return new HashMap<>();
      } else {
         Map<String, List<ErrorMessage>> errorMap = new HashMap<>();
         Iterator var3 = ((Map)restConnectorResponse.getResponse().getBody()).entrySet().iterator();

         while(var3.hasNext()) {
            Entry<String, List<ExtendedErrorMessageResource>> entry = (Entry)var3.next();
            errorMap.put(entry.getKey(), this.fromResources((Collection)entry.getValue()));
         }

         return errorMap;
      }
   }

   /**
    * From resources.
    *
    * @param resources the resources
    * @return the list
    */
   private List<ErrorMessage> fromResources(Collection<ExtendedErrorMessageResource> resources) {
      return (List)(resources == null ? new ArrayList() : (List)resources.stream().filter((r) -> {
         return r != null;
      }).map((r) -> {
         return this.fromResource(r);
      }).collect(Collectors.toList()));
   }

   /**
    * From resource.
    *
    * @param resource the resource
    * @return the error message
    */
   private ErrorMessage fromResource(ExtendedErrorMessageResource resource) {
      ErrorMessage message = new ErrorMessage();
      message.setRetry(resource.getRetry());
      message.setErrorCode(resource.getErrorCode());
      message.setMessage(resource.getMessage());
      message.setMessageKey(resource.getMessageKey());
      message.setSeverity(resource.getSeverity());
      message.setMessagesForm(resource.getMessagesForm());
      message.setMessageTitle(resource.getMessageTitle());
      message.setLanguage(resource.getLanguage());
      return message;
   }
}

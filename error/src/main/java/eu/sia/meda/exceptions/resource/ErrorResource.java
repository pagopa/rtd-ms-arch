package eu.sia.meda.exceptions.resource;

import eu.sia.meda.exceptions.model.ErrorMessage;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * The Class ErrorResource.
 */
@Data
public class ErrorResource {

    /**
     * The return messages.
     */
    private Map<String, List<ErrorMessage>> returnMessages;


}

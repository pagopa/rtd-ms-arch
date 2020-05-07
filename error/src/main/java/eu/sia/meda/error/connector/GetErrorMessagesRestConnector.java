package eu.sia.meda.error.connector;

import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.error.condition.RemoteErrorManagerCondition;
import eu.sia.meda.error.resource.ExtendedErrorMessageResource;
import eu.sia.meda.exceptions.model.ErrorMessage;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class GetErrorMessagesRestConnector.
 */
@Service
@Conditional({RemoteErrorManagerCondition.class})
public class GetErrorMessagesRestConnector extends BaseRestConnector<Collection<String>, Map<String, List<ErrorMessage>>, Collection<String>, Map<String, List<ExtendedErrorMessageResource>>> {
}

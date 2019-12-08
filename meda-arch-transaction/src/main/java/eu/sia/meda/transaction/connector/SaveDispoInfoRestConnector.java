/*
 * 
 */
package eu.sia.meda.transaction.connector;

import eu.sia.meda.connector.rest.connector.BaseRestConnector;
import eu.sia.meda.transaction.condition.EnabledPutDispoInfoCondition;
import eu.sia.meda.transaction.dto.SaveDispoInfoRequestDto;
import eu.sia.meda.transaction.model.SaveDispoInfoRequest;
import eu.sia.meda.transaction.model.SaveDispoInfoResponse;
import eu.sia.meda.transaction.resource.SaveDispoInfoResponseResource;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class SaveDispoInfoRestConnector.
 */
@Service
@Conditional({EnabledPutDispoInfoCondition.class})
public class SaveDispoInfoRestConnector extends BaseRestConnector<SaveDispoInfoRequest, SaveDispoInfoResponse, SaveDispoInfoRequestDto, SaveDispoInfoResponseResource> {
}

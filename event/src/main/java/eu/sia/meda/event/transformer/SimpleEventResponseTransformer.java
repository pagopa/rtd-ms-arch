package eu.sia.meda.event.transformer;

import eu.sia.meda.event.response.EventResponse;
import org.springframework.stereotype.Service;

@Service
public class SimpleEventResponseTransformer implements IEventResponseTransformer<Void, Boolean> {

    @Override
    public Boolean transform(EventResponse<Void> eventResponse) {
        try {
            eventResponse.getSendResultFuture().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return eventResponse.getResult();
    }

}


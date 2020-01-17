package eu.sia.meda.event.service;

import eu.sia.meda.event.BaseEventConnector;
import eu.sia.meda.event.transformer.ErrorEventRequestTransformer;
import eu.sia.meda.event.transformer.SimpleEventResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseErrorPublisherService implements ErrorPublisherService {
    private final ErrorEventRequestTransformer errorEventRequestTransformer;
    private final SimpleEventResponseTransformer simpleEventResponseTransformer;

    @Autowired
    public BaseErrorPublisherService(ErrorEventRequestTransformer errorEventRequestTransformer, SimpleEventResponseTransformer simpleEventResponseTransformer) {
        this.errorEventRequestTransformer = errorEventRequestTransformer;
        this.simpleEventResponseTransformer = simpleEventResponseTransformer;
    }

    @Override
    public void publishErrorEvent(byte[] message, String errorDesc) {
        getErrorPublisherConnector().call(message, errorEventRequestTransformer, simpleEventResponseTransformer, errorDesc);
    }

    protected abstract BaseEventConnector<byte[], Boolean, byte[], Void> getErrorPublisherConnector();
}

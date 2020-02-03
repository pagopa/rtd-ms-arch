package eu.sia.meda.event.service;

import eu.sia.meda.event.BaseEventConnector;
import eu.sia.meda.event.transformer.ErrorEventRequestTransformer;
import eu.sia.meda.event.transformer.SimpleEventResponseTransformer;
import org.apache.kafka.common.header.Headers;
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
    public boolean publishErrorEvent(byte[] message, Headers headers, String errorDesc) {
        return publishErrorEvent(null, message, headers, errorDesc);
    }

    @Override
    public boolean publishErrorEvent(String errorTopic, byte[] message, Headers headers, String errorDesc) {
        return Boolean.TRUE.equals(getErrorPublisherConnector().call(message, errorEventRequestTransformer, simpleEventResponseTransformer, headers, errorDesc, errorTopic));
    }

    protected abstract BaseEventConnector<byte[], Boolean, byte[], Void> getErrorPublisherConnector();
}

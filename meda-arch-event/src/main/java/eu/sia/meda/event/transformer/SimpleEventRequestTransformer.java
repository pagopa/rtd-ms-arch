package eu.sia.meda.event.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.event.request.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/** Simple {@link IEventRequestTransformer} where INPUT==DTO */
@Service
public class SimpleEventRequestTransformer <INPUT> implements IEventRequestTransformer<INPUT, INPUT> {

    private final ObjectMapper objectMapper;

    @Autowired
    public SimpleEventRequestTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public EventRequest<INPUT> transform(INPUT payload, Object... args) {
        try {
            EventRequest<INPUT> request = new EventRequest<>();
            if(payload instanceof byte[]){
                request.setPayload((byte[])payload);
            }
            else{
                request.setPayload(objectMapper.writeValueAsString(payload).getBytes(StandardCharsets.UTF_8));
            }

            return request;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot serialize payload!", e);
        }
    }
}


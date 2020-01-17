package eu.sia.meda.event.transformer;

import eu.sia.meda.event.request.EventRequest;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ErrorEventRequestTransformer implements IEventRequestTransformer<byte[], byte[]> {

    @Override
    public EventRequest<byte[]> transform(byte[] payload, Object... args) {
        String errorDesc = args.length>0?(String)args[0]:"null";
        Header errorHeader = new RecordHeader("ERROR_DESC", errorDesc.getBytes(StandardCharsets.UTF_8));

        EventRequest<byte[]> request = new EventRequest<>();
        request.setPayload(payload);
        request.setHeaders(new RecordHeaders(new Header[]{errorHeader}));

        return request;
    }
}
package eu.sia.meda.event.transformer;

import eu.sia.meda.event.request.EventRequest;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ErrorEventRequestTransformer implements IEventRequestTransformer<byte[], byte[]> {

    /** <ol>
     * <li>args[0], if not null, will contains the headers</li>
     * <li>args[1], if not null, will contains the error description</li>
     * <li>args[2], if not null, will contains the error topic</li>
     * </ol>
     */
    @Override
    public EventRequest<byte[]> transform(byte[] payload, Object... args) {
        String errorDesc = args.length>1?(String)args[1]:"null";
        Header errorHeader = new RecordHeader("ERROR_DESC", errorDesc.getBytes(StandardCharsets.UTF_8));

        Headers headers = args.length>0?(Headers)args[0]:new RecordHeaders();
        headers.add(errorHeader);

        EventRequest<byte[]> request = new EventRequest<>();
        request.setPayload(payload);
        request.setHeaders(headers);

        if(args.length>2 && Strings.isNotEmpty((String)args[2])){
            request.setTopic((String)args[2]);
        }

        return request;
    }
}
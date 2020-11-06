package eu.sia.meda.event.transformer;

import eu.sia.meda.event.request.EventRequest;
import eu.sia.meda.util.ColoredPrinters;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;

public class ErrorEventRequestTransformerTest {

    @Test
    public void test() {
        ColoredPrinters.PRINT_GREEN.println("Case: no args");

        ErrorEventRequestTransformer transformer = Mockito.spy(new ErrorEventRequestTransformer());

        String testString = "testString";

        EventRequest<byte[]> result = transformer.transform(testString.getBytes(StandardCharsets.UTF_8));

        Assert.assertNotNull(result);
        Assert.assertEquals(testString, new String(result.getPayload(), StandardCharsets.UTF_8));
        Assert.assertNull(result.getTopic());
        Assert.assertNotNull(result.getHeaders());
        Assert.assertEquals("null", new String(result.getHeaders().lastHeader("ERROR_DESC").value(), StandardCharsets.UTF_8));
        Assert.assertNull(result.getHeaders().lastHeader("LISTENER"));

        ColoredPrinters.PRINT_GREEN.println("Case: passing header");

        Headers testHeaders = new RecordHeaders();
        testHeaders.add("testHeader", "testHeader".getBytes(StandardCharsets.UTF_8));

        result = transformer.transform(testString.getBytes(StandardCharsets.UTF_8), testHeaders);

        Assert.assertNotNull(result);
        Assert.assertEquals(testString, new String(result.getPayload(), StandardCharsets.UTF_8));
        Assert.assertNull(result.getTopic());
        Assert.assertNotNull(result.getHeaders());
        Assert.assertEquals("null", new String(result.getHeaders().lastHeader("ERROR_DESC").value(), StandardCharsets.UTF_8));
        Assert.assertEquals("testHeader", new String(result.getHeaders().lastHeader("testHeader").value(), StandardCharsets.UTF_8));
        Assert.assertNull(result.getHeaders().lastHeader("LISTENER"));

        ColoredPrinters.PRINT_GREEN.println("Case: passing string for error header value");

        testHeaders = new RecordHeaders();
        testHeaders.add("testHeader", "testHeader".getBytes(StandardCharsets.UTF_8));

        result = transformer.transform(testString.getBytes(StandardCharsets.UTF_8), testHeaders, "error");

        Assert.assertNotNull(result);
        Assert.assertEquals(testString, new String(result.getPayload(), StandardCharsets.UTF_8));
        Assert.assertNull(result.getTopic());
        Assert.assertNotNull(result.getHeaders());
        Assert.assertEquals("error", new String(result.getHeaders().lastHeader("ERROR_DESC").value(), StandardCharsets.UTF_8));
        Assert.assertEquals("testHeader", new String(result.getHeaders().lastHeader("testHeader").value(), StandardCharsets.UTF_8));
        Assert.assertNull(result.getHeaders().lastHeader("LISTENER"));

        ColoredPrinters.PRINT_GREEN.println("Case: passing String for topic");

        testHeaders = new RecordHeaders();
        testHeaders.add("testHeader", "testHeader".getBytes(StandardCharsets.UTF_8));

        result = transformer.transform(testString.getBytes(StandardCharsets.UTF_8), testHeaders, "error", "topic");

        Assert.assertNotNull(result);
        Assert.assertEquals(testString, new String(result.getPayload(), StandardCharsets.UTF_8));
        Assert.assertNotNull(result.getTopic());
        Assert.assertEquals("topic", result.getTopic());
        Assert.assertNotNull(result.getHeaders());
        Assert.assertEquals("error", new String(result.getHeaders().lastHeader("ERROR_DESC").value(), StandardCharsets.UTF_8));
        Assert.assertEquals("testHeader", new String(result.getHeaders().lastHeader("testHeader").value(), StandardCharsets.UTF_8));
        Assert.assertNull(result.getHeaders().lastHeader("LISTENER"));

        ColoredPrinters.PRINT_GREEN.println("Case: called by onReceived");

        testHeaders = new RecordHeaders();
        testHeaders.add("testHeader", "testHeader".getBytes(StandardCharsets.UTF_8));

        result = onReceived(transformer,testString, testHeaders);

        Assert.assertNotNull(result);
        Assert.assertEquals(testString, new String(result.getPayload(), StandardCharsets.UTF_8));
        Assert.assertNotNull(result.getTopic());
        Assert.assertEquals("topic", result.getTopic());
        Assert.assertNotNull(result.getHeaders());
        Assert.assertEquals("error", new String(result.getHeaders().lastHeader("ERROR_DESC").value(), StandardCharsets.UTF_8));
        Assert.assertEquals("testHeader", new String(result.getHeaders().lastHeader("testHeader").value(), StandardCharsets.UTF_8));
        Assert.assertNotNull(result.getHeaders().lastHeader("LISTENER"));
        Assert.assertEquals(this.getClass().getName(), new String(result.getHeaders().lastHeader("LISTENER").value(), StandardCharsets.UTF_8));
    }

    private EventRequest<byte[]> onReceived(ErrorEventRequestTransformer transformer,String testString, Headers testHeaders) {
        return transformer.transform(testString.getBytes(StandardCharsets.UTF_8), testHeaders, "error", "topic");
    }
}

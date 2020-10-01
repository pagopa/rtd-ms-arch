package eu.sia.meda.event.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.event.request.EventRequest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Import({SimpleEventRequestTransformer.class})
public class SimpleEventRequestTrasnformerTest extends BaseSpringTest {

    @Autowired
    private SimpleEventRequestTransformer<String> stringTrasformer;

    @Autowired
    private SimpleEventRequestTransformer<byte[]> byteTrasformer;

    @SpyBean
    private ObjectMapper objectMapper;

    @Test
    public void test() throws IOException {
        String testString = "testString";

        EventRequest<String> resultString = stringTrasformer.transform(testString);

        Assert.assertNotNull(resultString);
        String strResult = objectMapper.readValue(resultString.getPayload(), String.class);
        Assert.assertEquals(testString, strResult);

        EventRequest<byte[]> resultByte = byteTrasformer.transform(testString.getBytes(StandardCharsets.UTF_8));

        Assert.assertNotNull(resultByte);
        String result = new String(resultByte.getPayload(), StandardCharsets.UTF_8);
        Assert.assertEquals(testString, result);

        Mockito.doThrow(new JsonProcessingException("error") {
        }).when(objectMapper).writeValueAsString(Mockito.any());
        try {
            stringTrasformer.transform(testString);
            Assert.fail("expected exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
            Assert.assertEquals("Cannot serialize payload!", e.getMessage());
        }

    }
}

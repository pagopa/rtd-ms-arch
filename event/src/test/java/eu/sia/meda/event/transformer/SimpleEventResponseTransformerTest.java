package eu.sia.meda.event.transformer;

import eu.sia.meda.event.response.EventResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public class SimpleEventResponseTransformerTest {
    @Test
    public void test() {
        SimpleEventResponseTransformer transformer = new SimpleEventResponseTransformer();

        ListenableFuture<SendResult> listenableFuture = Mockito.mock(ListenableFuture.class);

        EventResponse<Void> eventResponse = new EventResponse<>(true, "", listenableFuture);
        Boolean result = transformer.transform(eventResponse);

        Assert.assertNotNull(result);
        Assert.assertTrue(result);

        eventResponse = new EventResponse<>(false, "", listenableFuture);
        result = transformer.transform(eventResponse);

        Assert.assertNotNull(result);
        Assert.assertFalse(result);

        eventResponse = new EventResponse<>(false, "", null);
        try {
            transformer.transform(eventResponse);
            Assert.fail("expected exception");
        } catch (RuntimeException e) {
            Assert.assertTrue(e.getCause() instanceof NullPointerException);
        }
    }
}

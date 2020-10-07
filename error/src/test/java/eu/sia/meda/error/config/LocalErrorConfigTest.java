package eu.sia.meda.error.config;

import eu.sia.meda.exceptions.model.ErrorMessage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

public class LocalErrorConfigTest {

    private static final String MESSAGE = "testMessage";
    private static final String MESSAGE_TITLE = "testMessageTitle";
    private static final String SEVERITY = "testSeverity";
    private static final String MESSAGE_KEY = "testKey";

    @Test
    public void testSetMessages(){
        LocalErrorConfig config = new LocalErrorConfig();
        Assert.assertNotNull(config.getMessages());
        Assert.assertTrue(config.getMessages().isEmpty());

        ErrorMessage message = new ErrorMessage();
        message.setMessage(MESSAGE);
        message.setMessageTitle(MESSAGE_TITLE);
        message.setSeverity(SEVERITY);
        message.setMessageKey(MESSAGE_KEY);

        config.setMessages(Collections.singletonMap(MESSAGE_KEY,message));

        Map<String, ErrorMessage> messages = config.getMessages();
        Assert.assertNotNull(messages);
        Assert.assertFalse(messages.isEmpty());
        ErrorMessage resultMessage = messages.get(MESSAGE_KEY.toLowerCase());
        Assert.assertNotNull(resultMessage);
        Assert.assertEquals(MESSAGE,resultMessage.getMessage());
        Assert.assertEquals(MESSAGE_TITLE,resultMessage.getMessageTitle());
        Assert.assertEquals(SEVERITY,resultMessage.getSeverity());
    }
}

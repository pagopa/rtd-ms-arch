package eu.sia.meda.exceptions;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MedaTransactionExceptionTest {

    private static final String MESSAGE = "testMessage";

    @Test
    public void testConstructors(){
        MedaTransactionException exception = new MedaTransactionException(MESSAGE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        Exception e = new Exception();
        exception = new MedaTransactionException(MESSAGE,e);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
        Assert.assertTrue(exception.getCause() instanceof Exception);

        e = new Exception(MESSAGE);
        exception = new MedaTransactionException(e);
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception.getCause() instanceof Exception);
        Assert.assertEquals(e.toString(),exception.getMessage());
        Assert.assertEquals(MESSAGE,exception.getCause().getMessage());
    }
}

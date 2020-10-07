package eu.sia.meda.exceptions;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MedaRollbackExceptionTest {

    private static final String MESSAGE = "testMessage";

    @Test
    public void testConstructors(){
        MedaRollbackException exception = new MedaRollbackException(MESSAGE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        Exception e = new Exception();
        exception = new MedaRollbackException(MESSAGE,e);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
        Assert.assertTrue(exception.getCause() instanceof Exception);

        e = new Exception(MESSAGE);
        exception = new MedaRollbackException(e);
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception.getCause() instanceof Exception);
        Assert.assertEquals(e.toString(),exception.getMessage());
        Assert.assertEquals(MESSAGE,exception.getCause().getMessage());
    }
}

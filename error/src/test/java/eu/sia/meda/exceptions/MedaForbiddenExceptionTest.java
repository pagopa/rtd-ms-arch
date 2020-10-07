package eu.sia.meda.exceptions;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MedaForbiddenExceptionTest {

    private static final String GENERIC_FORBIDDEN = "GENERIC";
    private static final String MESSAGE = "testMessage";
    private static final String CODE = "testCode";

    @Test
    public void testConstructors(){
        MedaForbiddenException exception = new MedaForbiddenException(MESSAGE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        exception = new MedaForbiddenException(MESSAGE,CODE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
    }
}

package eu.sia.meda.exceptions;

import eu.sia.meda.exceptions.model.MedaError;
import eu.sia.meda.exceptions.model.MedaErrorTypeEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MicroServiceExceptionTest {

    public static final String MESSAGE = "testMessage";
    public static final String MESSAGE_UNKNOWN = "Message unspecified";
    public static final String CODE = "testCode";

    @Test
    public void testConstructors(){
        MicroServiceException exception = new MicroServiceException();
        Assert.assertNotNull(exception);
        Assert.assertNull(exception.getMessage());

        exception = new MicroServiceException(MESSAGE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        exception = new MicroServiceException(MESSAGE,CODE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        exception = new MicroServiceException(MESSAGE,CODE, MedaErrorTypeEnum.BUSINESS);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        MedaDomainRuntimeException runtimeException = new MedaDomainRuntimeException(MESSAGE,CODE, HttpStatus.BAD_REQUEST);
        exception = new MicroServiceException(runtimeException);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        exception = new MicroServiceException(runtimeException,CODE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());

        MedaError medaError = new MedaError(MESSAGE);
        exception = new MicroServiceException(medaError);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
    }
}

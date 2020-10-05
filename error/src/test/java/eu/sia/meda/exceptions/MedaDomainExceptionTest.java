package eu.sia.meda.exceptions;

import eu.sia.meda.exceptions.model.MedaErrorTypeEnum;
import eu.sia.meda.exceptions.model.MedaSeverityEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

public class MedaDomainExceptionTest {

    public static final String MESSAGE = "testMessage";
    public static final String CODE = "testCode";
    public static final String ADDITIONAL_INFO_KEY = "testAdditionalInfoKey";
    public static final String ADDITIONAL_INFO_VALUE = "testAdditionalInfoValue";
    public static final String RAW_REMOTE_ERROR = "testRawRemoteError";
    public static final String REMOTE_SOURCE = "testRemoteSource";

    @Test
    public void testConsturctors(){
        MedaDomainException exception = new MedaDomainException(MESSAGE,CODE, HttpStatus.BAD_REQUEST);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
        Assert.assertEquals(CODE,exception.getCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,exception.getResponseStatus());
        Assert.assertEquals(MedaErrorTypeEnum.BUSINESS,exception.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR,exception.getSeverity());
        Assert.assertNull(exception.getAdditionalInfo());
        Assert.assertNull(exception.getRawRemoteError());
        Assert.assertNull(exception.getRemoteSource());

        exception = new MedaDomainException(MESSAGE,CODE, HttpStatus.BAD_REQUEST, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.FATAL);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
        Assert.assertEquals(CODE,exception.getCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,exception.getResponseStatus());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL,exception.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.FATAL,exception.getSeverity());
        Assert.assertNull(exception.getAdditionalInfo());
        Assert.assertNull(exception.getRawRemoteError());
        Assert.assertNull(exception.getRemoteSource());

        Map<String,Object> additionalInfo = Collections.singletonMap(ADDITIONAL_INFO_KEY,ADDITIONAL_INFO_VALUE);
        exception = new MedaDomainException(MESSAGE,CODE, HttpStatus.BAD_REQUEST, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.FATAL,additionalInfo);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
        Assert.assertEquals(CODE,exception.getCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,exception.getResponseStatus());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL,exception.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.FATAL,exception.getSeverity());
        Assert.assertFalse(exception.getAdditionalInfo().isEmpty());
        Assert.assertEquals(ADDITIONAL_INFO_VALUE,exception.getAdditionalInfo().get(ADDITIONAL_INFO_KEY));
        Assert.assertNull(exception.getRawRemoteError());
        Assert.assertNull(exception.getRemoteSource());

        exception = new MedaDomainException(MESSAGE,CODE, HttpStatus.BAD_REQUEST, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.FATAL,RAW_REMOTE_ERROR,REMOTE_SOURCE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
        Assert.assertEquals(CODE,exception.getCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,exception.getResponseStatus());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL,exception.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.FATAL,exception.getSeverity());
        Assert.assertNull(exception.getAdditionalInfo());
        Assert.assertEquals(RAW_REMOTE_ERROR,exception.getRawRemoteError());
        Assert.assertEquals(REMOTE_SOURCE,exception.getRemoteSource());

        exception = new MedaDomainException(MESSAGE,CODE, HttpStatus.BAD_REQUEST, MedaErrorTypeEnum.TECHNICAL, MedaSeverityEnum.FATAL,additionalInfo,RAW_REMOTE_ERROR,REMOTE_SOURCE);
        Assert.assertNotNull(exception);
        Assert.assertEquals(MESSAGE,exception.getMessage());
        Assert.assertEquals(CODE,exception.getCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST,exception.getResponseStatus());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL,exception.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.FATAL,exception.getSeverity());
        Assert.assertFalse(exception.getAdditionalInfo().isEmpty());
        Assert.assertEquals(ADDITIONAL_INFO_VALUE,exception.getAdditionalInfo().get(ADDITIONAL_INFO_KEY));
        Assert.assertEquals(RAW_REMOTE_ERROR,exception.getRawRemoteError());
        Assert.assertEquals(REMOTE_SOURCE,exception.getRemoteSource());
    }
}

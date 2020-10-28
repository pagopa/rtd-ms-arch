package eu.sia.meda.exceptions.model;

import eu.sia.meda.exceptions.MedaDomainException;
import eu.sia.meda.exceptions.MedaDomainRuntimeException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

public class MedaErrorTest {

    private MedaError medaError;

    public static final String MESSAGE_UNKNOWN = "Message unspecified";
    public static final String MESSAGE_TEST = "testMessage";
    public static final String RAW_REMOTE_ERROR = "testRawRemoteError";
    public static final String REMOTE_SOURCE = "testRemoteSource";

    @BeforeEach
    public void init(){
        medaError = new MedaError();
    }

    @Test
    public void testConstructors(){
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_UNKNOWN, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.CODE_GENERIC, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());

        medaError = new MedaError(MESSAGE_TEST);
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.CODE_GENERIC, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());

        medaError = new MedaError(MESSAGE_TEST, MedaErrorCode.INVALID_OPERATION);
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.INVALID_OPERATION, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());

        medaError =new MedaError(MESSAGE_TEST, MedaErrorCode.INVALID_OPERATION, MedaErrorTypeEnum.BUSINESS);
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.INVALID_OPERATION, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.BUSINESS, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());

        Map<String,Object> additionalInfoMap = Collections.singletonMap("testKey",2);

        MedaDomainException medaDomainException = new MedaDomainException(MESSAGE_TEST, MedaErrorCode.INVALID_OPERATION,
                HttpStatus.BAD_REQUEST, MedaErrorTypeEnum.BUSINESS, MedaSeverityEnum.ERROR, additionalInfoMap,
                RAW_REMOTE_ERROR,REMOTE_SOURCE);
        medaError = new MedaError(medaDomainException);
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.INVALID_OPERATION, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.BUSINESS, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());
        Assert.assertEquals(RAW_REMOTE_ERROR, medaError.getRawRemoteError());
        Assert.assertEquals(REMOTE_SOURCE, medaError.getRemoteSource());
        Map<String, Object> additionalInfo = medaError.getAdditionalInfo();
        Assert.assertFalse(additionalInfo.isEmpty());
        Assert.assertEquals(2,additionalInfo.get("testKey"));

        MedaDomainRuntimeException medaDomainRuntimeException = new MedaDomainRuntimeException(MESSAGE_TEST, MedaErrorCode.INVALID_OPERATION,
                HttpStatus.BAD_REQUEST, MedaErrorTypeEnum.BUSINESS, MedaSeverityEnum.ERROR, additionalInfoMap,
                RAW_REMOTE_ERROR,REMOTE_SOURCE);
        medaError = new MedaError(medaDomainRuntimeException);
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.INVALID_OPERATION, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.BUSINESS, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());
        Assert.assertEquals(RAW_REMOTE_ERROR, medaError.getRawRemoteError());
        Assert.assertEquals(REMOTE_SOURCE, medaError.getRemoteSource());
        additionalInfo = medaError.getAdditionalInfo();
        Assert.assertFalse(additionalInfo.isEmpty());
        Assert.assertEquals(2,additionalInfo.get("testKey"));

        Throwable throwable = new Throwable(MESSAGE_TEST);
        medaError = new MedaError(throwable);
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.CODE_GENERIC, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());

        medaError = new MedaError(throwable, MedaErrorCode.INVALID_OPERATION);
        Assert.assertNotNull(medaError);
        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
        Assert.assertEquals(MedaErrorCode.INVALID_OPERATION, medaError.getCode());
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL, medaError.getErrorType());
        Assert.assertEquals(MedaSeverityEnum.ERROR, medaError.getSeverity());
    }

    @Test
    public void testSetMessage(){
        Assert.assertEquals(MESSAGE_UNKNOWN, medaError.getMessage());

        medaError.setMessage(MESSAGE_TEST);

        Assert.assertEquals(MESSAGE_TEST, medaError.getMessage());
    }

    @Test
    public void testSetCode(){
        Assert.assertEquals(MedaErrorCode.CODE_GENERIC, medaError.getCode());

        medaError.setCode(MedaErrorCode.INVALID_OPERATION);

        Assert.assertEquals(MedaErrorCode.INVALID_OPERATION, medaError.getCode());
    }

    @Test
    public void testSetErrorType(){
        Assert.assertEquals(MedaErrorTypeEnum.TECHNICAL, medaError.getErrorType());

        medaError.setErrorType(MedaErrorTypeEnum.BUSINESS);

        Assert.assertEquals(MedaErrorTypeEnum.BUSINESS, medaError.getErrorType());
    }

    @Test
    public void testSetRawRemoteError(){
        Assert.assertNull(medaError.getRawRemoteError());

        medaError.setRawRemoteError(RAW_REMOTE_ERROR);

        Assert.assertEquals(RAW_REMOTE_ERROR, medaError.getRawRemoteError());
    }

    @Test
    public void testSetSeverity(){
        Assert.assertEquals(MedaSeverityEnum.ERROR,medaError.getSeverity());

        medaError.setSeverity(MedaSeverityEnum.FATAL);

        Assert.assertEquals(MedaSeverityEnum.FATAL, medaError.getSeverity());
    }

    @Test
    public void testSetRemoteSource(){
        Assert.assertNull(medaError.getRemoteSource());

        medaError.setRemoteSource(REMOTE_SOURCE);

        Assert.assertEquals(REMOTE_SOURCE, medaError.getRemoteSource());
    }

    @Test
    public void testSetExtendedMessage(){
        Assert.assertNull(medaError.getExtendedMessage());

        medaError.setExtendedMessage("testExtendedMessage");

        Assert.assertEquals("testExtendedMessage", medaError.getExtendedMessage());
    }

    @Test
    public void testSetLocalizedMessage(){
        Assert.assertNull(medaError.getLocalizedMessage());

        medaError.setLocalizedMessage("testLocalizedMessage");

        Assert.assertEquals("testLocalizedMessage", medaError.getLocalizedMessage());
    }


    @Test
    public void testSetAdditionalInfo(){
        Assert.assertNull(medaError.getAdditionalInfo());

        medaError.setAdditionalInfo(Collections.singletonMap("testAdditionalInfoKey","testAdditionalInfoValue"));

        Assert.assertEquals("testAdditionalInfoValue", medaError.getAdditionalInfo().get("testAdditionalInfoKey"));
    }

    @Test
    public void testSetTechnicalInfo(){
        Assert.assertNull(medaError.getTechnicalInfo());

        medaError.setTechnicalInfo(Collections.singletonMap("testTechnicalInfoKey","testTechnicalInfoValue"));

        Assert.assertEquals("testTechnicalInfoValue", medaError.getTechnicalInfo().get("testTechnicalInfoKey"));
    }

    @Test
    public void testAppendTechnicalInfo(){
        String technicalKey = "testTechnicalInfoKey";
        String technicalValue = "testTechnicalInfoValue";
        Assert.assertNull(medaError.getTechnicalInfo());

        medaError.appendTechnicalInfo(Collections.singletonMap(technicalKey,technicalValue));

        Assert.assertEquals(technicalValue, medaError.getTechnicalInfo().get(technicalKey));

        medaError.setTechnicalInfo(null);
        Assert.assertNull(medaError.getTechnicalInfo());

        medaError.appendTechnicalInfo(technicalKey,technicalValue);

        Assert.assertEquals(technicalValue, medaError.getTechnicalInfo().get(technicalKey));
    }

    @Test
    public void testAppendAdditionalInfo(){
        String additionalKey = "testAdditionalInfoKey";
        String additionalValue = "testAdditionalInfoValue";
        Assert.assertNull(medaError.getAdditionalInfo());

        medaError.appendAdditionalInfo(Collections.singletonMap(additionalKey,additionalValue));

        Assert.assertEquals(additionalValue, medaError.getAdditionalInfo().get(additionalKey));

        medaError.setAdditionalInfo(null);
        Assert.assertNull(medaError.getAdditionalInfo());

        medaError.appendAdditionalInfo(additionalKey,additionalValue);

        Assert.assertEquals(additionalValue, medaError.getAdditionalInfo().get(additionalKey));
    }

    @Test
    public void testToString(){
        String expected = "Error(message=" + MESSAGE_UNKNOWN + ", code=" + MedaErrorCode.CODE_GENERIC + ", errorType=" +
                MedaErrorTypeEnum.TECHNICAL + ", severity=" + MedaSeverityEnum.ERROR + ", remoteSource=" + null +
                ", rawRemoteError=" + null + ")";

        Assert.assertEquals(expected,medaError.toString());
    }
}

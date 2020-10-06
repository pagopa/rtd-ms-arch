package eu.sia.meda.error.handler;

import feign.FeignException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MedaExceptionHandlerTest {

    @Test
    public void test(){
        MedaExceptionHandler handler = new MedaExceptionHandler();
        FeignException mockException = Mockito.mock(FeignException.class);
        Mockito.when(mockException.status()).thenReturn(403);

        ResponseEntity<String> response = handler.handleFeignException(mockException);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }
}

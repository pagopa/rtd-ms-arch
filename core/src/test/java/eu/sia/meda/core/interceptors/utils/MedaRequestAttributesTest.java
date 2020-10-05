package eu.sia.meda.core.interceptors.utils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

public class MedaRequestAttributesTest {

    @Test
    public void testGetRequestId(){
        HttpServletRequest request = setupTest("x-request-id","testRequestId");
        String result = MedaRequestAttributes.getRequestId(request);
        verifyTest(request,result,"x-request-id","testRequestId");
    }

    @Test
    public void testGetTransactionId(){
        HttpServletRequest request = setupTest("x-transaction-id","testTransactionId");
        String result = MedaRequestAttributes.getTransactionId(request);
        verifyTest(request,result,"x-transaction-id","testTransactionId");
    }

    @Test
    public void testGetOriginApp(){
        HttpServletRequest request = setupTest("x-originapp", "testOriginApp");
        String result = MedaRequestAttributes.getOriginApp(request);
        verifyTest(request,result,"x-originapp", "testOriginApp");
    }

    protected HttpServletRequest setupTest(String id, String value){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader(Mockito.eq(id))).thenReturn(value);
        Mockito.when(request.getAttribute(Mockito.eq(id))).thenReturn(null).thenReturn(value);

        return request;
    }

    protected void verifyTest(HttpServletRequest request, String result, String id, String value){
        Assert.assertEquals(value,result);
        Mockito.verify(request).setAttribute(Mockito.eq(id),Mockito.eq(value));
    }
}

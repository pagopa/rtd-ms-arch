package eu.sia.meda.core.interceptors;

import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.AuthorizationContext;
import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.core.model.ErrorContext;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BaseContextHolderTest {

    @BeforeEach
    public void init(){
        BaseContextHolder.clear();
    }

    @Test
    public void testGetApplicationContext(){
        ApplicationContext applicationContext = BaseContextHolder.getApplicationContext();
        Assert.assertNotNull(applicationContext);

        BaseContextHolder.clear();

        applicationContext = BaseContextHolder.getApplicationContext();
        Assert.assertNotNull(applicationContext);
        Assert.assertEquals("UNKNOWN", applicationContext.getOriginApp());
        Assert.assertEquals("anonymousUser", applicationContext.getUserId());
        Assert.assertEquals("NONE", applicationContext.getApimRequestId());
    }

    @Test
    public void testSetSessionContext(){

        Assert.assertNull(BaseContextHolder.getSessionContext());

        BaseContext context = new BaseContext<>();
        BaseContextHolder.setSessionContext(context);

        Assert.assertNotNull(BaseContextHolder.getSessionContext());

        try {
            BaseContextHolder.setSessionContext(context);
            Assert.fail("expected exception");
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testSetAuthorizationContext(){

        Assert.assertNull(BaseContextHolder.getAuthorizationContext());

        AuthorizationContext context = new AuthorizationContext();
        BaseContextHolder.setAuthorizationContext(context);

        Assert.assertNotNull(BaseContextHolder.getAuthorizationContext());

        try {
            BaseContextHolder.setAuthorizationContext(context);
            Assert.fail("expected exception");
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testSetErrorContext(){

        Assert.assertNull(BaseContextHolder.getErrorContext());

        ErrorContext context = new ErrorContext("testProjectId");
        BaseContextHolder.setErrorContext(context);

        Assert.assertNotNull(BaseContextHolder.getErrorContext());

        try {
            BaseContextHolder.setErrorContext(context);
            Assert.fail("expected exception");
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testSetApplicationContext(){
        ApplicationContext applicationContext = BaseContextHolder.getApplicationContext();
        Assert.assertNotNull(applicationContext);
        Assert.assertEquals("UNKNOWN",applicationContext.getOriginApp());
        Assert.assertEquals("anonymousUser", applicationContext.getUserId());
        Assert.assertEquals("NONE", applicationContext.getApimRequestId());

        applicationContext.setOriginApp("testOriginApp");
        applicationContext.setUserId("testUserId");
        applicationContext.setApimRequestId("testApimRequestId");

        BaseContextHolder.setApplicationContext(applicationContext);

        applicationContext = BaseContextHolder.getApplicationContext();
        Assert.assertNotNull(applicationContext);
        Assert.assertEquals("testOriginApp",applicationContext.getOriginApp());
        Assert.assertEquals("testUserId",applicationContext.getUserId());
        Assert.assertEquals("testApimRequestId",applicationContext.getApimRequestId());

        try {
            BaseContextHolder.setApplicationContext(applicationContext);
            Assert.fail("expected exception");
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }
}

package eu.sia.meda.async.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.AuthorizationContext;
import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.core.model.ErrorContext;
import org.mockito.Mockito;

class AsyncUtilsTest {

    private AsyncUtils asyncUtilsUnderTest;

    @BeforeEach
    void setUp() {
        asyncUtilsUnderTest = new AsyncUtils();
    }

    @Test
    void testCallAsyncService() {
        // Setup
        final Supplier<String> supplier = () -> {return "test";};

        final ApplicationContext applicationContext = new ApplicationContext();
        final AuthorizationContext authorizationContext = new AuthorizationContext();
        authorizationContext.setJwtToken("jwtToken");
        authorizationContext.setAuthorizationHeader("authorizationHeader");
        authorizationContext.setAdditionalClaims(new HashMap<>());

        final ErrorContext errorContext = new ErrorContext("projectId");
        final BaseContext sessionContext = new BaseContext<>();
        sessionContext.setSessionId("sessionId");
        sessionContext.setEnvironment("environment");
        sessionContext.setUser(null);
        sessionContext.setCustomer(null);

        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final CompletableFuture<String> expectedResult = new CompletableFuture<>();

        // Run the test
        final CompletableFuture<String> result = asyncUtilsUnderTest.callAsyncService(supplier, applicationContext, authorizationContext, errorContext, sessionContext, request);

        // Verify the results
        assertNotNull(result);
        try {
			assertEquals("test", result.get());
		} catch (InterruptedException | ExecutionException e) {
			fail();
		} 
    }
}

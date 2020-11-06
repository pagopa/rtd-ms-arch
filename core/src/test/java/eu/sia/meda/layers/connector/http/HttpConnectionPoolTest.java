package eu.sia.meda.layers.connector.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.apache.http.auth.Credentials;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import eu.sia.meda.config.http.HttpConnectionPoolConfiguration;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ScheduledFuture;

class HttpConnectionPoolTest {

	@Mock
	private HttpConnectionPoolConfiguration mockConfiguration;
	@Mock
	private Credentials mockCredentials;
	@Mock
	private HttpConnectionPoolSweeperScheduler mockConnectionPoolSweeperScheduler;

	private HttpConnectionPool httpConnectionPoolUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
		when(mockConfiguration.getMaxConnections()).thenReturn(1);
		httpConnectionPoolUnderTest = new HttpConnectionPool(mockConfiguration, mockCredentials,
				mockConnectionPoolSweeperScheduler);
	}

	@Test
	void testGetMaxConnections() {
		// Setup
		final int expectedResult = 1;

		// Run the test
		final int result = httpConnectionPoolUnderTest.getMaxConnections();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	void testShutdownException() throws Exception {
		// Run the test - connector null
		try {
			httpConnectionPoolUnderTest.shutdown();
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	void testShutdownAsync() {
		// Run the test
		try {
			httpConnectionPoolUnderTest.shutdownAsync();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}


	@Test
	public void testGetHttpClient(){
		Assert.assertNotNull(httpConnectionPoolUnderTest.getHttpClient());
	}

	@Test
	public void testShutdownNoException(){
		ScheduledFuture scheduledFuture = Mockito.mock(ScheduledFuture.class);
		ReflectionTestUtils.setField(httpConnectionPoolUnderTest,"connectionSweeper",scheduledFuture);

		try {
			httpConnectionPoolUnderTest.shutdown();
		} catch (IOException e) {
			Assert.fail();
		}
		Mockito.verify(scheduledFuture).cancel(Mockito.eq(false));
	}

	@Test
	void testShutdownAsyncWithException() {
		// Run the test
		try {
			CompletableFuture<Void> resultCompletableFuture = httpConnectionPoolUnderTest.shutdownAsync();
			resultCompletableFuture.join();
			Assert.fail("expected exception");
		} catch (Exception e) {
			Assert.assertTrue(e instanceof CompletionException);
		}
	}
}

package eu.sia.meda.core.command;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import eu.sia.meda.async.util.AsyncUtils;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.switcher.CopySwitcherSemaphoreRegistry;

class BaseCommandTest {

	@Mock
	private ApplicationContext mockApplicationContext;
	@Mock
	private AsyncUtils mockAsyncUtils;
	@Mock
	private CopySwitcherSemaphoreRegistry mockCopySwitcher;

	@Mock
	private BaseCommand<String> baseCommandUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
	}

	@Test
	void testExecute() throws Exception {
		// Run the test
		final String result = baseCommandUnderTest.execute();

		// Verify the results
		assertNull(result);
	}

	@Test
	void testCallAsyncService() {
		// Setup
		final Supplier<String> t = () -> "test";
		// Run the test
		final CompletableFuture<String> result = baseCommandUnderTest.callAsyncService(t);
		assertNull(result);
	}
}

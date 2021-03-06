package eu.sia.meda.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ApplicationContextTest {

	private ApplicationContext applicationContextUnderTest;

	@BeforeEach
	void setUp() {
		applicationContextUnderTest = new ApplicationContext();
	}

	@Test
	void testInitWithDefault() {
		// Run the test
		final ApplicationContext result = ApplicationContext.initWithDefault();

		// Verify the results
		assertNotNull(result);
		assertNotNull(result.getRequestId());
		assertNotNull(result.getTransactionId());
	}

	@Test
	public void testSetName() throws Exception {
		applicationContextUnderTest.setName("name");
		assertEquals("name", applicationContextUnderTest.getName());
	}

	@Test
	public void testSetRequestId() throws Exception {
		applicationContextUnderTest.setRequestId("requestId");
		assertEquals("requestId", applicationContextUnderTest.getRequestId());
	}

	@Test
	public void testSetTransactionId() throws Exception {
		applicationContextUnderTest.setTransactionId("transactionId");
		assertEquals("transactionId", applicationContextUnderTest.getTransactionId());
	}


	@Test
	public void testGetCopyHeader(){
		applicationContextUnderTest.buildDefaultCopyHeader();
		Map<String, String> copyHeader = applicationContextUnderTest.getCopyHeader();
		Assert.assertNotNull(copyHeader);
		Assert.assertFalse(copyHeader.isEmpty());
		Assert.assertEquals(5,copyHeader.size());
	}
}

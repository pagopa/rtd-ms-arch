package eu.sia.meda.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseContextTest {

	private BaseContext<String, String> baseContextUnderTest;

	@BeforeEach
	void setUp() {
		baseContextUnderTest = new BaseContext<>();
	}

	@Test
	public void testGetSessionId() throws Exception {
		baseContextUnderTest.setSessionId("sessionId");
		assertEquals("sessionId", baseContextUnderTest.getSessionId());
	}

	@Test
	public void testSetEnvironment() throws Exception {
		baseContextUnderTest.setEnvironment("environment");
		assertEquals("environment", baseContextUnderTest.getEnvironment());
	}

	@Test
	public void testSetUser() throws Exception {
		baseContextUnderTest.setUser("user");
		assertEquals("user", baseContextUnderTest.getUser());
	}

	@Test
	public void testSetCustomer() throws Exception {
		baseContextUnderTest.setCustomer("customer");
		assertEquals("customer", baseContextUnderTest.getCustomer());
	}

}

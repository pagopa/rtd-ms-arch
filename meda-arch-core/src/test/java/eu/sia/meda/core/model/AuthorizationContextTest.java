package eu.sia.meda.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthorizationContextTest {

	private AuthorizationContext authorizationContextUnderTest;

	@BeforeEach
	void setUp() {
		authorizationContextUnderTest = new AuthorizationContext();
	}

	@Test
	public void testSetJwtToken() throws Exception {
		authorizationContextUnderTest.setJwtToken("jwtToken");
		assertEquals("jwtToken", authorizationContextUnderTest.getJwtToken());
	}

	@Test
	public void testSetAuthorizationHeader() throws Exception {
		authorizationContextUnderTest.setAuthorizationHeader("authHeader");
		assertEquals("authHeader", authorizationContextUnderTest.getAuthorizationHeader());
	}

	@Test
	public void testSetAdditionalClaims() throws Exception {
		Map<String, Object> tmp = new HashMap<>();
		tmp.put("testKey", "testValue");
		authorizationContextUnderTest.setAdditionalClaims(tmp);
		assertEquals(tmp, authorizationContextUnderTest.getAdditionalClaims());
		assertEquals(tmp.get("testKey"), authorizationContextUnderTest.getAdditionalClaims().get("testKey"));
	}
}

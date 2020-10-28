package eu.sia.meda.config.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.ConfigurableEnvironment;

import eu.sia.meda.core.properties.PropertiesManager;

class HttpConnectionPoolConfigurationTest {

	@Mock
	private ConfigurableEnvironment mockEnvironment;

	@InjectMocks
	private PropertiesManager propertiesManager;

	@BeforeEach
	void setUp() {
		initMocks(this);
	}

	@Test
	void testLoadFromProperties() {
		// Setup
		when(mockEnvironment.containsProperty("connectorType.items.className.connectionPool.connectTimeout"))
				.thenReturn(true);
		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"connectionPool" + "." + "connectTimeout", Integer.class)).thenReturn(20);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className");

		// Verify the results
		assertNotNull(result);
		assertNull(result.getMaxConnections());
	}

	@Test
	void testLoadFromProperties1() {
		// Setup
		when(mockEnvironment.containsProperty("connectorType.items.className.prefix.connectTimeout")).thenReturn(true);
		when(propertiesManager.getConnectorProperty("connectorType", "className", "prefix" + "." + "connectTimeout",
				Integer.class)).thenReturn(20);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className", "prefix" + "." + "connectTimeout");

		// Verify the results
		assertNotNull(result);
		assertEquals(Integer.valueOf(20), result.getMaxConnections());
	}

	@Test
	void testMakeDefaultConfig() {
		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration.makeDefaultConfig();

		// Verify the results
		assertNotNull(result);
		assertNull(result.getConnectorClassName());
	}

	@Test
	void testMakeDefaultConfig1() {
		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.makeDefaultConfig("connectorClassName");

		// Verify the results
		assertNotNull(result);
		assertEquals("connectorClassName", result.getConnectorClassName());
	}

	@Test
	void testConfigurationDisabled() {
		// Setup
		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"connectionPool" + "." + "enabled", Boolean.class)).thenReturn(Boolean.FALSE);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className");

		// Verify the results
		Assert.assertNull(result);
	}

	@Test
	void testConfigurationSetReadTimeout() {
		// Setup
		/*when(mockEnvironment.containsProperty("connectorType.items.className.connectionPool.timeout"))
				.thenReturn(true);*/
		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"connectionPool" + "." + "enabled", Boolean.class)).thenReturn(Boolean.TRUE);

		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"timeout", Integer.class)).thenReturn(20);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className");

		// Verify the results
		Assert.assertNotNull(result);
		Assert.assertEquals(20,(long)result.getReadTimeout());
	}

	@Test
	void testConfigurationSetConnectTimeout() {
		// Setup
		/*when(mockEnvironment.containsProperty("connectorType.items.className.connectionPool.connectTimeout"))
				.thenReturn(true);*/
		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"connectionPool" + "." + "connectTimeout", Integer.class)).thenReturn(20);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className");

		// Verify the results
		Assert.assertNotNull(result);
		Assert.assertEquals(20,(long)result.getConnectTimeout());
	}

	@Test
	void testConfigurationSetConnectRequestTimeout() {
		// Setup
		/*when(mockEnvironment.containsProperty("connectorType.items.className.connectionPool.connectRequestTimeout"))
				.thenReturn(true);*/
		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"connectionPool" + "." + "connectRequestTimeout", Integer.class)).thenReturn(20);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className");

		// Verify the results
		Assert.assertNotNull(result);
		Assert.assertEquals(20,(long)result.getConnectRequestTimeout());
	}

	@Test
	void testConfigurationSetConnectionSweeperInterval() {
		// Setup
		when(mockEnvironment.containsProperty("connectorType.items.className.connectionPool.connectionSweeperInterval"))
				.thenReturn(true);
		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"connectionPool" + "." + "connectionSweeperInterval", Integer.class)).thenReturn(20);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className");

		// Verify the results
		Assert.assertNotNull(result);
		Assert.assertEquals(20,(long)result.getConnectionSweeperInterval());
	}

	@Test
	void testConfigurationSetIdleTimeout() {
		// Setup
		/*when(mockEnvironment.containsProperty("connectorType.items.className.connectionPool.idleTimeout"))
				.thenReturn(true);*/
		when(propertiesManager.getConnectorProperty("connectorType", "className",
				"connectionPool" + "." + "idleTimeout", Integer.class)).thenReturn(20);

		// Run the test
		final HttpConnectionPoolConfiguration result = HttpConnectionPoolConfiguration
				.loadFromProperties(propertiesManager, "connectorType", "className");

		// Verify the results
		Assert.assertNotNull(result);
		Assert.assertEquals(20,(long)result.getIdleTimeout());
	}
}

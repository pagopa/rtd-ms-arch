package eu.sia.meda.tracing.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.AuditableConnectorConfiguration;
import eu.sia.meda.layers.connector.TraceableConnectorConfiguration;

class TracingConfigurationTest {

	@Mock
	private PropertiesManager mockPropertiesManager;

	private TracingConfiguration tracingConfigurationUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
		tracingConfigurationUnderTest = new TracingConfiguration(mockPropertiesManager);
	}

	@Test
	void testIsTracingEnabledFor() {
		// Setup
		final TraceableConnectorConfiguration connectorConfiguration = mock(TraceableConnectorConfiguration.class);
		when(mockPropertiesManager.get("tracing.enabled", Boolean.class)).thenReturn(true);
		when(connectorConfiguration.isEnabledTracing()).thenReturn(true);
		// Run the test
		final boolean result = tracingConfigurationUnderTest.isTracingEnabledFor(connectorConfiguration);

		// Verify the results
		assertTrue(result);
	}

	@Test
	void testIsAuditEnabledFor() {
		// Setup
		final AuditableConnectorConfiguration connectorConfiguration = mock(AuditableConnectorConfiguration.class);
		when(mockPropertiesManager.get("tracing.enabled", Boolean.class)).thenReturn(true);
		connectorConfiguration.setEnabledTracing(Boolean.FALSE);
		connectorConfiguration.setMocked(Boolean.TRUE);		
		// Run the test
		final boolean result = tracingConfigurationUnderTest.isAuditEnabledFor(connectorConfiguration);

		// Verify the results
		assertFalse(result);
	}
}

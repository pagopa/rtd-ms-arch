package eu.sia.meda.event.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import eu.sia.meda.core.properties.PropertiesManager;

public class ArchEventConfigurationServiceTest {

	@Mock
	private PropertiesManager mockPropertiesManager;

	@InjectMocks
	private ArchEventConfigurationService archEventConfigurationServiceUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
	}

	@Test
	void testRetrieveEventConfiguration() {
		// Setup
		when(mockPropertiesManager.containsConnectorProperty("connectors.eventConfigurations", "className", "topic"))
				.thenReturn(true);
		when(mockPropertiesManager.getConnectorProperty("connectors.eventConfigurations", "className", "topic",
				String.class)).thenReturn("topicName");

		// Run the test
		final ArchEventConfigurationService.EventConfiguration result = archEventConfigurationServiceUnderTest
				.retrieveEventConfiguration("className");

		// Verify the results
		assertNotNull(result);
		assertEquals("topicName", result.getTopic());
	}

	@Test
	void testRetrieveEventConfigurationNull() {
		// Setup
		when(mockPropertiesManager.containsConnectorProperty("connectors.eventConfigurations", "className", "topic"))
				.thenReturn(false);
		when(mockPropertiesManager.getConnectorProperty("connectors.eventConfigurations", "className", "topic",
				String.class)).thenReturn("topicName");

		// Run the test
		final ArchEventConfigurationService.EventConfiguration result = archEventConfigurationServiceUnderTest
				.retrieveEventConfiguration("className");

		// Verify the results
		assertNull(result);
	}

	@Test
	public void testGetSaslServiceName() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setTransactionalId("transactionId");
		assertNull(archEventConfigurationService_EventConfiguration0.getSaslServiceName());
	}

	@Test
	public void testGetBatchSize() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setTransactionTimeoutMs(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getBatchSize());
	}

	@Test
	public void test02() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setSecurityProtocol(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getMetadataMaxAgeMs());
	}

	@Test
	public void testGetBootstrapServers() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Long long0 = new Long((-1L));
		archEventConfigurationService_EventConfiguration0.setBufferMemory(long0);
		assertNull(archEventConfigurationService_EventConfiguration0.getBootstrapServers());
	}

	@Test
	public void testGetSecurityProtocol() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getSecurityProtocol();
		assertNull(string0);
	}

	@Test
	public void testGetTopic() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getTopic();
		assertNull(string0);
	}

	@Test
	public void testGetBootstrapServers1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setSaslJaasConf(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getBootstrapServers());
	}

	@Test
	public void testGetSaslJaasConf() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getSaslJaasConf();
		assertNull(string0);
	}

	@Test
	public void testGetRequestTimeoutMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Integer integer0 = archEventConfigurationService_EventConfiguration0.getRequestTimeoutMs();
		assertNull(integer0);
	}

	@Test
	public void testGetLingerMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setClientId(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getLingerMs());
	}

	@Test
	public void testGetMaxBlockMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setSaslServiceName(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getMaxBlockMs());
	}

	@Test
	public void testGetBatchSize1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Integer integer0 = archEventConfigurationService_EventConfiguration0.getBatchSize();
		assertNull(integer0);
	}

	@Test
	public void testCetSecurityProtocol() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setReconnectBackoffMs(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getSecurityProtocol());
	}

	@Test
	public void testGetMetadataMaxAgeMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Long long0 = archEventConfigurationService_EventConfiguration0.getMetadataMaxAgeMs();
		assertNull(long0);
	}

	@Test
	public void testGetRetryBackoffMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Long long0 = archEventConfigurationService_EventConfiguration0.getRetryBackoffMs();
		assertNull(long0);
	}

	@Test
	public void testGetCompressionType() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getCompressionType();
		assertNull(string0);
	}

	@Test
	public void testGetReconnectBackoffMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Long long0 = archEventConfigurationService_EventConfiguration0.getReconnectBackoffMs();
		assertNull(long0);
	}

	@Test
	public void testGetMaxRequestSize() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Integer integer0 = archEventConfigurationService_EventConfiguration0.getMaxRequestSize();
		assertNull(integer0);
	}

	@Test
	public void testGetMetadataMaxAgeMs1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setRetryBackoffMs(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getMetadataMaxAgeMs());
	}

	@Test
	public void testGetMaxBlockMs1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setBootstrapServers("localhost:9092");
		assertNull(archEventConfigurationService_EventConfiguration0.getMaxBlockMs());
	}

	@Test
	public void testGetTransactionalId() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setBatchSize(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getTransactionalId());
	}

	@Test
	public void testGetBootstrapServers2() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setMaxBlockMs(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getBootstrapServers());
	}

	@Test
	public void testGetReconnectBackoffMaxMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setRequestTimeoutMs(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getReconnectBackoffMaxMs());
	}

	@Test
	public void testGetReconnectBackoffMaxMs1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Long long0 = archEventConfigurationService_EventConfiguration0.getReconnectBackoffMaxMs();
		assertNull(long0);
	}

	@Test
	public void testGetMetadataMaxAgeMs2() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setLingerMs(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getMetadataMaxAgeMs());
	}

	@Test
	public void testGetMaxBlockMs2() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Long long0 = archEventConfigurationService_EventConfiguration0.getMaxBlockMs();
		assertNull(long0);
	}

	@Test
	public void testGetSaslJaasConf1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setMetadataMaxAgeMs(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getSaslJaasConf());
	}

	@Test
	public void testGetTransactionalId1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getTransactionalId();
		assertNull(string0);
	}

	@Test
	public void testgetRetryBackoffMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setCompressionType("compType");
		assertNull(archEventConfigurationService_EventConfiguration0.getRetryBackoffMs());
	}

	@Test
	public void testGetLingerMs1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Integer integer0 = archEventConfigurationService_EventConfiguration0.getLingerMs();
		assertNull(integer0);
	}

	@Test
	public void testGetBufferMemory() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setTopic(null);
		assertNull(archEventConfigurationService_EventConfiguration0.getBufferMemory());
	}

	@Test
	public void testGetTransactionTimeoutMs() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Integer integer0 = archEventConfigurationService_EventConfiguration0.getTransactionTimeoutMs();
		assertNull(integer0);
	}

	@Test
	public void testGetBufferMemory1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		Long long0 = archEventConfigurationService_EventConfiguration0.getBufferMemory();
		assertNull(long0);
	}

	@Test
	public void testGetSaslServiceName1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getSaslServiceName();
		assertNull(string0);
	}

	@Test
	public void testGetBootstrapServers3() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getBootstrapServers();
		assertNull(string0);
	}

	@Test
	public void testGetCompressionType1() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setReconnectBackoffMaxMs((Long) null);
		assertNull(archEventConfigurationService_EventConfiguration0.getCompressionType());
	}

	@Test
	public void testGetBufferMemory2() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		archEventConfigurationService_EventConfiguration0.setMaxRequestSize((Integer) null);
		assertNull(archEventConfigurationService_EventConfiguration0.getBufferMemory());
	}

	@Test
	public void testGetClientId() throws Throwable {
		ArchEventConfigurationService.EventConfiguration archEventConfigurationService_EventConfiguration0 = new ArchEventConfigurationService.EventConfiguration();
		String string0 = archEventConfigurationService_EventConfiguration0.getClientId();
		assertNull(string0);
	}

}

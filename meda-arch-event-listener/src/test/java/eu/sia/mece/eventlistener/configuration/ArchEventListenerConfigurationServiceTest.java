package eu.sia.meda.eventlistener.configuration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import eu.sia.meda.core.properties.PropertiesManager;

public class ArchEventListenerConfigurationServiceTest {

	@Mock
	private PropertiesManager mockPropertiesManager;

	@InjectMocks
	private ArchEventListenerConfigurationService archEventListenerConfigurationServiceUnderTest;

	@BeforeEach
	void setUp() {
		initMocks(this);
	}

	@Test
	void testRetrieveEventConfiguration() {
		// Setup
		when(mockPropertiesManager.containsConnectorProperty("listeners.eventConfigurations", "className", "topic"))
				.thenReturn(true);
		when(mockPropertiesManager.getConnectorProperty("listeners.eventConfigurations", "className", "topic", String.class))
				.thenReturn("topicListener");

		// Run the test
		final ArchEventListenerConfigurationService.EventListenerConfiguration result = archEventListenerConfigurationServiceUnderTest
				.retrieveEventConfiguration("className");

		// Verify the results
		assertNotNull(result);
		assertEquals("topicListener", result.getTopic());
	}

	@Test
	void testRetrieveEventConfigurationNull() {
		// Setup
		when(mockPropertiesManager.containsConnectorProperty("listeners.eventConfigurations", "className", "topicName"))
				.thenReturn(false);
		when(mockPropertiesManager.getConnectorProperty("listeners.eventConfigurations", "className", "topicName", String.class))
				.thenReturn("topicListener");

		// Run the test
		final ArchEventListenerConfigurationService.EventListenerConfiguration result = archEventListenerConfigurationServiceUnderTest
				.retrieveEventConfiguration("classNameTestNull");

		// Verify the results
		assertNull(result);
	}
	
	@Test
	public void test01() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setExcludeInternalTopics((Boolean) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getEnableAutoCommit());
	}

	@Test
	public void test02() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = new Long(268L);
		archEventListenerConfigurationService_EventListenerConfiguration0.setReconnectBackoffMaxMs(long0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getMaxFailures());
	}

	@Test
	public void test03() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setIsolationLevel("0O");
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getClientId());
	}

	@Test
	public void test04() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setSecurityProtocol((String) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getClientId());
	}

	@Test
	public void test05() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setFetchMaxBytes((Integer) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getSecurityProtocol());
	}

	@Test
	public void test06() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setReconnectBackoffMs((Long) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getMetadataMaxAgeMs());
	}

	@Test
	public void test07() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = new Integer(0);
		archEventListenerConfigurationService_EventListenerConfiguration0.setMaxPollRecords(integer0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getEnableAutoCommit());
	}

	@Test
	public void test08() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setMaxFailures((Integer) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getMaxPollIntervalMs());
	}

	@Test
	public void test09() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setClientId("request.timeout.ms");
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getFetchMaxWaitMs());
	}

	@Test
	public void test10() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setTopic("reconnect.backoff.ms");
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getCheckCrcs());
	}

	@Test
	public void test11() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setGroupId("");
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getConnectionsMaxIdleMs());
	}

	@Test
	public void test12() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setCheckCrcs((Boolean) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getBootstrapServers());
	}

	@Test
	public void test13() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setAckOnError((Boolean) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getSessionTimeoutMs());
	}

	@Test
	public void test14() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setSaslServiceName("");
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getBootstrapServers());
	}

	@Test
	public void test15() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = new Integer(0);
		archEventListenerConfigurationService_EventListenerConfiguration0.setFetchMinBytes(integer0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getMaxFailures());
	}

	@Test
	public void test16() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = new Long((-3063L));
		archEventListenerConfigurationService_EventListenerConfiguration0.setMetadataMaxAgeMs(long0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getCheckCrcs());
	}

	@Test
	public void test18() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = new Long(0L);
		archEventListenerConfigurationService_EventListenerConfiguration0.setConnectionsMaxIdleMs(long0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getCheckCrcs());
	}

	@Test
	public void test19() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = new Integer((-5681));
		archEventListenerConfigurationService_EventListenerConfiguration0.setConcurrency(integer0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getClientId());
	}

	@Test
	public void test20() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setSessionTimeoutMs((Integer) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getTopic());
	}

	@Test
	public void test22() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = new Integer(814);
		archEventListenerConfigurationService_EventListenerConfiguration0.setRequestTimeoutMs(integer0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getFetchMaxBytes());
	}

	@Test
	public void test23() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = new Integer((-1213));
		archEventListenerConfigurationService_EventListenerConfiguration0.setFetchMaxWaitMs(integer0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getEnableAutoCommit());
	}

	@Test
	public void test24() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setBootstrapServers((String) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getMetadataMaxAgeMs());
	}

	@Test
	public void test25() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setMaxPartitionFetchBytes((Integer) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getReconnectBackoffMaxMs());
	}

	@Test
	public void test26() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setAutoOffsetReset("");
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getSaslServiceName());
	}

	@Test
	public void test27() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		archEventListenerConfigurationService_EventListenerConfiguration0.setEnableAutoCommit((Boolean) null);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getMaxPollRecords());
	}

	@Test
	public void test29() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = archEventListenerConfigurationService_EventListenerConfiguration0.getReconnectBackoffMaxMs();
		assertNull(long0);
	}

	@Test
	public void test30() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = archEventListenerConfigurationService_EventListenerConfiguration0.getReconnectBackoffMs();
		assertNull(long0);
	}

	@Test
	public void test31() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getFetchMinBytes();
		archEventListenerConfigurationService_EventListenerConfiguration0.setAutoCommitIntervalMs(integer0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getExcludeInternalTopics());
	}

	@Test
	public void test32() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getFetchMaxWaitMs();
		assertNull(integer0);
	}

	@Test
	public void test33() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = archEventListenerConfigurationService_EventListenerConfiguration0.getConnectionsMaxIdleMs();
		assertNull(long0);
	}

	@Test
	public void test34() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getMaxFailures();
		assertNull(integer0);
	}

	@Test
	public void test35() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getSaslJaasConf();
		assertNull(string0);
	}

	@Test
	public void test36() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getBootstrapServers();
		assertNull(string0);
	}

	@Test
	public void test37() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Boolean boolean0 = archEventListenerConfigurationService_EventListenerConfiguration0.getEnableAutoCommit();
		assertNull(boolean0);
	}

	@Test
	public void test38() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Boolean boolean0 = archEventListenerConfigurationService_EventListenerConfiguration0.getAckOnError();
		assertNull(boolean0);
	}

	@Test
	public void test39() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getSessionTimeoutMs();
		assertNull(integer0);
	}

	@Test
	public void test40() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getSaslServiceName();
		assertNull(string0);
	}

	@Test
	public void test41() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getAutoOffsetReset();
		assertNull(string0);
	}

	@Test
	public void test42() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getFetchMaxBytes();
		assertNull(integer0);
	}

	@Test
	public void test43() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getTopic();
		assertNull(string0);
	}

	@Test
	public void test44() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getClientId();
		assertNull(string0);
	}

	@Test
	public void test45() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getConcurrency();
		assertNull(integer0);
	}

	@Test
	public void test46() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getIsolationLevel();
		assertNull(string0);
	}

	@Test
	public void test47() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getAutoCommitIntervalMs();
		assertNull(integer0);
	}

	@Test
	public void test48() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getSecurityProtocol();
		assertNull(string0);
	}

	@Test
	public void test49() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Boolean boolean0 = archEventListenerConfigurationService_EventListenerConfiguration0.getExcludeInternalTopics();
		assertNull(boolean0);
	}

	@Test
	public void test50() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = archEventListenerConfigurationService_EventListenerConfiguration0.getMetadataMaxAgeMs();
		assertNull(long0);
	}

	@Test
	public void test51() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getMaxPollIntervalMs();
		assertNull(integer0);
	}

	@Test
	public void test52() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getMaxPollRecords();
		assertNull(integer0);
	}

	@Test
	public void test53() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Long long0 = archEventListenerConfigurationService_EventListenerConfiguration0.getRetryBackoffMs();
		archEventListenerConfigurationService_EventListenerConfiguration0.setRetryBackoffMs(long0);
		assertNull(archEventListenerConfigurationService_EventListenerConfiguration0.getMaxPartitionFetchBytes());
	}

	@Test
	public void test54() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0
				.getMaxPartitionFetchBytes();
		assertNull(integer0);
	}

	@Test
	public void test55() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Boolean boolean0 = archEventListenerConfigurationService_EventListenerConfiguration0.getCheckCrcs();
		assertNull(boolean0);
	}

	@Test
	public void test56() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		String string0 = archEventListenerConfigurationService_EventListenerConfiguration0.getGroupId();
		assertNull(string0);
	}

	@Test
	public void test57() throws Throwable {
		ArchEventListenerConfigurationService.EventListenerConfiguration archEventListenerConfigurationService_EventListenerConfiguration0 = new ArchEventListenerConfigurationService.EventListenerConfiguration();
		Integer integer0 = archEventListenerConfigurationService_EventListenerConfiguration0.getRequestTimeoutMs();
		assertNull(integer0);
	}
}

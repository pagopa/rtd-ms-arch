package eu.sia.meda.layers.connector;

import org.junit.Assert;
import org.junit.Test;

public class ConnectorConfigurationExceptionTest {

    private final static String DEFAULT_MESSAGE = "Error loading configuration managers for the connector";

    @Test
    public void testConstructors(){
        ConnectorConfigurationException connectorConfigurationException = new ConnectorConfigurationException();
        Assert.assertNotNull(connectorConfigurationException);
        Assert.assertEquals(DEFAULT_MESSAGE,connectorConfigurationException.getMessage());

        connectorConfigurationException = new ConnectorConfigurationException("testName");
        Assert.assertNotNull(connectorConfigurationException);
        Assert.assertEquals(DEFAULT_MESSAGE+": testName",connectorConfigurationException.getMessage());
    }
}

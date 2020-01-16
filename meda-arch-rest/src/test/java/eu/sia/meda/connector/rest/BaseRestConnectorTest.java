package eu.sia.meda.connector.rest;

import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.connector.medacore.ArchMedaCoreConnectorConfigurationService;
import eu.sia.meda.connector.medacore.JwtTokenProvisioner;
import eu.sia.meda.connector.rest.transformer.request.SimpleRestPostRequestTransformer;
import eu.sia.meda.connector.rest.transformer.response.SimpleRest2xxResponseTransformer;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.http.HttpConnectionPoolSweeperScheduler;
import eu.sia.meda.rest.configuration.ArchRestConfigurationService;
import eu.sia.meda.tracing.config.TracingConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Import;

/** Base class to configure rest unit test */
@Import({ArchRestConfigurationService.class, ArchMedaCoreConnectorConfigurationService.class, JwtTokenProvisioner.class, RestTemplateAutoConfiguration.class, PropertiesManager.class, TracingConfiguration.class, HttpConnectionPoolSweeperScheduler.class, SimpleRestPostRequestTransformer.class, SimpleRest2xxResponseTransformer.class})
public class BaseRestConnectorTest extends BaseSpringTest {
}

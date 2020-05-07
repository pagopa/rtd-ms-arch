package eu.sia.meda.connector.jpa;

import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.connector.jpa.config.ArchJPAConfigurationService;
import eu.sia.meda.connector.jpa.config.JPAConnectorConfig;
import eu.sia.meda.core.properties.PropertiesManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/** In order to use this test, remember to include the jpa connection properties! */
@TestPropertySource(properties = {
        "connectors.jpaConfigurations.connection.mocked:true",
        "connectors.jpaConfigurations.connection.path:postgres/",
})
@Transactional
@Import({ArchJPAConfigurationService.class, PropertiesManager.class, JPAConnectorConfig.class})
public class BaseJpaTest extends BaseSpringTest {
}

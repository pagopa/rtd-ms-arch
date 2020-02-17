package eu.sia.meda.connector.jpa;

import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.connector.jpa.config.ArchJPAConfigurationService;
import eu.sia.meda.core.properties.PropertiesManager;
import org.springframework.context.annotation.Import;

@Import({ArchJPAConfigurationService.class, PropertiesManager.class})
public class BaseJpaTest extends BaseSpringTest {
}

package eu.sia.meda;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Base class to use in order to configure integration test for Spring components
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(properties = {
        "logging.level.root=INFO",
        "logging.level.eu.sia=DEBUG",
        "logging.level.eu.sia.meda.layers.connector.http.HttpConnectionPool=INFO"
})
public abstract class BaseSpringIntegrationTest extends BaseTest {
}

package eu.sia.meda;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/** Base class to use in order to configure unit test for Spring components */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DummyConfiguration.class)
@DirtiesContext
@TestPropertySource(properties = {
        "logging.level.root=INFO",
        "logging.level.eu.sia=DEBUG"
})
public class BaseSpringTest extends BaseTest {
}

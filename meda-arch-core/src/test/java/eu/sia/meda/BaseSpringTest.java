package eu.sia.meda;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/** Base class to use in order to configurate unit test for Spring components */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DummyConfiguration.class)
@TestPropertySource(properties = {
        "logging.level.root=INFO"
})
public class BaseSpringTest extends BaseTest {
}

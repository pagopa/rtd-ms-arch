package eu.sia.meda;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

/** Base class to use in order to configurate unit test */
public class BaseTest {

    @BeforeClass
    public static void setLogLevels() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
    }
}

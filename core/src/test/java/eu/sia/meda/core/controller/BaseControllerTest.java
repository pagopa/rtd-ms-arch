package eu.sia.meda.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.config.ArchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/** Base test for Controller.
 * Remember to define
 * <ol>
 * <li>{@link org.springframework.context.annotation.Import} and {@link org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest} to configure the Spring Context</li>
 * <li>{@link org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc}(secure = false)</li>
 * </ol>
 */
public class BaseControllerTest extends BaseSpringTest {
    @Autowired
    protected MockMvc mvc;
    protected ObjectMapper objectMapper = new ArchConfiguration().objectMapper();
}

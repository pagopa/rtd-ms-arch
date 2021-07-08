package eu.sia.meda.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseSpringIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WithMockUser
public abstract class BaseControllerIntegrationTest extends BaseSpringIntegrationTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
}

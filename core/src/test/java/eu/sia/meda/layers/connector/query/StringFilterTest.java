package eu.sia.meda.layers.connector.query;

import eu.sia.meda.core.controller.BaseControllerTest;
import eu.sia.meda.util.ColoredPrinters;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@Import({DummyController.class})
@WebMvcTest(controllers = DummyController.class)
@AutoConfigureMockMvc(secure = false)
public class StringFilterTest extends BaseControllerTest {

    @Test
    public void testStringFilter() throws Exception {
        ColoredPrinters.PRINT_GREEN.println("sending null..");
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        Assert.assertEquals("{}", result.getResponse().getContentAsString());

        ColoredPrinters.PRINT_GREEN.println("sending equals as default..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField", "value")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        DummyCriteria resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        DummyCriteria expectedCriteria = new DummyCriteria();
        expectedCriteria.setStringField(new StringFilter());
        expectedCriteria.getStringField().setEqualsTo("value");
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField.equalsTo", "value")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & startsWith..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField.equalsTo", "value")
                .param("stringField.startsWith", "prefix")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getStringField().setStartsWith("prefix");
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & startsWith & in..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField.equalsTo", "value")
                .param("stringField.startsWith", "prefix")
                .param("stringField.in", "v1,v2")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getStringField().setIn(Arrays.asList("v1","v2"));
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & startsWith & in & isNull..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField.equalsTo", "value")
                .param("stringField.startsWith", "prefix")
                .param("stringField.in", "v1,v2")
                .param("stringField.isNull", "true")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getStringField().setIsNull(true);
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & startsWith & in & isNull false..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField.equalsTo", "value")
                .param("stringField.startsWith", "prefix")
                .param("stringField.in", "v1,v2")
                .param("stringField.isNull", "false")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getStringField().setIsNull(false);
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);
    }
}

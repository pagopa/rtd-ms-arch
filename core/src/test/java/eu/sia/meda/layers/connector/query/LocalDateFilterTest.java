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

import java.time.LocalDate;
import java.util.Arrays;

@Import({DummyController.class})
@WebMvcTest(controllers = DummyController.class)
@AutoConfigureMockMvc(secure = false)
public class LocalDateFilterTest extends BaseControllerTest {

    @Test
    public void testLocalDateFilter() throws Exception {
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
                .param("localDateField", "2020-03-18")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        DummyCriteria resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        DummyCriteria expectedCriteria = new DummyCriteria();
        expectedCriteria.setLocalDateField(new LocalDateFilter());
        expectedCriteria.getLocalDateField().setEqualsTo(LocalDate.of(2020, 3, 18));
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & greaterThan..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
                .param("localDateField.greaterThan", "2020-03-19")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getLocalDateField().setGreaterThan(LocalDate.of(2020, 3, 19));
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & greaterThan & greaterOrEqual..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
                .param("localDateField.greaterThan", "2020-03-19")
                .param("localDateField.greaterOrEqualTo", "2020-03-20")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getLocalDateField().setGreaterOrEqualTo(LocalDate.of(2020, 3, 20));
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & greaterThan & greaterOrEqual & lessThan..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
                .param("localDateField.greaterThan", "2020-03-19")
                .param("localDateField.greaterOrEqualTo", "2020-03-20")
                .param("localDateField.lessThan", "2020-03-21")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getLocalDateField().setLessThan(LocalDate.of(2020, 3, 21));
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & greaterThan & greaterOrEqual & lessThan & lessOrEqual..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
                .param("localDateField.greaterThan", "2020-03-19")
                .param("localDateField.greaterOrEqualTo", "2020-03-20")
                .param("localDateField.lessThan", "2020-03-21")
                .param("localDateField.lessOrEqualTo", "2020-03-22")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getLocalDateField().setLessOrEqualTo(LocalDate.of(2020, 3, 22));
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & greaterThan & greaterOrEqual & lessThan & lessOrEqual & in..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
                .param("localDateField.greaterThan", "2020-03-19")
                .param("localDateField.greaterOrEqualTo", "2020-03-20")
                .param("localDateField.lessThan", "2020-03-21")
                .param("localDateField.lessOrEqualTo", "2020-03-22")
                .param("localDateField.in", "2020-03-23,2020-03-24")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getLocalDateField().setIn(Arrays.asList(LocalDate.of(2020, 3, 23),LocalDate.of(2020, 3, 24)));
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & greaterThan & greaterOrEqual & lessThan & lessOrEqual & in & isNull..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
                .param("localDateField.greaterThan", "2020-03-19")
                .param("localDateField.greaterOrEqualTo", "2020-03-20")
                .param("localDateField.lessThan", "2020-03-21")
                .param("localDateField.lessOrEqualTo", "2020-03-22")
                .param("localDateField.in", "2020-03-23,2020-03-24")
                .param("LocalDateField.isNull", "true")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getLocalDateField().setIsNull(true);
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);

        ColoredPrinters.PRINT_GREEN.println("sending equals & greaterThan & greaterOrEqual & lessThan & lessOrEqual & in & isNull false..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("localDateField.equalsTo", "2020-03-18")
                .param("localDateField.greaterThan", "2020-03-19")
                .param("localDateField.greaterOrEqualTo", "2020-03-20")
                .param("localDateField.lessThan", "2020-03-21")
                .param("localDateField.lessOrEqualTo", "2020-03-22")
                .param("localDateField.in", "2020-03-23,2020-03-24")
                .param("localDateField.isNull", "false")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.getLocalDateField().setIsNull(false);
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);
    }
}

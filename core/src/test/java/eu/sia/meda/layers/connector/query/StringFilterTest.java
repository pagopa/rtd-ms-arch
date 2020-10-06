package eu.sia.meda.layers.connector.query;

import eu.sia.meda.core.controller.BaseControllerTest;
import eu.sia.meda.util.ColoredPrinters;
import org.assertj.core.util.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

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

        ColoredPrinters.PRINT_GREEN.println("sending startsWith..");
        result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField.startsWith", "prefix")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        expectedCriteria.setStringField(new StringFilter());
        expectedCriteria.getStringField().setStartsWith("prefix");
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria.getStringField().toString(),resultedCriteria.getStringField().toString());
    }

    @Test
    public void testStringFilterToString() throws Exception {
        String expectedFilter = "value && startsWith=prefix% && in=[v1, v2] && isNull=false";

        StringFilter dummyFilter = new StringFilter();
        dummyFilter.setEqualsTo("value");
        dummyFilter.setStartsWith("prefix");
        dummyFilter.setIn(Arrays.asList("v1","v2"));
        dummyFilter.setIsNull(false);

        Assert.assertEquals(expectedFilter,dummyFilter.toString());

        ColoredPrinters.PRINT_GREEN.println("test toString sending equals & startsWith & in & isNull false..");

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/dummy/criteria")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("stringField.equalsTo", "value")
                .param("stringField.startsWith", "prefix")
                .param("stringField.in", "v1,v2")
                .param("stringField.isNull", "false")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        DummyCriteria resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedFilter, resultedCriteria.getStringField().toString());
    }

    @Test
    public void testStartsWith(){
        StringFilter dummyFilter = new StringFilter();

        Assert.assertNull(dummyFilter.getStartsWith());

        dummyFilter.setStartsWith(null);

        Assert.assertNull(dummyFilter.getStartsWith());

        dummyFilter.setStartsWith("prefix");

        Assert.assertFalse(Strings.isNullOrEmpty(dummyFilter.getStartsWith()));
        Assert.assertEquals("prefix%",dummyFilter.getStartsWith());
    }

    @Test
    public void testAccept(){
        StringFilter dummyFilter = new StringFilter();

        Assert.assertTrue(dummyFilter.accept(String.class));
        Assert.assertFalse(dummyFilter.accept(Integer.class));
    }

    @Test
    public void testToPredicate(){
        StringFilter dummyFilter = new StringFilter();
        dummyFilter.setEqualsTo("value");
        dummyFilter.setStartsWith("prefix");
        List<String> valueList = Arrays.asList("v1", "v2");
        dummyFilter.setIn(valueList);
        dummyFilter.setIsNull(true);

        CriteriaBuilder builderMock = Mockito.mock(CriteriaBuilder.class);
        Path pathMock = Mockito.mock(Path.class);

        Predicate predicate = dummyFilter.toPredicate(builderMock, pathMock);
        Assert.assertNull(predicate);
        Mockito.verify(builderMock).equal(Mockito.any(),Mockito.eq("value"));
        Mockito.verify(builderMock).like(Mockito.any(),Mockito.eq("prefix%"));
        Mockito.verify(pathMock).in(Mockito.eq(valueList));
        Mockito.verify(pathMock).isNull();

        dummyFilter.setIsNull(false);

        dummyFilter.toPredicate(builderMock,pathMock);
        Mockito.verify(pathMock).isNotNull();
    }
}

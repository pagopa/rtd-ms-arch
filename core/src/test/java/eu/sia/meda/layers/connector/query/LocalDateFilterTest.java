package eu.sia.meda.layers.connector.query;

import eu.sia.meda.core.controller.BaseControllerTest;
import eu.sia.meda.util.ColoredPrinters;
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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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
        String expectedString ="2020-03-18 && greaterThan=2020-03-19 && greaterOrEqualTo=2020-03-20 && lessThan=2020-03-21 "+
                "&& lessOrEqualTo=2020-03-22 && in=2020-03-23,2020-03-24 && isNull=false";
        expectedCriteria.getLocalDateField().setIsNull(false);
        resultedCriteria = objectMapper.readValue(result.getResponse().getContentAsString(), DummyCriteria.class);
        Assert.assertEquals(expectedCriteria, resultedCriteria);
        Assert.assertEquals(expectedString,resultedCriteria.getLocalDateField().toString());
    }

    @Test
    public void testAccept(){
        LocalDateFilter localDateFilter = new LocalDateFilter();

        Assert.assertTrue(localDateFilter.accept(LocalDate.class));
        Assert.assertFalse(localDateFilter.accept(OffsetDateTime.class));
    }

    @Test
    public void testToPredicate(){
        LocalDateFilter dummyFilter =new LocalDateFilter();
        dummyFilter.setEqualsTo(LocalDate.parse("2020-03-18", DateTimeFormatter.ISO_DATE));
        dummyFilter.setGreaterThan(LocalDate.parse("2020-03-19", DateTimeFormatter.ISO_DATE));
        dummyFilter.setGreaterOrEqualTo(LocalDate.parse("2020-03-20", DateTimeFormatter.ISO_DATE));
        dummyFilter.setLessThan(LocalDate.parse("2020-03-21", DateTimeFormatter.ISO_DATE));
        dummyFilter.setLessOrEqualTo(LocalDate.parse("2020-03-22", DateTimeFormatter.ISO_DATE));
        List<LocalDate> dateList = Arrays.asList(LocalDate.parse("2020-03-23", DateTimeFormatter.ISO_DATE),
                LocalDate.parse("2020-03-24", DateTimeFormatter.ISO_DATE));
        dummyFilter.setIn(dateList);
        dummyFilter.setIsNull(true);


        CriteriaBuilder builderMock = Mockito.mock(CriteriaBuilder.class);
        Path pathMock = Mockito.mock(Path.class);

        Predicate predicate = dummyFilter.toPredicate(builderMock, pathMock);
        Assert.assertNull(predicate);
        Mockito.verify(builderMock).equal(Mockito.any(),
                Mockito.eq(LocalDate.parse("2020-03-18", DateTimeFormatter.ISO_DATE)));
        Mockito.verify(builderMock).greaterThan(Mockito.any(),
                Mockito.eq(LocalDate.parse("2020-03-19", DateTimeFormatter.ISO_DATE)));
        Mockito.verify(builderMock).greaterThanOrEqualTo(Mockito.any(),
                Mockito.eq(LocalDate.parse("2020-03-20", DateTimeFormatter.ISO_DATE)));
        Mockito.verify(builderMock).lessThan(Mockito.any(),
                Mockito.eq(LocalDate.parse("2020-03-21", DateTimeFormatter.ISO_DATE)));
        Mockito.verify(builderMock).lessThanOrEqualTo(Mockito.any(),
                Mockito.eq(LocalDate.parse("2020-03-22", DateTimeFormatter.ISO_DATE)));
        Mockito.verify(pathMock).in(Mockito.eq(dateList));
        Mockito.verify(pathMock).isNull();

        dummyFilter.setIsNull(false);

        dummyFilter.toPredicate(builderMock,pathMock);
        Mockito.verify(pathMock).isNotNull();
    }
}

package eu.sia.meda.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseTest;
import eu.sia.meda.config.ArchConfiguration;
import eu.sia.meda.layers.connector.CrudOperations;
import eu.sia.meda.util.ReflectionUtils;
import eu.sia.meda.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Base test class to test common behavior of {@link CrudController} mocking a {@link CrudOperations} ({@link eu.sia.meda.service.CrudService} or {@link eu.sia.meda.layers.connector.CrudDAO}) */
public abstract class CrudOperationTest<E extends Serializable, K extends Serializable>  extends BaseTest {
    private final Class<E> entityClazz;
    private final Class<K> keyClazz;

    @SuppressWarnings({"unchecked"})
    public CrudOperationTest() {
        this.entityClazz = (Class<E>)ReflectionUtils.getGenericTypeClass(getClass(), 0);
        this.keyClazz = (Class<K>)ReflectionUtils.getGenericTypeClass(getClass(), 1);
    }

    @PostConstruct
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    private MockMvc mvc;
    @SuppressWarnings("FieldCanBeLocal")
    private CrudOperations<E,K> crudOperationsMock;

    private ObjectMapper objectMapper = new ArchConfiguration().objectMapper();

    private List<E> entities;

    /** To retrieve the number of entities to use as ground set */
    protected int getNTestData(){
        return 5;
    }

    protected abstract K getId(E entity);
    protected abstract CrudOperations<E,K> getCrudOperationsMock();
    protected abstract String getBasePath();

    @PostConstruct
    public void configureTest() {
        entities = IntStream.range(0, getNTestData()).mapToObj(this::buildTestEntity).collect(Collectors.toList());

        crudOperationsMock = getCrudOperationsMock();

        BDDMockito.when(crudOperationsMock.findAll(Mockito.any())).thenReturn(entities);
        BDDMockito.when(crudOperationsMock.findById(Mockito.eq(getId(entities.get(4))))).thenReturn(entities.get(4));
        BDDMockito.when(crudOperationsMock.deleteById(Mockito.eq(getId(entities.get(4))))).thenReturn(entities.get(4));
        BDDMockito.when(crudOperationsMock.save(Mockito.eq(entities.get(1)))).thenReturn(entities.get(2));
        BDDMockito.when(crudOperationsMock.update(Mockito.eq(entities.get(2)))).thenReturn(entities.get(3));
    }

    protected E buildTestEntity(int i) {
        try {
            return TestUtils.mockInstance(entityClazz.newInstance(), i);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot instance entity " + entityClazz);
        }
    }

    @Test
    public void testFindAll() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(getBasePath())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        @SuppressWarnings("unchecked") List<E> entitiesResult = Arrays.asList((E[])objectMapper.readValue(
                result.getResponse().getContentAsString(),
                Class.forName(String.format("[L%s;", entityClazz.getName())))
        );
        Assert.assertEquals(entities, entitiesResult);
    }

    @Test
    public void testFindById() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(String.format("%s/%s", getBasePath(), getId(entities.get(4))))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        E promotionsResult = objectMapper.readValue(result.getResponse().getContentAsString(), entityClazz);
        Assert.assertEquals(entities.get(4), promotionsResult);
    }

    @Test
    public void testSave() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(getBasePath())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(entities.get(1)))
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        E promotionsResult = objectMapper.readValue(result.getResponse().getContentAsString(), entityClazz);
        Assert.assertEquals(entities.get(2), promotionsResult);
    }

    @Test
    public void testUpdate() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .put(getBasePath())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(entities.get(2)))
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        E promotionsResult = objectMapper.readValue(result.getResponse().getContentAsString(), entityClazz);
        Assert.assertEquals(entities.get(3), promotionsResult);
    }

    @Test
    public void testDelete() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .delete(String.format("%s/%s", getBasePath(), getId(entities.get(4))))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        E promotionsResult = objectMapper.readValue(result.getResponse().getContentAsString(), entityClazz);
        Assert.assertEquals(entities.get(4), promotionsResult);
    }

}
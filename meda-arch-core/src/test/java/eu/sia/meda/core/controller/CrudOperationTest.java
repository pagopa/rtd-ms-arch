package eu.sia.meda.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseTest;
import eu.sia.meda.config.ArchConfiguration;
import eu.sia.meda.core.assembler.BaseResourceAssemblerSupport;
import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.layers.connector.CrudOperations;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import eu.sia.meda.util.ReflectionUtils;
import eu.sia.meda.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Base test class to test common behavior of {@link CrudController} mocking a {@link CrudOperations} ({@link eu.sia.meda.service.CrudService} or {@link eu.sia.meda.layers.connector.CrudDAO}) */
public abstract class CrudOperationTest<R extends BaseResource, E extends Serializable, K extends Serializable, C extends CriteriaQuery<E>>  extends BaseTest {
    protected final Class<R> resourceClazz;
    private final Class<E> entityClazz;
    private final Class<K> keyClazz;
    private final Class<C> criteriaQueryClazz;

    @SuppressWarnings({"unchecked"})
    public CrudOperationTest() {
        this.resourceClazz = (Class<R>)ReflectionUtils.getGenericTypeClass(getClass(), 0);
        this.entityClazz = (Class<E>)ReflectionUtils.getGenericTypeClass(getClass(), 1);
        this.keyClazz = (Class<K>)ReflectionUtils.getGenericTypeClass(getClass(), 2);
        this.criteriaQueryClazz = (Class<C>)ReflectionUtils.getGenericTypeClass(getClass(), 3);
    }

    @PostConstruct
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    protected MockMvc mvc;
    @SuppressWarnings("FieldCanBeLocal")
    protected CrudOperations<E,K> crudOperationsMock;
    private BaseResourceAssemblerSupport<E,R> resourceAssembler;

    protected ObjectMapper objectMapper = new ArchConfiguration().objectMapper();

    protected List<E> entities;

    /** To retrieve the number of entities to use as ground set */
    protected int getNTestData(){
        return 5;
    }

    protected abstract K getId(E entity);
    protected abstract CrudOperations<E,K> getCrudOperationsMock();
    protected abstract BaseResourceAssemblerSupport<E,R> getResourceAssemblerSpy();
    protected abstract String getBasePath();

    @PostConstruct
    public void configureTest() throws JsonProcessingException {
        entities = IntStream.range(0, getNTestData()).mapToObj(this::buildTestEntity).collect(Collectors.toList());
        for (int i = 0; i < entities.size(); i++) {
            Assert.assertEquals(getExpectedJson(entities.get(i)), objectMapper.writeValueAsString(entities.get(i)));
        }

        crudOperationsMock = getCrudOperationsMock();
        resourceAssembler = getResourceAssemblerSpy();

        BDDMockito.doReturn(new PageImpl<>(entities, PageRequest.of(0,entities.size()), entities.size())).when(crudOperationsMock).findAll(Mockito.any(), Mockito.any());
        BDDMockito.doReturn(Optional.of(entities.get(4))).when(crudOperationsMock).findById(Mockito.eq(getId(entities.get(4))));
        BDDMockito.doReturn(entities.get(2)).when(crudOperationsMock).save(Mockito.eq(entities.get(1)));
        BDDMockito.doReturn(entities.get(2)).when(crudOperationsMock).update(Mockito.eq(entities.get(1)));

        BDDMockito.doReturn(entities.get(3)).when(crudOperationsMock).update(Mockito.eq(entities.get(2)));
    }

    /** To override in order to customize the input entity. Remember to override also {@link #getExpectedJson(E)} */
    protected E buildTestEntity(int bias) {
        try {
            return TestUtils.mockInstance(entityClazz.newInstance(), bias);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot instance entity " + entityClazz);
        }
    }

    /** To override in order to match the input entity when overriding {@link #buildTestEntity(int)} */
    protected String getExpectedJson(E entity){
        try {
            return objectMapper.copy().configure(MapperFeature.USE_ANNOTATIONS, false).writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Assert.fail("Cannot build expected json using default strategy");
            return null;
        }
    }

    @Test
    public void testFindAll() throws Exception {
        C criteriaQuery = getCriteriaQuery();
        MultiValueMap<String, String> criteriaMap = new LinkedMultiValueMap<>();
        org.springframework.util.ReflectionUtils.doWithFields(criteriaQueryClazz, f->{
            f.setAccessible(true);
            Object value = f.get(criteriaQuery);
            if(value!=null){
                criteriaMap.put(f.getName(), Collections.singletonList(value.toString()));
            }
        });

        if(criteriaMap.size()==0){
            throw new IllegalStateException("Provided at least 1 criteria filter");
        }

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(getBasePath())
                .param("page", "0")
                .param("size", getNTestData()+"")
                .params(criteriaMap)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        PagedResources<R> pageResult = objectMapper.readValue(
                transformEmbeddedAfterLinks(transformLinks(result.getResponse().getContentAsString())),
                objectMapper.getTypeFactory().constructParametricType(PagedResources.class, resourceClazz)
        );
        Iterator<R> resultIterator = pageResult.iterator();
        for (E entity : entities) {
            BDDMockito.verify(resourceAssembler).toResource(Mockito.eq(entity));
            compare(entity, resultIterator.next());
        }
    }

    private C getCriteriaQuery() {
        try {
            return TestUtils.mockInstance(criteriaQueryClazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException((e));
        }
    }

    protected void compare(E entity, R resource){
        TestUtils.reflectionEqualsByName(entity, resource);
    }

    protected String transformLinks(String json) {
        return json.replaceAll("\"_links\":\\{\"self\":\\{([^}]+\\})\\}", "\"links\":[{\"rel\":\"self\",$1]");
    }

    private String transformEmbeddedAfterLinks(String json) {
        return json.replaceAll("\"_embedded\":\\{\"[^\"]+\":(.*)\\},\"links", "\"content\":$1,\"links");
    }

    @Test
    public void testFindById() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(String.format("%s/%s", getBasePath(), getId(entities.get(4))))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        R promotionsResult = objectMapper.readValue(transformLinks(result.getResponse().getContentAsString()), resourceClazz);
        compare(entities.get(4), promotionsResult);

        BDDMockito.verify(resourceAssembler).toResource(Mockito.eq(entities.get(4)));
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

        R promotionsResult = objectMapper.readValue(transformLinks(result.getResponse().getContentAsString()), resourceClazz);
        compare(entities.get(2), promotionsResult);

        BDDMockito.verify(resourceAssembler).toResource(Mockito.eq(entities.get(2)));
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

        R promotionsResult = objectMapper.readValue(transformLinks(result.getResponse().getContentAsString()), resourceClazz);
        compare(entities.get(3), promotionsResult);

        BDDMockito.verify(resourceAssembler).toResource(Mockito.eq(entities.get(3)));
    }

    @Test
    public void testDelete() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .delete(String.format("%s/%s", getBasePath(), getId(entities.get(4))))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        BDDMockito.verify(getCrudOperationsMock()).deleteById(Mockito.eq(getId(entities.get(4))));
    }

}
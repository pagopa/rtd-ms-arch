package eu.sia.meda.assembler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.sia.meda.BaseTest;
import eu.sia.meda.config.ArchConfiguration;
import eu.sia.meda.core.assembler.BaseResourceAssemblerSupport;
import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.util.ReflectionUtils;
import eu.sia.meda.util.TestUtils;

public abstract class BaseResourceAssemblerTest<A extends BaseResourceAssemblerSupport<E, R>, R extends BaseResource, E> extends BaseTest {

    protected ObjectMapper objectMapperStrict;
    protected ObjectMapper objectMapperBase;
    private Class<A> resourceAssemblerClass;
    private Class<R> resourceClass;
    private Class<E> entityClass;

    private final Set<String> excludedFields;

    public BaseResourceAssemblerTest() {

        ArchConfiguration archConfiguration = new ArchConfiguration();
        this.objectMapperStrict = archConfiguration.objectMapperStrict();
        this.objectMapperBase = archConfiguration.objectMapper();
        //noinspection unchecked
        this.entityClass = (Class<E>) ReflectionUtils.getGenericTypeClass(getClass(), 2);
        //noinspection unchecked
        this.resourceClass = (Class<R>) ReflectionUtils.getGenericTypeClass(getClass(), 1);
        //noinspection unchecked
        this.resourceAssemblerClass = (Class<A>) ReflectionUtils.getGenericTypeClass(getClass(), 0);

        excludedFields = new HashSet<>();
        excludedFields.addAll(getExcludedFields());
    }


    @Test
    public void toResourceTest() throws IOException, IllegalAccessException, InstantiationException {
        E entity = buildEntity();
        org.springframework.util.ReflectionUtils.doWithFields(entityClass,
                f -> {
                    f.setAccessible(true);
                    Assert.assertNotNull("The field "+f.getName()+" of the input entity is null! Populate it in order to check the correct copy into resource, or add it to ignored fields", f.get(entity));
                },
                f -> !excludedFields.contains(f.getName()));
        R resource = getResourceAssembler().toResource(entity);

        String resourceStr = this.objectMapperStrict.writeValueAsString(resource);

        Assert.assertNotNull(resource);
        Assert.assertNotNull(resourceStr);
        Assert.assertNotNull(resource.getLinks());

        String outputStr = this.getJsonEntity(resource);

        if (StringUtils.isNotEmpty(outputStr)) {
            R eventResourceDeserialized = this.objectMapperStrict.readValue(outputStr, this.resourceClass);
            TestUtils.reflectionEqualsByName(resource, eventResourceDeserialized);
        }

        E deserializedAsEntity = this.objectMapperBase.readValue(resourceStr, this.entityClass);
        checkEntity(entity, deserializedAsEntity);
    }

    /** To check the provided entity with the result of the deserialization of the resource obtained from it */
    protected void checkEntity(E entity, E deserializedAsEntity) {
        TestUtils.reflectionEqualsByName(entity, deserializedAsEntity, excludedFields.toArray(new String[0]));
    }

    protected A getResourceAssembler() throws InstantiationException, IllegalAccessException {
        return this.resourceAssemblerClass.newInstance();
    }

    protected E buildEntity() throws InstantiationException, IllegalAccessException {
        return TestUtils.mockInstance(this.entityClass.newInstance());
    }

    /** Not mandatory check to verify that the resource could be successfully deserialized */
    protected String getJsonEntity(R resource) throws JsonProcessingException {
        return null;
    }

    /** Field to ignore during check */
    protected Set<String> getExcludedFields(){
        return Collections.emptySet();
    }
}

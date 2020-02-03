package eu.sia.meda.assembler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.BaseTest;
import eu.sia.meda.config.ArchConfiguration;
import eu.sia.meda.core.assembler.BaseResourceAssemblerSupport;
import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.domain.model.BaseEntity;
import eu.sia.meda.domain.model.be4fe.Residency;
import eu.sia.meda.util.ReflectionUtils;
import eu.sia.meda.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public abstract class BaseResourceAssemblerTest<E extends BaseResourceAssemblerSupport<K, F>, F extends BaseResource, K extends BaseEntity> extends BaseTest {

    protected ObjectMapper om;
    private Class<E> resourceAssemblerClass;
    private Class<F> resourceClass;
    private Class<K> entityClass;

    public BaseResourceAssemblerTest() {

        ArchConfiguration archConfiguration = new ArchConfiguration();
        this.om = archConfiguration.objectMapperStrict();
        this.entityClass = (Class<K>)ReflectionUtils.getGenericTypeClass(getClass(), 2);
        this.resourceClass = (Class<F>)ReflectionUtils.getGenericTypeClass(getClass(), 1);
        this.resourceAssemblerClass = (Class<E>) ReflectionUtils.getGenericTypeClass(getClass(), 0);
    }


    @Test
    public void toResourceTest() throws IOException, IllegalAccessException, InstantiationException {

        K event = TestUtils.mockInstance(this.entityClass.newInstance());

        F resource = this.resourceAssemblerClass.newInstance().toResource(event);

        String resourceStr = this.om.writeValueAsString(resource);

        Assert.assertNotNull(resource);
        Assert.assertNotNull(resourceStr);
        Assert.assertNotNull(resource.getLinks());

        String outputStr = this.getJsonEntity(resource);

        Assert.assertEquals(resourceStr, outputStr);

        F eventResourceDeserialized = this.om.readValue(outputStr, this.resourceClass);

        TestUtils.reflectionEqualsByName(resource, eventResourceDeserialized);

    }

    /**
     * Override to build the String json of the Resouce
     * @param resource
     * @return
     */
    public abstract String getJsonEntity(F resource) throws JsonProcessingException;
}

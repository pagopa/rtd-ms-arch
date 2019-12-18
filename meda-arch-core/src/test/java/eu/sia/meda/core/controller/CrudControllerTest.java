package eu.sia.meda.core.controller;

import eu.sia.meda.DummyConfiguration;
import eu.sia.meda.service.CrudService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

/** Base test class to unit test common behavior of {@link CrudController} */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DummyConfiguration.class)
@TestPropertySource(properties = {
        "logging.level.root=INFO"
})
public abstract class CrudControllerTest<E extends Serializable, K extends Serializable>  extends CrudOperationTest<E,K> {
    @Override
    protected CrudService<E, K> getCrudOperationsMock() {
        return getCrudServiceMock();
    }

    protected abstract CrudService<E,K> getCrudServiceMock();
}
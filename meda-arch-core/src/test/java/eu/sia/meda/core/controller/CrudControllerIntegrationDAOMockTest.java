package eu.sia.meda.core.controller;

import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.layers.connector.CrudDAO;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

/**
 * Base test class to test common behavior of {@link CrudController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(properties = {
        "logging.level.root=INFO"
})
public abstract class CrudControllerIntegrationDAOMockTest<R extends BaseResource, E extends Serializable, K extends Serializable, C extends CriteriaQuery<? super E>> extends CrudOperationTest<R, E, K, C> {
    @Override
    protected CrudDAO<E, K> getCrudOperationsMock() {
        return getCrudDAOMock();
    }

    protected abstract CrudDAO<E, K> getCrudDAOMock();
}
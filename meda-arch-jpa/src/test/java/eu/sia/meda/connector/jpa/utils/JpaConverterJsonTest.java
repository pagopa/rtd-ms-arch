package eu.sia.meda.connector.jpa.utils;

import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.connector.jpa.config.ArchJPAConfigurationService;
import eu.sia.meda.connector.jpa.config.JPAConnectorConfig;
import eu.sia.meda.connector.jpa.model.JsonConverterEntity;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.domain.model.be4fe.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@TestPropertySource(properties = {
        "connectors.jpaConfigurations.connection.mocked:true",
        "connectors.jpaConfigurations.connection.path:testjsonconv/",
        "connectors.jpaConfigurations.connection.files:create_testjsonconv.sql",
        "connectors.jpaConfigurations.connection.url:${POSTGRES_CONN_URL:jdbc:postgresql://${POSTGRES_HOST:localhost}:${POSTGRES_PORT:5432}/${POSTGRES_SCHEMA:SCONTI}}",
        "connectors.jpaConfigurations.connection.driver-class-name:org.postgresql.Driver",
        "connectors.jpaConfigurations.connection.username:${POSTGRES_USERNAME:postgres}",
        "connectors.jpaConfigurations.connection.password:${POSTGRES_PASSWORD:admin}",
        "connectors.jpaConfigurations.connection.connectionPoolSize:${POSTGRES_POOLSIZE:5}",
        "connectors.jpaConfigurations.connection.timeout:${POSTGRES_TIMEOUT:6000}",
        "connectors.jpaConfigurations.connection.hibernateDialect:org.hibernate.dialect.PostgreSQL95Dialect"
})
@Import({ArchJPAConfigurationService.class, PropertiesManager.class,JPAConnectorConfig.class})
public class JpaConverterJsonTest extends BaseSpringTest {

    @TestConfiguration
    public static class JpaConfig extends JPAConnectorConfig {}

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    @Rollback
    @Transactional
    public void convertToDatabaseColumnTest(){

        JsonConverterEntity entity = new JsonConverterEntity();
        entity.setId(1);

        User u = new User();
        u.setName("user");
        entity.setObj(u);

        entityManager.persist(entity);

        entityManager.flush();
        entityManager.clear();

        JsonConverterEntity entityfound = entityManager.find(JsonConverterEntity.class, entity.getId());

        Assert.assertNotNull(entityfound);
        Assert.assertEquals(entity.getId(), entityfound.getId());

        List<String> list2 = entityManager.createNativeQuery("SELECT OBJ FROM TESTJSONCONV").getResultList();

        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertNotNull(list2.get(0));
        Assert.assertEquals("["+User.class.getName()+"]{\"name\":\"user\"}", list2.get(0));

        //TODO: Testare il campo a null, cosa succede?

    }
}


package eu.sia.meda.connector.jpa.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import eu.sia.meda.BaseSpringTest;
import eu.sia.meda.connector.jpa.config.ArchJPAConfigurationService;
import eu.sia.meda.connector.jpa.config.JPAConnectorConfig;
import eu.sia.meda.connector.jpa.model.DummyEntity;
import eu.sia.meda.connector.jpa.model.User;
import eu.sia.meda.core.properties.PropertiesManager;

@TestPropertySource(properties = {
        "connectors.jpaConfigurations.connection.mocked:true",
        "connectors.jpaConfigurations.connection.path:testDummyEntity/",
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

        DummyEntity entity = new DummyEntity();
        entity.setId(1);

        User u = new User();
        u.setName("user");
        entity.setObj(u);

        entityManager.persist(entity);

        entityManager.flush();
        entityManager.clear();

        DummyEntity entityfound = entityManager.find(DummyEntity.class, entity.getId());

        Assert.assertNotNull(entityfound);
        Assert.assertEquals(entity.getId(), entityfound.getId());

        List<?> list2 = entityManager.createNativeQuery("SELECT OBJ FROM DUMMYTABLE").getResultList();

        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertNotNull(list2.get(0));
        //Assert.assertEquals("("+User.class.getName()+"){\"name\":\"user\"}", list2.get(0));
        Assert.assertEquals("{\"className\":\""+User.class.getName()+"\",\"object\":{\"name\":\"user\"}}", list2.get(0));

        DummyEntity entity2 = new DummyEntity();
        entity2.setId(2);

        entityManager.persist(entity2);
        entityManager.flush();
        entityManager.clear();

        List<?> list3 = entityManager.createNativeQuery("SELECT OBJ FROM DUMMYTABLE WHERE ID=2").getResultList();

        Assert.assertNotNull(list3);
        Assert.assertEquals(1, list3.size());
        Assert.assertNull(list3.get(0));


        DummyEntity entity3 = new DummyEntity();
        ArrayList<Object> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("u1");
        users.add(u1);
        User u2 = new User();
        u2.setName("u2");
        users.add(u2);
        entity3.setObj(users);
        entity3.setId(3);

        entityManager.persist(entity3);
        entityManager.flush();
        entityManager.clear();

        List<?> list4 = entityManager.createNativeQuery("SELECT OBJ FROM DUMMYTABLE WHERE ID=3").getResultList();

        Assert.assertNotNull(list4);
        Assert.assertEquals(1, list4.size());
        //Assert.assertEquals("("+User.class.getName()+")("+ArrayList.class.getName()+")[{\"name\":\"u1\"},{\"name\":\"u2\"}]", list4.get(0));
        Assert.assertEquals("{\"className\":\""+User.class.getName()+"\",\"collectionName\":\""+ArrayList.class.getName()+"\",\"object\":[{\"name\":\"u1\"},{\"name\":\"u2\"}]}", list4.get(0));
    }
}


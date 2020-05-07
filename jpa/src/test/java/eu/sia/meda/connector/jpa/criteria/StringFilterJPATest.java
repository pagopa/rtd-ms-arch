package eu.sia.meda.connector.jpa.criteria;

import eu.sia.meda.connector.jpa.BaseJpaTest;
import eu.sia.meda.connector.jpa.dao.DummyDAO;
import eu.sia.meda.connector.jpa.model.DummyEntity;
import eu.sia.meda.layers.connector.query.StringFilter;
import eu.sia.meda.util.ColoredPrinters;
import eu.sia.meda.util.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@TestPropertySource(properties = {
        "connectors.jpaConfigurations.connection.path:testDummyEntity/",
        "connectors.jpaConfigurations.connection.url:jdbc:postgresql://localhost:5432/SCONTI",
        "connectors.jpaConfigurations.connection.driver-class-name:org.postgresql.Driver",
        "connectors.jpaConfigurations.connection.username:postgres",
        "connectors.jpaConfigurations.connection.password:PASSWORD",
        "connectors.jpaConfigurations.connection.connectionPoolSize:5",
        "connectors.jpaConfigurations.connection.timeout:6000",
        "connectors.jpaConfigurations.connection.hibernateDialect:org.hibernate.dialect.PostgreSQL95Dialect",
        "logging.level.org.hibernate.SQL=DEBUG",
        "logging.level.org.hibernate.type=TRACE"
})
@Transactional
@ActiveProfiles(DummyDAO.SPRING_PROFILE_DUMMY_ENTITY)
public class StringFilterJPATest extends BaseJpaTest {
    private static final int N=10;

    @Autowired
    private DummyDAO dao;

    @Before
    public void makeTestData(){
        for(int i=0;i<N;i++){
            DummyEntity e = TestUtils.mockInstance(new DummyEntity(), i);
            if(i%2==0){
                e.setObj("STARTING_" + i);
            } else {
                e.setObj("NOT_STARTING_" + i);
            }
            dao.save(e);
        }
    }

    @Test
    public void testStringFilter(){
        DummyQueryCriteria criteria = null;

        ColoredPrinters.PRINT_GREEN.println("Testing null...");
        Page<DummyEntity> e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing empty...");
        criteria=new DummyQueryCriteria();
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing not setted...");
        criteria.setObj(new StringFilter());
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing equals to none...");
        criteria.getObj().setEqualsTo("DUMMY");
        e = dao.findAll(criteria, null);
        Assert.assertEquals(0, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing equals...");
        criteria.getObj().setEqualsTo("STARTING_0");
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing equals & startsWith...");
        criteria.getObj().setStartsWith("STARTING_");
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing equals | startsWith...");
        criteria.getObj().setAndAggregate(false);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N/2, e.getContent().size());
        criteria.getObj().setAndAggregate(true);

        ColoredPrinters.PRINT_GREEN.println("Testing equals & startsWith & in...");
        criteria.getObj().setIn(Collections.singleton("STARTING_0"));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing startsWith & in...");
        criteria.getObj().setEqualsTo(null);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing startsWith...");
        criteria.getObj().setIn(null);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N/2, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing startsWith none...");
        criteria.getObj().setStartsWith("DUMMY");
        e = dao.findAll(criteria, null);
        Assert.assertEquals(0, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing in...");
        criteria.getObj().setStartsWith(null);
        criteria.getObj().setIn(Arrays.asList("STARTING_0", "STARTING_1"));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing in none...");
        criteria.getObj().setStartsWith(null);
        criteria.getObj().setIn(Collections.singletonList("DUMMY"));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(0, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing null true...");
        criteria.getObj().setIn(null);
        criteria.getObj().setIsNull(true);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(0, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing null false...");
        criteria.getObj().setIn(null);
        criteria.getObj().setIsNull(false);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());
    }
}

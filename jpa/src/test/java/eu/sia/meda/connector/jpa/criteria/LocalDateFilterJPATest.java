package eu.sia.meda.connector.jpa.criteria;

import eu.sia.meda.connector.jpa.BaseJpaTest;
import eu.sia.meda.connector.jpa.dao.DummyDAO;
import eu.sia.meda.connector.jpa.model.DummyEntity;
import eu.sia.meda.layers.connector.query.LocalDateFilter;
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

import java.time.LocalDate;
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
public class LocalDateFilterJPATest extends BaseJpaTest {
    private static final int N=10;

    @Autowired
    private DummyDAO dao;

    private LocalDate baseDateValue = LocalDate.now();

    @Before
    public void makeTestData(){
        for(int i=0;i<N;i++){
            DummyEntity e = TestUtils.mockInstance(new DummyEntity(), i);
            e.setLocalDate(baseDateValue.plusDays(i));
            dao.save(e);
        }


        DummyEntity withNull = TestUtils.mockInstance(new DummyEntity());
        withNull.setId(N+1);
        withNull.setLocalDate(null);
        dao.save(withNull);
    }

    @Test
    public void testLocalDateFilter(){
        DummyQueryCriteria criteria = null;

        ColoredPrinters.PRINT_GREEN.println("Testing null...");
        Page<DummyEntity> e = dao.findAll(criteria, null);
        Assert.assertEquals(N+1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing empty...");
        criteria=new DummyQueryCriteria();
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N+1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing not setted...");
        criteria.setLocalDate(new LocalDateFilter());
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N+1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing equals to none...");
        criteria.getLocalDate().setEqualsTo(baseDateValue.minusDays(1));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(0, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing greaterThan...");
        criteria=new DummyQueryCriteria();
        criteria.setLocalDate(new LocalDateFilter());
        criteria.getLocalDate().setGreaterThan(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N-1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing greaterThan & equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue.plusDays(1));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing greaterThan | equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue);
        criteria.getLocalDate().setAndAggregate(false);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing greaterOrEqualTo...");
        criteria=new DummyQueryCriteria();
        criteria.setLocalDate(new LocalDateFilter());
        criteria.getLocalDate().setGreaterOrEqualTo(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing greaterOrEqualTo & equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing lessThan...");
        criteria=new DummyQueryCriteria();
        criteria.setLocalDate(new LocalDateFilter());
        criteria.getLocalDate().setLessThan(baseDateValue.plusDays(N-1));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N-1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing lessThan & equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing lessThan | equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue.plusDays(N-1));
        criteria.getLocalDate().setAndAggregate(false);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing lessOrEqualTo...");
        criteria=new DummyQueryCriteria();
        criteria.setLocalDate(new LocalDateFilter());
        criteria.getLocalDate().setLessOrEqualTo(baseDateValue.plusDays(N-1));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing lessOrEqualTo & equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing in...");
        criteria=new DummyQueryCriteria();
        criteria.setLocalDate(new LocalDateFilter());
        criteria.getLocalDate().setIn(Arrays.asList(baseDateValue, baseDateValue.plusDays(1), baseDateValue.minusDays(1)));
        e = dao.findAll(criteria, null);
        Assert.assertEquals(2, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing in & equals...");
        criteria.getLocalDate().setEqualsTo(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing null true...");
        criteria=new DummyQueryCriteria();
        criteria.setLocalDate(new LocalDateFilter());
        criteria.getLocalDate().setIsNull(true);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing null false...");
        criteria=new DummyQueryCriteria();
        criteria.setLocalDate(new LocalDateFilter());
        criteria.getLocalDate().setIsNull(false);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(N, e.getContent().size());

        ColoredPrinters.PRINT_GREEN.println("Testing null false...");
        criteria.getLocalDate().setEqualsTo(baseDateValue);
        e = dao.findAll(criteria, null);
        Assert.assertEquals(1, e.getContent().size());
    }
}

package eu.sia.meda.service;

import eu.sia.meda.layers.connector.CrudDAO;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.MockitoAnnotations.initMocks;

public class DummyCrudServiceTest {

    private CrudService<DummyEntity,String> service;
    @Mock
    private CrudDAO<DummyEntity,String> daoMock;

    @BeforeEach
    public void init(){
        initMocks(this);
        service = new DummyCrudServiceImpl<>(daoMock);
    }

    @Test
    public void testFindAll(){
        CriteriaQuery criteriaQueryMock = Mockito.mock(CriteriaQuery.class);

        Mockito.when(daoMock.findAll(Mockito.eq(criteriaQueryMock),Mockito.any())).thenReturn(new PageImpl(Collections.emptyList()));

        Page result = service.findAll(criteriaQueryMock, null);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.hasContent());
        Mockito.verify(daoMock).findAll(Mockito.eq(criteriaQueryMock),Mockito.any());
    }

    @Test
    public void testCount(){
        service.count();

        Mockito.verify(daoMock).count();
    }

    @Test
    public void testFindById(){
        String id = "testId";

        Mockito.when(daoMock.findById(Mockito.eq(id))).thenReturn(Optional.of(new DummyEntity()));

        Optional<DummyEntity> result = service.findById(id);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Mockito.verify(daoMock).findById(Mockito.eq(id));
    }

    @Test
    public void testSave(){
        service.save(new DummyEntity("testId"));

        Mockito.verify(daoMock).save(Mockito.argThat(e->"testId".equals(e.getId())));
    }

    @Test
    public void testUpdate(){
        try {
            service.update(null);
            Assert.fail("expected exception");
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }

        DummyEntity entity = new DummyEntity();

        try {
            service.update(entity);
            Assert.fail("expected exception");
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }

        entity.setId("testId");

        try {
            service.update(entity);
            Assert.fail("expected exception");
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalStateException);
            Mockito.verify(daoMock).findById(Mockito.eq(entity.getId()));
        }

        Mockito.when(daoMock.findById("testId")).thenReturn(Optional.of(entity));
        service.update(entity);

        Mockito.verify(daoMock).update(Mockito.eq(entity));
    }

    @Test
    public void testDeleteById(){
        service.deleteById("testId");

        Mockito.verify(daoMock).deleteById(Mockito.eq("testId"));
    }
}

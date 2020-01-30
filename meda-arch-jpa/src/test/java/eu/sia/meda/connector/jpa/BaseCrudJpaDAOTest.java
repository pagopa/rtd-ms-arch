package eu.sia.meda.connector.jpa;

import eu.sia.meda.BaseSpringIntegrationTest;
import eu.sia.meda.util.ReflectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseCrudJpaDAOTest<D extends CrudJpaDAO<E,K> ,E extends Serializable, K extends Serializable> extends BaseSpringIntegrationTest {

    protected final Class<D> daoClass;
    private final Class<E> entityClass;
    private final Class<K> keyClass;

    @SuppressWarnings({"unchecked"})
    public BaseCrudJpaDAOTest(){
        this.daoClass = (Class<D>) ReflectionUtils.getGenericTypeClass(getClass(), 0);
        this.entityClass = (Class<E>)ReflectionUtils.getGenericTypeClass(getClass(), 1);
        this.keyClass = (Class<K>)ReflectionUtils.getGenericTypeClass(getClass(), 2);
    }
    
    protected abstract D getDao();

    protected abstract void setId(E entity, K id);

    protected abstract K getId(E entity);

    /**
     * This function returns an id sorted by bias
     */
    protected abstract K buildId(int bias);

    protected String getIdName(){
        return "id";
    }

    protected E getEntity() throws IllegalAccessException, InstantiationException {
        E e = entityClass.newInstance();
        this.setId(e, this.getStoredId());
        return e;
    }

    /**
     * The Id has to be just stored before test
     * and has to be smaller than each next id {@link #getNextId()}
     */
    protected K getStoredId(){
        return this.buildId(1);
    }

    protected E getEntityToSave() throws InstantiationException, IllegalAccessException {
        E e = this.getEntity();
        this.setId(e, this.getNextId());
        return e;
    }

    /**
     * This function return an id greater than {@link #getStoredId()}
     */
    protected K getNextId(){
        return this.buildId(2);
    }

    protected abstract void alterEntityToUpdate(E entity);


    @Rollback
    @Test
    @Transactional
    public void findAll(){
        List<E> list = getDao().findAll();
        Assert.assertNotNull(list);
    }

    @Rollback
    @Test
    @Transactional
    public void findAllPageable() throws IllegalAccessException, InstantiationException {

        PageRequest pageable = PageRequest.of(0,1, Sort.by(this.getIdName()));
        getDao().save(getEntityToSave());

        Page<E> list1 =  getDao().findAll(pageable);

        Assert.assertEquals(getDao().count(), list1.getTotalElements());
        Assert.assertEquals(1, list1.getPageable().getPageSize());
        Assert.assertEquals(0, list1.getPageable().getPageNumber());
        Assert.assertNotNull(list1.getContent());
        Assert.assertEquals(1, list1.getContent().size());
        Assert.assertEquals(this.getEntity(), list1.getContent().get(0));

        pageable = PageRequest.of(1,1, Sort.by(this.getIdName()));

        Page<E> list2 = getDao().findAll(pageable);
        Assert.assertNotNull(list2.getContent());
        Assert.assertEquals(1, list2.getContent().size());
        Assert.assertEquals(this.getEntityToSave(), list2.getContent().get(0));
    }




    @Rollback
    @Test
    @Transactional
    public void deleteById() {

        getDao().deleteById(this.getStoredId());

        Optional<E> optionalE = getDao().findById(this.getStoredId());

        Assert.assertFalse(optionalE.isPresent());

    }

    @Rollback
    @Test
    @Transactional
    public void findById()  {

        Optional<E> optionalE = getDao().findById(this.getStoredId());
        Assert.assertTrue(optionalE.isPresent());
        Assert.assertNotNull(optionalE.get());

    }

    @Rollback
    @Test
    @Transactional
    public void save() throws IllegalAccessException, InstantiationException {

        Assert.assertNotNull(getDao().save(this.getEntityToSave()));
        Optional<E> optional = getDao().findById(this.getId(this.getEntityToSave()));
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(this.getEntityToSave(), optional.get());
    }


    @Rollback
    @Test
    @Transactional
    public void update() {

        Optional<E> optional1 = getDao().findById(this.getStoredId());
        Assert.assertTrue(optional1.isPresent());
        E entity = optional1.get();
        this.alterEntityToUpdate(entity);
        E entityUpdated = getDao().update(entity);

        Optional<E> optional2 = getDao().findById(this.getId(entity));
        Assert.assertTrue(optional2.isPresent());

        Assert.assertEquals(entity, entityUpdated);
        Assert.assertEquals(entity, optional2.get());
    }

    @Rollback
    @Test
    @Transactional
    public void count(){
        List<E> list = getDao().findAll();
        Assert.assertEquals(list.size(), getDao().count());
    }

    @Rollback
    @Test
    @Transactional
    public void deleteAll(){
        getDao().deleteAll();
        Assert.assertEquals(0, getDao().count());
    }

}

package eu.sia.meda.connector.jpa;

import eu.sia.meda.BaseSpringIntegrationTest;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import eu.sia.meda.util.ColoredPrinters;
import eu.sia.meda.util.ReflectionUtils;
import eu.sia.meda.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public abstract class BaseCrudJpaDAOTest<D extends CrudJpaDAO<E, K>, E extends Serializable, K extends Serializable> extends BaseSpringIntegrationTest {

    protected final Class<D> daoClass;
    private final Class<E> entityClass;
    private final Class<K> keyClass;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings({"unchecked"})
    public BaseCrudJpaDAOTest() {
        this.daoClass = (Class<D>) ReflectionUtils.getGenericTypeClass(getClass(), 0);
        this.entityClass = (Class<E>) ReflectionUtils.getGenericTypeClass(getClass(), 1);
        this.keyClass = (Class<K>) ReflectionUtils.getGenericTypeClass(getClass(), 2);

        CriteriaQuery<E> queryCriteria = getMatchAlreadySavedCriteria();
        org.springframework.util.ReflectionUtils.doWithFields(queryCriteria.getClass(), f -> {
            Field entityField = org.springframework.util.ReflectionUtils.findField(entityClass, f.getName());
            if (entityField == null) {
                throw new IllegalStateException(String.format("The provided QueryCriteria has a not valid field '%s': not allowed for the %s", f.getName(), entityClass));
            }
            if (f.getType().isPrimitive()) {
                throw new IllegalStateException(String.format("The QueryCriteria cannot contains primitive types! field '%s' of %s", f.getName(), queryCriteria.getClass()));
            }
            if (!entityField.getType().equals(f.getType()) && (!entityField.getType().isPrimitive() || !ReflectionUtils.isPrimitiveWrapperOf(f.getType(), entityField.getType()))) {
                throw new IllegalStateException(String.format("The provided QueryCriteria has a not valid field '%s' for the %s: the type %s mismatch the entity type %s", f.getName(), entityClass, f.getType(), entityField.getType()));
            }
        });
    }

    protected abstract D getDao();

    protected abstract void setId(E entity, K id);

    protected abstract K getId(E entity);

    /**
     * This function returns an id sorted by bias
     */
    protected abstract K buildId(int bias);

    protected String getIdName() {
        if (org.springframework.util.ReflectionUtils.findField(entityClass, "id") ==null) {
            throw new IllegalStateException("The entity has not a field named 'id'. Please override getIdName method inside the test!");
        }
        return "id";
    }

    /**
     * This function has to return a valid E object with all right parameters
     * according to entity type
     */
    protected E getEntity() throws IllegalAccessException, InstantiationException {
        E e = TestUtils.mockInstance(entityClass.newInstance());
        this.setId(e, this.getStoredId());
        return e;
    }

    /**
     * The Id has to be just stored before test
     * and has to be smaller than each next id {@link #getNextId()}
     */
    protected K getStoredId() {
        return this.buildId(1);
    }

    protected E getEntityToSave() throws InstantiationException, IllegalAccessException {
        E e = this.getEntity();
        this.setId(e, this.getNextId());
        return e;
    }

    /**
     * This method has to return the entity already present in db
     */
    protected E getStoredEntity() throws InstantiationException, IllegalAccessException {
        return getEntity();
    }

    /**
     * This function return an id greater than {@link #getStoredId()}
     */
    protected K getNextId() {
        return this.buildId(2);
    }

    protected abstract void alterEntityToUpdate(E entity);


    @Rollback
    @Test
    @Transactional
    public void findAll() {
        List<E> list = getDao().findAll();
        Assert.assertNotNull(list);
    }

    @Rollback
    @Test
    @Transactional
    public void findAllPageable() throws IllegalAccessException, InstantiationException {

        PageRequest pageable = PageRequest.of(0, 1, getSortBy());
        E saved = getDao().save(getEntityToSave());

        clearContext(saved);

        Page<E> list1 = getDao().findAll((CriteriaQuery<E>) null, pageable);

        Assert.assertEquals(getDao().count(), list1.getTotalElements());
        Assert.assertEquals(1, list1.getPageable().getPageSize());
        Assert.assertEquals(0, list1.getPageable().getPageNumber());
        Assert.assertNotNull(list1.getContent());
        Assert.assertEquals(1, list1.getContent().size());
        compare(this.getStoredEntity(), list1.getContent().get(0));

        pageable = PageRequest.of(1, 1, getSortBy());

        Page<E> list2 = getDao().findAll((CriteriaQuery<E>) null, pageable);
        Assert.assertNotNull(list2.getContent());
        Assert.assertEquals(1, list2.getContent().size());
        compare(saved, list2.getContent().get(0));
    }

    /** To override in order to sort the result in such a way to have the already stored entity as first entity */
    protected Sort getSortBy() {
        return Sort.by(this.getIdName());
    }

    protected void clearContext(E saved) {
        lazyLoadCollectionToCheck(saved);

        entityManager.flush();
        entityManager.clear();
    }

    @Rollback
    @Test
    @Transactional
    public void findAllCriteria() throws IllegalAccessException, InstantiationException {
        E saved = getDao().save(getEntityToSave());

        clearContext(saved);

        ColoredPrinters.PRINT_GREEN.println("Fetching alreadyStored");
        CriteriaQuery<E> criteriaQuery = getMatchAlreadySavedCriteria();
        Page<E> list1 = getDao().findAll(criteriaQuery, null);

        Assert.assertNotNull(list1.getContent());
        Assert.assertEquals(1, list1.getContent().size());
        compare(this.getStoredEntity(), list1.getContent().get(0));

        ColoredPrinters.PRINT_GREEN.println("Fetching new saved entities");
        BeanUtils.copyProperties(saved, criteriaQuery);
        list1 = getDao().findAll(criteriaQuery, null);

        Assert.assertNotNull(list1.getContent());
        Assert.assertEquals(1, list1.getContent().size());
        compare(saved, list1.getContent().get(0));

    }

    protected abstract CriteriaQuery<E> getMatchAlreadySavedCriteria();

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
    public void findById() {

        Optional<E> optionalE = getDao().findById(this.getStoredId());
        Assert.assertTrue(optionalE.isPresent());
        Assert.assertNotNull(optionalE.get());

    }

    @Rollback
    @Test
    @Transactional
    public void save() throws IllegalAccessException, InstantiationException {

        E saved = getDao().save(this.getEntityToSave());
        Assert.assertNotNull(saved);
        Assert.assertNotNull(getId(saved));

        clearContext(saved);

        Optional<E> optional = getDao().findById(this.getId(saved));
        Assert.assertTrue(optional.isPresent());
        compare(saved, optional.get());
    }

    /**
     * To override in order to lazy load collection before {@link EntityManager#clear()}
     */
    protected void lazyLoadCollectionToCheck(E saved) {
        //Do Nothing
    }

    protected void compare(E entityToSave, E saved) {
        TestUtils.reflectionEqualsByName(entityToSave, saved);
    }


    @Rollback
    @Test
    @Transactional
    public void update() {

        Optional<E> optional1 = getDao().findById(this.getStoredId());
        Assert.assertTrue(optional1.isPresent());
        E entity = optional1.get();

        clearContext(entity);

        this.alterEntityToUpdate(entity);
        E entityUpdated = getDao().update(entity);

        compare(entity, entityUpdated);

        clearContext(entityUpdated);

        Optional<E> optional2 = getDao().findById(this.getId(entity));
        Assert.assertTrue(optional2.isPresent());

        compare(entity, optional2.get());
    }

    @Rollback
    @Test
    @Transactional
    public void count() {
        List<E> list = getDao().findAll();
        Assert.assertEquals(list.size(), getDao().count());
    }

    @Rollback
    @Test
    @Transactional
    public void deleteAll() {
        getDao().deleteAll();
        Assert.assertEquals(0, getDao().count());
    }

}

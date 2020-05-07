package eu.sia.meda.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ReflectionUtils;

import eu.sia.meda.layers.connector.CrudDAO;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CrudServiceImpl<E extends Serializable, K extends Serializable> implements CrudService<E,K> {

    public static final String[] TECH_FIELDS = {"enabled", "insertDate", "insertUser", "updateDate", "updateUser"};
    protected final CrudDAO<E,K> crudDAO;

    protected CrudServiceImpl(CrudDAO<E, K> crudDAO) {
        this.crudDAO = crudDAO;
    }

    @Override
    public Page<E> findAll(CriteriaQuery<? super E> criteriaQuery, Pageable pageable) {
        return crudDAO.findAll(criteriaQuery, pageable);
    }

    @Override
    public long count() {
        return crudDAO.count();
    }

    @Override
    public Optional<E> findById(@NotNull K id) {
        return crudDAO.findById(id);
    }

    @Override
    public <S extends E> S save(S entity) {
        return crudDAO.save(entity);
    }

    @Override
    public <S extends E> S update(@NotNull S entity) {
        if(entity==null){
            throw new IllegalArgumentException("Trying to update using a null entity");
        }
        K id = getId(entity);
        if(id==null){
            throw new IllegalArgumentException("Trying to update an entity with null id");

        }
        Optional<E> savedOpt = findById(id);
        if(savedOpt.isPresent()) {
            return crudDAO.update(entity);
        } else {
            throw new IllegalStateException(String.format("Trying to update a not existent entity! %s(%s)", entity.getClass().getName(), id));
        }
    }

    /** To override if the entity has a not "id" field */
    protected <S extends E> K getId(S entity){
        Field idField = ReflectionUtils.findField(entity.getClass(), "id");
        if(idField == null){
            throw new IllegalStateException("The input entity has not a standard \"id\" field. Please override eu.sia.meda.service.CrudServiceImpl.getId");
        }
        boolean oldAccessible = idField.isAccessible();
        idField.setAccessible(true);
        //noinspection unchecked
        K out = (K)ReflectionUtils.getField(idField, entity);
        idField.setAccessible(oldAccessible);
        return out;
    }

    @Override
    public void deleteById(@NotNull K id) {
        crudDAO.deleteById(id);
    }
}

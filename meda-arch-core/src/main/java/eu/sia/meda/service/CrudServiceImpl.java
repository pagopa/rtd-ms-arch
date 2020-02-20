package eu.sia.meda.service;

import eu.sia.meda.domain.model.BaseEntity;
import eu.sia.meda.layers.connector.CrudDAO;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ReflectionUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.Optional;

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

        if(entity instanceof BaseEntity){

            if(((BaseEntity) entity).getInsertDate()==null || ((BaseEntity) entity).getInsertDate().equals("")){
                ((BaseEntity) entity).setInsertDate(OffsetDateTime.now());
            }

            if(((BaseEntity) entity).getInsertUser() == null || ((BaseEntity) entity).getInsertUser().equals("")){
                //TODO: set INSERT_USER from context
            }

            ((BaseEntity) entity).setUpdateDate(OffsetDateTime.now());
            //TODO: set UPDATE_USER User from context
        }
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
            if (entity instanceof BaseEntity) {
                //TODO: set UPDATE_USER User from context
                ((BaseEntity) entity).setUpdateDate(OffsetDateTime.now());
            }
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

package eu.sia.meda.service;

import eu.sia.meda.domain.model.BaseEntity;
import eu.sia.meda.layers.connector.CrudDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Slf4j
public abstract class CrudServiceImpl<E extends Serializable, K extends Serializable> implements CrudService<E,K> {

    private final CrudDAO<E,K> crudDAO;

    protected CrudServiceImpl(CrudDAO<E, K> crudDAO) {
        this.crudDAO = crudDAO;
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return crudDAO.findAll(pageable);
    }

    @Override
    public long count() {
        return crudDAO.count();
    }

    @Override
    public E findById(@NotNull K id) {
        return crudDAO.findById(id);
    }

    @Override
    public E save(@NotNull  E entity) {

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
    public E update(@NotNull  E entity) {
        if(entity instanceof BaseEntity){
            //TODO: set UPDATE_USER User from context
            ((BaseEntity) entity).setUpdateDate(OffsetDateTime.now());
        }
        return crudDAO.update(entity);
    }

    @Override
    public E deleteById(@NotNull K id) {
        return crudDAO.deleteById(id);
    }
}

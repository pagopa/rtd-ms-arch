package eu.sia.meda.service;

import eu.sia.meda.layers.connector.CrudDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    public E findById(@NotNull K id) {
        return crudDAO.findById(id);
    }

    @Override
    public E save(@NotNull  E entity) {
        return crudDAO.save(entity);
    }

    @Override
    public E update(@NotNull  E entity) {
        return crudDAO.update(entity);
    }

    @Override
    public E deleteById(@NotNull K id) {
        return crudDAO.deleteById(id);
    }
}

package eu.sia.meda.service;

import eu.sia.meda.layers.connector.CrudDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class CrudServiceImpl<E extends Serializable, K extends Serializable> implements CrudService<E,K> {

    private final CrudDAO<E,K> crudDAO;

    protected CrudServiceImpl(CrudDAO<E, K> crudDAO) {
        this.crudDAO = crudDAO;
    }

    @Override
    public List<E> findAll(Pageable pageable) {
        return crudDAO.findAll(pageable);
    }

    @Override
    public E findById(K id) {
        return crudDAO.findById(id);
    }

    @Override
    public E save(@Valid E entity) {
        return crudDAO.save(entity);
    }

    @Override
    public E update(@Valid E entity) {
        return crudDAO.update(entity);
    }

    @Override
    public E deleteById(K id) {
        return crudDAO.deleteById(id);
    }
}

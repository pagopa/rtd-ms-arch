package eu.sia.meda.core.controller;

import eu.sia.meda.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class CrudControllerImpl <E extends Serializable, K extends Serializable> extends StatelessController implements CrudController<E,K> {

    private final CrudService<E,K> crudService;

    protected CrudControllerImpl(CrudService<E, K> crudService) {
        this.crudService = crudService;
    }

    @Override
    public List<E> findAll(Pageable pageable) {
        return crudService.findAll(pageable);
    }

    @Override
    public E findById(K id) {
        return crudService.findById(id);
    }

    @Override
    public E save(@Valid E entity) {
        return crudService.save(entity);
    }

    @Override
    public E update(@Valid E entity) {
        return crudService.update(entity);
    }

    @Override
    public E deleteById(K id) {
        return crudService.deleteById(id);
    }
}

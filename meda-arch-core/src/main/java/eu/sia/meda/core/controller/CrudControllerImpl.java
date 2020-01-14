package eu.sia.meda.core.controller;

import eu.sia.meda.core.assembler.BaseResourceAssemblerSupport;
import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collections;

@Slf4j
public abstract class CrudControllerImpl <R extends BaseResource, E extends Serializable, K extends Serializable> extends StatelessController implements CrudController<R,E,K> {

    private final CrudService<E,K> crudService;
    private final BaseResourceAssemblerSupport<E,R> resourceAssembler;

    protected CrudControllerImpl(CrudService<E, K> crudService, BaseResourceAssemblerSupport<E, R> resourceAssembler) {
        this.crudService = crudService;
        this.resourceAssembler = resourceAssembler;
    }

    @Override
    public Page<R> findAll(Pageable pageable) {
        Page<E> result = crudService.findAll(pageable);
        if(result == null){
            return Page.empty();
        } else {
            //noinspection unchecked
            return new PageImpl<R>(resourceAssembler.toResources(result), pageable, result.getTotalElements());
        }
    }

    @Override
    public long count() {
        return crudService.count();
    }

    @Override
    public R findById(K id) {
        return resourceAssembler.toResource(crudService.findById(id));
    }

    @Override
    public R save(@Valid E entity) {
        return resourceAssembler.toResource(crudService.save(entity));
    }

    @Override
    public R update(@Valid E entity) {
        return resourceAssembler.toResource(crudService.update(entity));
    }

    @Override
    public R deleteById(K id) {
        return resourceAssembler.toResource(crudService.deleteById(id));
    }
}

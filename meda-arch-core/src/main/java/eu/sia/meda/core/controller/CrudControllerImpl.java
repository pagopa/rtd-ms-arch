package eu.sia.meda.core.controller;

import eu.sia.meda.core.assembler.BaseResourceAssemblerSupport;
import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;

import javax.validation.Valid;
import java.io.Serializable;

@Slf4j
public abstract class CrudControllerImpl <R extends BaseResource, E extends Serializable, K extends Serializable> extends StatelessController implements CrudController<R,E,K> {

    private final CrudService<E,K> crudService;
    private final BaseResourceAssemblerSupport<E,R> resourceAssembler;

    @Autowired
    private PagedResourcesAssembler<E> pagedResourcesAssembler;

    protected CrudControllerImpl(CrudService<E, K> crudService, BaseResourceAssemblerSupport<E, R> resourceAssembler) {
        this.crudService = crudService;
        this.resourceAssembler = resourceAssembler;
    }

    @Override
    public PagedResources<R> findAll(Pageable pageable) {
        Page<E> result = crudService.findAll(pageable);
        if(result == null){
            result = Page.empty();
        }

        return pagedResourcesAssembler.toResource(result, resourceAssembler);
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

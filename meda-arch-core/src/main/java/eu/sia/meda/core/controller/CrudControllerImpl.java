package eu.sia.meda.core.controller;

import eu.sia.meda.core.assembler.BaseResourceAssemblerSupport;
import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import eu.sia.meda.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Optional;

@Slf4j
public abstract class CrudControllerImpl <R extends BaseResource, E extends Serializable, K extends Serializable> extends StatelessController implements CrudController<R,E,K> {

    protected final CrudService<E,K> crudService;
    protected final BaseResourceAssemblerSupport<E,R> resourceAssembler;

    @Autowired
    private PagedResourcesAssembler<E> pagedResourcesAssembler;

    protected CrudControllerImpl(CrudService<E, K> crudService, BaseResourceAssemblerSupport<E, R> resourceAssembler) {
        this.crudService = crudService;
        this.resourceAssembler = resourceAssembler;
    }

    @Override
    public PagedResources<R> findAll(CriteriaQuery<E> criteriaQuery, Pageable pageable) {
        Page<E> result = crudService.findAll(criteriaQuery, pageable);
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
    public Optional<R> findById(K id) {
        Optional<E> entity = crudService.findById(id);
        if(!entity.isPresent()){
            return Optional.empty();
        }
        else{
            return Optional.of(resourceAssembler.toResource(crudService.findById(id).get()));
        }
    }

    @Override
    public R save(@Valid E entity) {
        if(entity == null) return null;
        return resourceAssembler.toResource(crudService.save(entity));
    }

    @Override
    public R update(@Valid E entity) {
        if(entity == null) return null;
        return resourceAssembler.toResource(crudService.update(entity));
    }

    @Override
    public void deleteById(K id) {
        crudService.deleteById(id);
    }
}

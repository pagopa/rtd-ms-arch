package eu.sia.meda.core.controller;

import eu.sia.meda.core.resource.BaseResource;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

/** Controller to expose Crud operations on {@link E} entity, having a key of type {@link K} */
@RequestMapping
public interface CrudController<R extends BaseResource, E extends Serializable, K extends Serializable, C extends CriteriaQuery<? super E>>{
    /** To find all entities */
    @GetMapping
    PagedResources<R> findAll(C criteriaQuery, Pageable pageable);

    /** To count the entities */
    @GetMapping("/count")
    long count();

    /** To find the entity having the provided <i>id</i> */
    @GetMapping("/{id}")
    Optional<R> findById(@Valid @NotNull @PathVariable K id);

    /** To store a new entity */
    @PostMapping
    R save(@Valid @RequestBody E entity);

    /** To update an already existent entity */
    @PutMapping
    R update(@Valid @RequestBody E entity);

    /** To delete an entity */
    @DeleteMapping("/{id}")
    void deleteById(@Valid @NotNull @PathVariable K id);
}
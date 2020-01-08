package eu.sia.meda.core.controller;

import eu.sia.meda.core.resource.BaseResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/** Controller to expose Crud operations on {@link E} entity, having a key of type {@link K} */
@RequestMapping
public interface CrudController<R extends BaseResource, E extends Serializable, K extends Serializable>{
    /** To find all entities */
    @GetMapping
    Page<R> findAll(Pageable pageable);

    /** To find the entity having the provided <i>id</i> */
    @GetMapping("/{id}")
    R findById(@Valid @NotNull @PathVariable K id);

    /** To store a new entity */
    @PostMapping
    R save(@Valid @RequestBody E entity);

    /** To update an already existent entity */
    @PutMapping
    R update(@Valid @RequestBody E entity);

    /** To delete an entity */
    @DeleteMapping("/{id}")
    R deleteById(@Valid @NotNull @PathVariable K id);
}
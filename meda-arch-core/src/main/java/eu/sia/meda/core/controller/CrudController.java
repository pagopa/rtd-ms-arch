package eu.sia.meda.core.controller;

import eu.sia.meda.layers.connector.CrudOperations;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/** Controller to expose Crud operations on {@link E} entity, having a key of type {@link K} */
@RequestMapping
public interface CrudController<E extends Serializable, K extends Serializable> extends CrudOperations<E,K>{
    /** To find all entities */
    @Override
    @GetMapping
    List<E> findAll(Pageable pageable);

    /** To find the entity having the provided <i>id</i> */
    @Override
    @GetMapping("/{id}")
    E findById(@Valid @NotNull @PathVariable K id);

    /** To store a new entity */
    @Override
    @PostMapping
    E save(@Valid @RequestBody E entity);

    /** To update an already existent entity */
    @Override
    @PutMapping
    E update(@Valid @RequestBody E entity);

    /** To delete an entity */
    @Override
    @DeleteMapping("/{id}")
    E deleteById(@Valid @NotNull @PathVariable K id);
}
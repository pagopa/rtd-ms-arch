package eu.sia.meda.layers.connector;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/** Crud operations on {@link E} entity, having a key of type {@link K} */
@RequestMapping
public interface CrudOperations<E extends Serializable, K extends Serializable> {
    /** To find all entities */
    @GetMapping
    List<E> findAll(Pageable pageable);

    /** To find the entity having the provided <i>id</i> */
    @GetMapping("/{id}")
    E findById(@PathVariable K id);

    /** To store a new entity */
    @PostMapping
    E save(@Valid @RequestBody E entity);

    /** To update an already existent entity */
    @PutMapping
    E update(@Valid @RequestBody E entity);

    /** To delete an entity */
    @DeleteMapping("/{id}")
    E deleteById(@PathVariable Long id);
}

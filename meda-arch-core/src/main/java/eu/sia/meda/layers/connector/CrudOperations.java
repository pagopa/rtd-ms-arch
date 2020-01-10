package eu.sia.meda.layers.connector;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/** Crud operations on {@link E} entity, having a key of type {@link K} */
public interface CrudOperations<E extends Serializable, K extends Serializable> {
    /** To find all entities */
    Page<E> findAll(Pageable pageable);

    /** To find the entity having the provided <i>id</i> */
    E findById(@NotNull K id);

    /** To store a new entity */
    E save(@NotNull E entity);

    /** To update an already existent entity */
    E update(@NotNull E entity);

    /** To delete an entity */
    E deleteById(@NotNull K id);
}

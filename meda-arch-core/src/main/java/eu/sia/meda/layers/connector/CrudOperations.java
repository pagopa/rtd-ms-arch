package eu.sia.meda.layers.connector;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/** Crud operations on {@link E} entity, having a key of type {@link K} */
public interface CrudOperations<E extends Serializable, K extends Serializable> {
    /** To find all entities */
    List<E> findAll(Pageable pageable);

    /** To find the entity having the provided <i>id</i> */
    E findById(K id);

    /** To store a new entity */
    E save(E entity);

    /** To update an already existent entity */
    E update(E entity);

    /** To delete an entity */
    E deleteById(K id);
}

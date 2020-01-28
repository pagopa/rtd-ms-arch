package eu.sia.meda.layers.connector;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

/** Crud operations on {@link E} entity, having a key of type {@link K} */
public interface CrudOperations<E extends Serializable, K extends Serializable> {
    /** To find all entities */
    Page<E> findAll(Pageable pageable);

    /** To count the entities */
    long count();

    /** To find the entity having the provided <i>id</i> */
    Optional<E> findById(@NotNull K id);

    /** To store a new entity */
    <S extends E> S save(@NotNull S entity);

    /** To update an already existent entity */
    <S extends E> S update(@NotNull S entity);

    /** To delete an entity */
    void deleteById(@NotNull K id);
}

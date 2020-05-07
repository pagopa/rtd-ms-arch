package eu.sia.meda.layers.connector;

import eu.sia.meda.layers.connector.query.CriteriaQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Crud operations on {@link E} entity, having a key of type {@link K} */
public interface CrudOperations<E extends Serializable, K extends Serializable> {
    /** To find all entities matching the {@link CriteriaQuery} built on {@link E} */
    Page<E> findAll(CriteriaQuery<? super E> criteriaQuery, Pageable pageable);

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

    /** To apply the pagination */
    static <E> Page<E> paginateResult(Pageable pageable, List<E> result, Class<E> clazz) {
        List<E> pageContent;

        if (pageable != null && pageable.isPaged()) {
            int offset = pageable.getPageNumber() * pageable.getPageSize();
            if (pageable.getSort().isSorted()) {
                //noinspection SimplifyStreamApiCallChains
                result.sort(pageable.getSort().get().map(o -> {
                    @SuppressWarnings("unchecked") Comparator<E> comparing = Comparator.comparing(x -> {
                        Field field = org.springframework.util.ReflectionUtils.findField(clazz, o.getProperty());
                        if (field == null) {
                            throw new IllegalArgumentException(String.format("Cannot find field %s (remember that nested sort is not handled)", o.getProperty()));
                        }
                        field.setAccessible(true);
                        try {
                            //noinspection rawtypes
                            return (Comparable) field.get(x);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException(e);
                        }
                    });
                    if (o.isDescending()) {
                        return comparing.reversed();
                    } else {
                        return comparing;
                    }
                })
                        .collect(Collectors.reducing(Comparator::thenComparing)).orElse(null));
            }
            pageContent = result.stream().skip(offset).limit(pageable.getPageSize()).collect(Collectors.toList());
        } else {
            pageable = Pageable.unpaged();
            pageContent = result;
        }
        return new PageImpl<>(pageContent, pageable, result.size());
    }
}

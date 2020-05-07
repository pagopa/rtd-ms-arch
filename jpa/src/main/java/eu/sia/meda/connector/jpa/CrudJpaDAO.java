package eu.sia.meda.connector.jpa;

import eu.sia.meda.layers.connector.CrudDAO;
import eu.sia.meda.layers.connector.query.CriteriaFilter;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import org.hibernate.metamodel.internal.SingularAttributeImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Embeddable;
import javax.persistence.criteria.Path;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

@Transactional
@NoRepositoryBean
public interface CrudJpaDAO<E extends Serializable, K extends Serializable> extends CrudDAO<E, K>, JPAConnector<E, K>, JpaSpecificationExecutor<E> {

    @Override
    default Page<E> findAll(CriteriaQuery<? super E> criteriaQuery, Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        if (criteriaQuery != null) {
            Specification<E> where = buildCriteria(criteriaQuery, Specification.where(null), null);
            return findAll(where, pageable);
        } else {
            return findAll(pageable);
        }
    }

    default Specification<E> buildCriteria(Object criteria, Specification<E> whereCondition, String path) {
        //noinspection unchecked
        Specification<E>[] where = new Specification[]{whereCondition};
        ReflectionUtils.doWithFields(criteria.getClass(), f -> {
                    f.setAccessible(true);
                    Object c = f.get(criteria);
                    String field = String.format("%s%s", path != null ? String.format("%s.", path) : "", f.getName());
                    if (c != null) {
                        if (f.getType().getAnnotation(Embeddable.class) != null) {
                            where[0] = where[0].and(buildCriteria(c, where[0], field));
                        } else {
                            where[0] = where[0].and((root, query, criteriaBuilder) -> {
                                String[] fieldParts = field.split("\\.");
                                Path<?> fieldPath = root.get(fieldParts[0]);
                                if (fieldParts.length > 1) {
                                    for (int i = 1; i < fieldParts.length; i++) {
                                        fieldPath = fieldPath.get(fieldParts[i]);
                                    }
                                }
                                if(CriteriaFilter.class.isAssignableFrom(f.getType())){
                                    //noinspection unchecked,rawtypes
                                    return ((CriteriaFilter)c).toPredicate(criteriaBuilder, fieldPath);
                                } else {
                                    return criteriaBuilder.equal(fieldPath, c);
                                }
                            });
                        }
                    }
                },
                f -> !Modifier.isStatic(f.getModifiers()));

        return where[0];
    }

    @Override
    long count();

    @Override
    Optional<E> findById(@NotNull K id);

    @Override
    <S extends E> S save(@NotNull S entity);

    @Override
    default <S extends E> S update(@NotNull S entity) {
        return save(entity);
    }

    @Override
    void deleteById(@NotNull K id);
}

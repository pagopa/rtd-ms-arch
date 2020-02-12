package eu.sia.meda.connector.jpa;

import eu.sia.meda.layers.connector.CrudDAO;
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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

@Transactional
@NoRepositoryBean
public interface CrudJpaDAO<E extends Serializable, K extends Serializable> extends CrudDAO<E, K>, JPAConnector<E, K>, JpaSpecificationExecutor<E> {

    @Override
    default Page<E> findAll(CriteriaQuery<? super E> criteriaQuery, Pageable pageable){
        if(pageable==null){
            pageable =Pageable.unpaged();
        }
        if(criteriaQuery!=null){
            Specification<E>[] where = new Specification[]{Specification.where(null)};
            ReflectionUtils.doWithFields(criteriaQuery.getClass(), f->{
                f.setAccessible(true);
                Object c = f.get(criteriaQuery);
                if(c!=null){
                    where[0]=where[0].and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(f.getName()), c));
                }
            });
            return findAll(where[0], pageable);
        }else {
            return findAll(pageable);
        }
    }

    @Override
    long count();

    @Override
    Optional<E> findById(@NotNull K id);

    @Override
    <S extends E> S save(@NotNull S entity);

    @Override
    default <S extends E> S update(@NotNull S entity){
        return save(entity);
    }

    @Override
    void deleteById(@NotNull K id);
}

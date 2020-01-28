package eu.sia.meda.connector.jpa;

import eu.sia.meda.layers.connector.CrudDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

@Transactional
@NoRepositoryBean
public interface CrudJpaDAO<E extends Serializable, K extends Serializable> extends CrudDAO<E, K>, JPAConnector<E, K>, JpaSpecificationExecutor<E> {

    @Override
    Page<E> findAll(Pageable pageable);

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

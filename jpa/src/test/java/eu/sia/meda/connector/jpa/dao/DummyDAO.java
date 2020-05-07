package eu.sia.meda.connector.jpa.dao;

import eu.sia.meda.connector.jpa.CrudJpaDAO;
import eu.sia.meda.connector.jpa.model.DummyEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@ConditionalOnProperty(name = "connectors.jpaConfigurations.connection.mocked", havingValue = "true")
@Profile(DummyDAO.SPRING_PROFILE_DUMMY_ENTITY)
@Transactional
public interface DummyDAO extends CrudJpaDAO<DummyEntity, Integer> {
    public final static String SPRING_PROFILE_DUMMY_ENTITY="SPRING_PROFILE_DUMMY_ENTITY";
}

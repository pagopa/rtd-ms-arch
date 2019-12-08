/*
 * 
 */
package eu.sia.meda.connector.jpa;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * The Interface JPAConnector.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
@NoRepositoryBean
public interface JPAConnector<T, ID extends Serializable> extends JpaRepository<T, ID> {
   
   /**
    * Execute SQL query.
    *
    * @param query the query
    * @return the list
    * @throws Exception the exception
    */
   List<T> executeSQLQuery(String query) throws Exception;
}

/*
 * 
 */
package eu.sia.meda.connector.jpa.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.util.Optionals;
import org.springframework.util.Assert;

/**
 * The Class DefaultQueryHintsExt.
 */
public class DefaultQueryHintsExt implements QueryHintsExt {
   
   /** The information. */
   private final JpaEntityInformation<?, ?> information;
   
   /** The metadata. */
   private final CrudMethodMetadata metadata;
   
   /** The entity manager. */
   private final Optional<EntityManager> entityManager;

   /**
    * Instantiates a new default query hints ext.
    *
    * @param information the information
    * @param metadata the metadata
    * @param entityManager the entity manager
    */
   private DefaultQueryHintsExt(JpaEntityInformation<?, ?> information, CrudMethodMetadata metadata, Optional<EntityManager> entityManager) {
      this.information = information;
      this.metadata = metadata;
      this.entityManager = entityManager;
   }

   /**
    * Of.
    *
    * @param information the information
    * @param metadata the metadata
    * @return the query hints ext
    */
   public static QueryHintsExt of(JpaEntityInformation<?, ?> information, CrudMethodMetadata metadata) {
      Assert.notNull(information, "JpaEntityInformation must not be null!");
      Assert.notNull(metadata, "CrudMethodMetadata must not be null!");
      return new DefaultQueryHintsExt(information, metadata, Optional.empty());
   }

   /**
    * With fetch graphs.
    *
    * @param em the em
    * @return the query hints ext
    */
   public QueryHintsExt withFetchGraphs(EntityManager em) {
      return new DefaultQueryHintsExt(this.information, this.metadata, Optional.of(em));
   }

   /**
    * Iterator.
    *
    * @return the iterator
    */
   public Iterator<Entry<String, Object>> iterator() {
      return this.asMap().entrySet().iterator();
   }

   /**
    * As map.
    *
    * @return the map
    */
   public Map<String, Object> asMap() {
      Map<String, Object> hints = new HashMap();
      hints.putAll(this.metadata.getQueryHints());
      hints.putAll(this.getFetchGraphs());
      return hints;
   }

   /**
    * Gets the fetch graphs.
    *
    * @return the fetch graphs
    */
   public Map<String, Object> getFetchGraphs() {
      return (Map)Optionals.mapIfAllPresent(this.entityManager, this.metadata.getEntityGraph(), (em, graph) -> {
         return Jpa21Utils.tryGetFetchGraphHints(em, this.getEntityGraph(graph), this.information.getJavaType());
      }).orElse(Collections.emptyMap());
   }

   /**
    * Gets the entity graph.
    *
    * @param entityGraph the entity graph
    * @return the entity graph
    */
   public JpaEntityGraph getEntityGraph(EntityGraph entityGraph) {
      String fallbackName = this.information.getEntityName() + "." + this.metadata.getMethod().getName();
      return new JpaEntityGraph(entityGraph, fallbackName);
   }
}

/*
 * 
 */
package eu.sia.meda.connector.jpa.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.EntityManager;

/**
 * The Interface QueryHintsExt.
 */
public interface QueryHintsExt extends Iterable<Entry<String, Object>> {
   
   /**
    * With fetch graphs.
    *
    * @param var1 the var 1
    * @return the query hints ext
    */
   QueryHintsExt withFetchGraphs(EntityManager var1);

   /**
    * As map.
    *
    * @return the map
    */
   Map<String, Object> asMap();

   /**
    * The Enum NoHints.
    */
   public static enum NoHints implements QueryHintsExt {
      
      /** The instance. */
      INSTANCE;

      /**
       * As map.
       *
       * @return the map
       */
      public Map<String, Object> asMap() {
         return Collections.emptyMap();
      }

      /**
       * Iterator.
       *
       * @return the iterator
       */
      public Iterator<Entry<String, Object>> iterator() {
         return Collections.emptyIterator();
      }

      /**
       * With fetch graphs.
       *
       * @param em the em
       * @return the query hints ext
       */
      public QueryHintsExt withFetchGraphs(EntityManager em) {
         return this;
      }
   }
}

package eu.sia.meda.layers.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.springframework.util.CollectionUtils;

/**
 * The Class BaseTransformer.
 *
 * @param <F> the generic type
 * @param <T> the generic type
 */
public abstract class BaseTransformer<F, T> implements ITransformer<F, T> {
   
   /**
    * Transform.
    *
    * @param from the from
    * @return the collection
    */
   public Collection<T> transform(Collection<F> from) {
      if (CollectionUtils.isEmpty(from)) {
         return Collections.emptyList();
      } else {
         Collection<T> collection = new ArrayList<>();
         Iterator<F> var3 = from.iterator();

         while(var3.hasNext()) {
            F f = var3.next();
            T to = this.transform(f);
            collection.add(to);
         }

         return collection;
      }
   }
}

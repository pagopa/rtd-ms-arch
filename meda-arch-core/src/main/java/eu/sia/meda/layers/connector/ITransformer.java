package eu.sia.meda.layers.connector;

import java.util.Collection;

/**
 * The Interface ITransformer.
 *
 * @param <F> the generic type
 * @param <T> the generic type
 */
public interface ITransformer<F, T> {
   
   /**
    * Transform.
    *
    * @param from the from
    * @return the t
    */
   T transform(F from);

   /**
    * Transform.
    *
    * @param from the from
    * @return the collection
    */
   Collection<T> transform(Collection<F> from);
}

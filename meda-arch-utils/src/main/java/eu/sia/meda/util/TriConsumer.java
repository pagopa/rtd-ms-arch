package eu.sia.meda.util;

/**
 * The Interface TriConsumer.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @param <S> the generic type
 */
public interface TriConsumer<K, V, S> {
   
   /**
    * Accept.
    *
    * @param var1 the var 1
    * @param var2 the var 2
    * @param var3 the var 3
    */
   void accept(K var1, V var2, S var3);
}

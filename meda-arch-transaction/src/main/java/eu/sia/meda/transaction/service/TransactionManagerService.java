/*
 * 
 */
package eu.sia.meda.transaction.service;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * The Interface TransactionManagerService.
 *
 * @param <T> the generic type
 */
public interface TransactionManagerService<T> {
   
   /**
    * Put transaction info.
    *
    * @param idOp the id op
    * @param transactionInfo the transaction info
    * @param dispoValues the dispo values
    * @return the string
    */
   String putTransactionInfo(String idOp, T transactionInfo, Map<String, String> dispoValues);

   /**
    * Put transaction info.
    *
    * @param transactionInfo the transaction info
    * @param dispoValues the dispo values
    * @return the string
    */
   String putTransactionInfo(T transactionInfo, Map<String, String> dispoValues);

   /**
    * Gets the transaction info.
    *
    * @param idOp the id op
    * @return the transaction info
    */
   T getTransactionInfo(String idOp);

   /**
    * Delete transaction info.
    *
    * @param idOp the id op
    * @return true, if successful
    */
   boolean deleteTransactionInfo(String idOp);

   /**
    * Sets the clazz.
    *
    * @param clazz the new clazz
    */
   void setClazz(Type clazz);
}

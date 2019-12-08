/*
 * 
 */
package eu.sia.meda.transaction.resource;

import eu.sia.meda.core.resource.BaseResource;

/**
 * The Class TransactionInfoResource.
 *
 * @param <T> the generic type
 */
public class TransactionInfoResource<T> extends BaseResource {
   
   /** The transaction info. */
   private T transactionInfo;

   /**
    * Gets the transaction info.
    *
    * @return the transaction info
    */
   public T getTransactionInfo() {
      return this.transactionInfo;
   }

   /**
    * Sets the transaction info.
    *
    * @param transactionInfo the new transaction info
    */
   public void setTransactionInfo(T transactionInfo) {
      this.transactionInfo = transactionInfo;
   }
}

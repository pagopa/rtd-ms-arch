/*
 * 
 */
package eu.sia.meda.transaction.model;

import java.util.Map;

/**
 * The Class TransactionInfoWithIdOp.
 *
 * @param <T> the generic type
 */
public class TransactionInfoWithIdOp<T> {
   
   /** The id op. */
   private String idOp;
   
   /** The transaction info. */
   private T transactionInfo;
   
   /** The dispo values. */
   private Map<String, String> dispoValues;

   /**
    * Instantiates a new transaction info with id op.
    */
   public TransactionInfoWithIdOp() {
   }

   /**
    * Instantiates a new transaction info with id op.
    *
    * @param idOp the id op
    * @param transactionInfo the transaction info
    * @param dispoValues the dispo values
    */
   public TransactionInfoWithIdOp(String idOp, T transactionInfo, Map<String, String> dispoValues) {
      this.idOp = idOp;
      this.transactionInfo = transactionInfo;
      this.dispoValues = dispoValues;
   }

   /**
    * Gets the id op.
    *
    * @return the id op
    */
   public String getIdOp() {
      return this.idOp;
   }

   /**
    * Sets the id op.
    *
    * @param idOp the new id op
    */
   public void setIdOp(String idOp) {
      this.idOp = idOp;
   }

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

   /**
    * Gets the dispo values.
    *
    * @return the dispo values
    */
   public Map<String, String> getDispoValues() {
      return this.dispoValues;
   }

   /**
    * Sets the dispo values.
    *
    * @param dispoValues the dispo values
    */
   public void setDispoValues(Map<String, String> dispoValues) {
      this.dispoValues = dispoValues;
   }
}

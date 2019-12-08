/*
 * 
 */
package eu.sia.meda.transaction.dto;

import java.util.Map;

/**
 * The Class TransactionDispoDto.
 */
public class TransactionDispoDto {
   
   /** The transaction info. */
   private Object transactionInfo;
   
   /** The dispo values. */
   private Map<String, String> dispoValues;

   /**
    * Gets the transaction info.
    *
    * @return the transaction info
    */
   public Object getTransactionInfo() {
      return this.transactionInfo;
   }

   /**
    * Sets the transaction info.
    *
    * @param transactionInfo the new transaction info
    */
   public void setTransactionInfo(Object transactionInfo) {
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

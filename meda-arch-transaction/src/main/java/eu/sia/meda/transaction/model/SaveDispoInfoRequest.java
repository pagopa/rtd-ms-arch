/*
 * 
 */
package eu.sia.meda.transaction.model;

import java.util.Map;

/**
 * The Class SaveDispoInfoRequest.
 */
public class SaveDispoInfoRequest {
   
   /** The id op. */
   private String idOp;
   
   /** The dispo values. */
   private Map<String, String> dispoValues;

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

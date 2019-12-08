/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class Province.
 */
public class Province implements Serializable {
   
   /** The code. */
   private String code;
   
   /** The denomination. */
   private String denomination;

   /**
    * Gets the code.
    *
    * @return the code
    */
   public String getCode() {
      return this.code;
   }

   /**
    * Sets the code.
    *
    * @param code the new code
    */
   public void setCode(String code) {
      this.code = code;
   }

   /**
    * Gets the denomination.
    *
    * @return the denomination
    */
   public String getDenomination() {
      return this.denomination;
   }

   /**
    * Sets the denomination.
    *
    * @param denomination the new denomination
    */
   public void setDenomination(String denomination) {
      this.denomination = denomination;
   }
}

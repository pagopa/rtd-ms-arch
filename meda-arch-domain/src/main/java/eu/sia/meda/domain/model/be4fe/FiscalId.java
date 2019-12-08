/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class FiscalId.
 */
public class FiscalId implements Serializable {
   
   /** The codice identificativo. */
   private String codiceIdentificativo;

   /**
    * Gets the codice identificativo.
    *
    * @return the codice identificativo
    */
   public String getCodiceIdentificativo() {
      return this.codiceIdentificativo;
   }

   /**
    * Sets the codice identificativo.
    *
    * @param codiceIdentificativo the new codice identificativo
    */
   public void setCodiceIdentificativo(String codiceIdentificativo) {
      this.codiceIdentificativo = codiceIdentificativo;
   }
}

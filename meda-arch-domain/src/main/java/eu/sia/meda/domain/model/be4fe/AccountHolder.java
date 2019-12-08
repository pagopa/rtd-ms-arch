/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class AccountHolder.
 */
public class AccountHolder implements Serializable {
   
   /** The progressivo ordinante. */
   private String progressivoOrdinante;
   
   /** The tipo intestatario. */
   private String tipoIntestatario;
   
   /** The ndg. */
   private String ndg;

   /**
    * Gets the ndg.
    *
    * @return the ndg
    */
   public String getNdg() {
      return this.ndg;
   }

   /**
    * Sets the ndg.
    *
    * @param ndg the new ndg
    */
   public void setNdg(String ndg) {
      this.ndg = ndg;
   }

   /**
    * Gets the progressivo ordinante.
    *
    * @return the progressivo ordinante
    */
   public String getProgressivoOrdinante() {
      return this.progressivoOrdinante;
   }

   /**
    * Sets the progressivo ordinante.
    *
    * @param progressivoOrdinante the new progressivo ordinante
    */
   public void setProgressivoOrdinante(String progressivoOrdinante) {
      this.progressivoOrdinante = progressivoOrdinante;
   }

   /**
    * Gets the tipo intestatario.
    *
    * @return the tipo intestatario
    */
   public String getTipoIntestatario() {
      return this.tipoIntestatario;
   }

   /**
    * Sets the tipo intestatario.
    *
    * @param tipoIntestatario the new tipo intestatario
    */
   public void setTipoIntestatario(String tipoIntestatario) {
      this.tipoIntestatario = tipoIntestatario;
   }
}

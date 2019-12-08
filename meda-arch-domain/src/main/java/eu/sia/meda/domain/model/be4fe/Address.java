/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class Address.
 */
public class Address implements Serializable {
   
   /** The indirizzo. */
   private String indirizzo;
   
   /** The localita. */
   private String localita;
   
   /** The province. */
   private Province province;

   /**
    * Gets the indirizzo.
    *
    * @return the indirizzo
    */
   public String getIndirizzo() {
      return this.indirizzo;
   }

   /**
    * Sets the indirizzo.
    *
    * @param indirizzo the new indirizzo
    */
   public void setIndirizzo(String indirizzo) {
      this.indirizzo = indirizzo;
   }

   /**
    * Gets the localita.
    *
    * @return the localita
    */
   public String getLocalita() {
      return this.localita;
   }

   /**
    * Sets the localita.
    *
    * @param localita the new localita
    */
   public void setLocalita(String localita) {
      this.localita = localita;
   }

   /**
    * Gets the province.
    *
    * @return the province
    */
   public Province getProvince() {
      return this.province;
   }

   /**
    * Sets the province.
    *
    * @param province the new province
    */
   public void setProvince(Province province) {
      this.province = province;
   }
}

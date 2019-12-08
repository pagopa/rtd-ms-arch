/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class Residency.
 */
public class Residency implements Serializable {
   
   /** The cap. */
   private String cap;
   
   /** The codice zona geografica. */
   private String codiceZonaGeografica;
   
   /** The comune. */
   private String comune;
   
   /** The frazione. */
   private String frazione;
   
   /** The indirizzo. */
   private String indirizzo;
   
   /** The nazione. */
   private String nazione;
   
   /** The numero civico. */
   private String numeroCivico;
   
   /** The provincia. */
   private String provincia;

   /**
    * Gets the cap.
    *
    * @return the cap
    */
   public String getCap() {
      return this.cap;
   }

   /**
    * Sets the cap.
    *
    * @param cap the new cap
    */
   public void setCap(String cap) {
      this.cap = cap;
   }

   /**
    * Gets the codice zona geografica.
    *
    * @return the codice zona geografica
    */
   public String getCodiceZonaGeografica() {
      return this.codiceZonaGeografica;
   }

   /**
    * Sets the codice zona geografica.
    *
    * @param codiceZonaGeografica the new codice zona geografica
    */
   public void setCodiceZonaGeografica(String codiceZonaGeografica) {
      this.codiceZonaGeografica = codiceZonaGeografica;
   }

   /**
    * Gets the comune.
    *
    * @return the comune
    */
   public String getComune() {
      return this.comune;
   }

   /**
    * Sets the comune.
    *
    * @param comune the new comune
    */
   public void setComune(String comune) {
      this.comune = comune;
   }

   /**
    * Gets the frazione.
    *
    * @return the frazione
    */
   public String getFrazione() {
      return this.frazione;
   }

   /**
    * Sets the frazione.
    *
    * @param frazione the new frazione
    */
   public void setFrazione(String frazione) {
      this.frazione = frazione;
   }

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
    * Gets the nazione.
    *
    * @return the nazione
    */
   public String getNazione() {
      return this.nazione;
   }

   /**
    * Sets the nazione.
    *
    * @param nazione the new nazione
    */
   public void setNazione(String nazione) {
      this.nazione = nazione;
   }

   /**
    * Gets the numero civico.
    *
    * @return the numero civico
    */
   public String getNumeroCivico() {
      return this.numeroCivico;
   }

   /**
    * Sets the numero civico.
    *
    * @param numeroCivico the new numero civico
    */
   public void setNumeroCivico(String numeroCivico) {
      this.numeroCivico = numeroCivico;
   }

   /**
    * Gets the provincia.
    *
    * @return the provincia
    */
   public String getProvincia() {
      return this.provincia;
   }

   /**
    * Sets the provincia.
    *
    * @param provincia the new provincia
    */
   public void setProvincia(String provincia) {
      this.provincia = provincia;
   }
}

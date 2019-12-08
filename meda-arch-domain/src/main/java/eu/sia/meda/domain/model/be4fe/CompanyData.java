/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class CompanyData.
 */
public class CompanyData implements Serializable {
   
   /** The nsg. */
   private String nsg;
   
   /** The abi. */
   private String abi;
   
   /** The anag utente. */
   private String anagUtente;
   
   /** The fiscal id. */
   private FiscalId fiscalId;
   
   /** The ragione sociale. */
   private String ragioneSociale;

   /**
    * Gets the ragione sociale.
    *
    * @return the ragione sociale
    */
   public String getRagioneSociale() {
      return this.ragioneSociale;
   }

   /**
    * Sets the ragione sociale.
    *
    * @param ragioneSociale the new ragione sociale
    */
   public void setRagioneSociale(String ragioneSociale) {
      this.ragioneSociale = ragioneSociale;
   }

   /**
    * Gets the abi.
    *
    * @return the abi
    */
   public String getAbi() {
      return this.abi;
   }

   /**
    * Sets the abi.
    *
    * @param abi the new abi
    */
   public void setAbi(String abi) {
      this.abi = abi;
   }

   /**
    * Gets the anag utente.
    *
    * @return the anag utente
    */
   public String getAnagUtente() {
      return this.anagUtente;
   }

   /**
    * Sets the anag utente.
    *
    * @param anagUtente the new anag utente
    */
   public void setAnagUtente(String anagUtente) {
      this.anagUtente = anagUtente;
   }

   /**
    * Gets the fiscal id.
    *
    * @return the fiscal id
    */
   public FiscalId getFiscalId() {
      return this.fiscalId;
   }

   /**
    * Sets the fiscal id.
    *
    * @param fiscalId the new fiscal id
    */
   public void setFiscalId(FiscalId fiscalId) {
      this.fiscalId = fiscalId;
   }

   /**
    * Gets the nsg.
    *
    * @return the nsg
    */
   public String getNsg() {
      return this.nsg;
   }

   /**
    * Sets the nsg.
    *
    * @param nsg the new nsg
    */
   public void setNsg(String nsg) {
      this.nsg = nsg;
   }
}

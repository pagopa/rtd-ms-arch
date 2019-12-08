/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class Branch.
 */
public class Branch implements Serializable {
   
   /** The abi. */
   private String abi;
   
   /** The code. */
   private String code;
   
   /** The codice classe gerarchica. */
   private String codiceClasseGerarchica;
   
   /** The codice contesto. */
   private String codiceContesto;
   
   /** The denominazione. */
   private String denominazione;
   
   /** The description. */
   private String description;
   
   /** The fax number. */
   private String faxNumber;
   
   /** The phone number. */
   private String phoneNumber;
   
   /** The address. */
   private Address address;

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
    * Gets the codice classe gerarchica.
    *
    * @return the codice classe gerarchica
    */
   public String getCodiceClasseGerarchica() {
      return this.codiceClasseGerarchica;
   }

   /**
    * Sets the codice classe gerarchica.
    *
    * @param codiceClasseGerarchica the new codice classe gerarchica
    */
   public void setCodiceClasseGerarchica(String codiceClasseGerarchica) {
      this.codiceClasseGerarchica = codiceClasseGerarchica;
   }

   /**
    * Gets the codice contesto.
    *
    * @return the codice contesto
    */
   public String getCodiceContesto() {
      return this.codiceContesto;
   }

   /**
    * Sets the codice contesto.
    *
    * @param codiceContesto the new codice contesto
    */
   public void setCodiceContesto(String codiceContesto) {
      this.codiceContesto = codiceContesto;
   }

   /**
    * Gets the denominazione.
    *
    * @return the denominazione
    */
   public String getDenominazione() {
      return this.denominazione;
   }

   /**
    * Sets the denominazione.
    *
    * @param denominazione the new denominazione
    */
   public void setDenominazione(String denominazione) {
      this.denominazione = denominazione;
   }

   /**
    * Gets the description.
    *
    * @return the description
    */
   public String getDescription() {
      return this.description;
   }

   /**
    * Sets the description.
    *
    * @param description the new description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Gets the fax number.
    *
    * @return the fax number
    */
   public String getFaxNumber() {
      return this.faxNumber;
   }

   /**
    * Sets the fax number.
    *
    * @param faxNumber the new fax number
    */
   public void setFaxNumber(String faxNumber) {
      this.faxNumber = faxNumber;
   }

   /**
    * Gets the phone number.
    *
    * @return the phone number
    */
   public String getPhoneNumber() {
      return this.phoneNumber;
   }

   /**
    * Sets the phone number.
    *
    * @param phoneNumber the new phone number
    */
   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   /**
    * Gets the address.
    *
    * @return the address
    */
   public Address getAddress() {
      return this.address;
   }

   /**
    * Sets the address.
    *
    * @param address the new address
    */
   public void setAddress(Address address) {
      this.address = address;
   }
}

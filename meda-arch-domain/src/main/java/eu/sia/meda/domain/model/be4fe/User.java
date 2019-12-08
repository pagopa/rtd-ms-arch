/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * The Class User.
 */
public class User implements Serializable {
   
   /** The user id. */
   private String userId;
   
   /** The codice abi. */
   private String codiceAbi;
   
   /** The codice filiale. */
   private String codiceFiliale;
   
   /** The name. */
   private String name;
   
   /** The surname. */
   private String surname;
   
   /** The language. */
   private String language;
   
   /** The last access. */
   private Date lastAccess;
   
   /** The settings. */
   private Map<String, String> settings;
   
   /** The authorizations. */
   private Map<String, Boolean> authorizations;
   
   /** The company bank code. */
   private String companyBankCode;

   /**
    * Gets the user id.
    *
    * @return the user id
    */
   public String getUserId() {
      return this.userId;
   }

   /**
    * Sets the user id.
    *
    * @param userId the new user id
    */
   public void setUserId(String userId) {
      this.userId = userId;
   }

   /**
    * Gets the codice abi.
    *
    * @return the codice abi
    */
   public String getCodiceAbi() {
      return this.codiceAbi;
   }

   /**
    * Sets the codice abi.
    *
    * @param codiceAbi the new codice abi
    */
   public void setCodiceAbi(String codiceAbi) {
      this.codiceAbi = codiceAbi;
   }

   /**
    * Gets the codice filiale.
    *
    * @return the codice filiale
    */
   public String getCodiceFiliale() {
      return this.codiceFiliale;
   }

   /**
    * Sets the codice filiale.
    *
    * @param codiceFiliale the new codice filiale
    */
   public void setCodiceFiliale(String codiceFiliale) {
      this.codiceFiliale = codiceFiliale;
   }

   /**
    * Gets the name.
    *
    * @return the name
    */
   public String getName() {
      return this.name;
   }

   /**
    * Sets the name.
    *
    * @param name the new name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Gets the surname.
    *
    * @return the surname
    */
   public String getSurname() {
      return this.surname;
   }

   /**
    * Sets the surname.
    *
    * @param surname the new surname
    */
   public void setSurname(String surname) {
      this.surname = surname;
   }

   /**
    * Gets the language.
    *
    * @return the language
    */
   public String getLanguage() {
      return this.language;
   }

   /**
    * Sets the language.
    *
    * @param language the new language
    */
   public void setLanguage(String language) {
      this.language = language;
   }

   /**
    * Gets the last access.
    *
    * @return the last access
    */
   public Date getLastAccess() {
      return this.lastAccess;
   }

   /**
    * Sets the last access.
    *
    * @param lastAccess the new last access
    */
   public void setLastAccess(Date lastAccess) {
      this.lastAccess = lastAccess;
   }

   /**
    * Gets the settings.
    *
    * @return the settings
    */
   public Map<String, String> getSettings() {
      return this.settings;
   }

   /**
    * Sets the settings.
    *
    * @param settings the settings
    */
   public void setSettings(Map<String, String> settings) {
      this.settings = settings;
   }

   /**
    * Gets the authorizations.
    *
    * @return the authorizations
    */
   public Map<String, Boolean> getAuthorizations() {
      return this.authorizations;
   }

   /**
    * Sets the authorizations.
    *
    * @param authorizations the authorizations
    */
   public void setAuthorizations(Map<String, Boolean> authorizations) {
      this.authorizations = authorizations;
   }

   /**
    * Gets the company bank code.
    *
    * @return the company bank code
    */
   public String getCompanyBankCode() {
      return this.companyBankCode;
   }

   /**
    * Sets the company bank code.
    *
    * @param companyBankCode the new company bank code
    */
   public void setCompanyBankCode(String companyBankCode) {
      this.companyBankCode = companyBankCode;
   }
}

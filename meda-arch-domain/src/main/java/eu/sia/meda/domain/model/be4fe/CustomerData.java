/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class CustomerData.
 */
public class CustomerData implements Serializable {
   
   /** The cliente identita digitale. */
   private boolean clienteIdentitaDigitale;
   
   /** The codice filiale. */
   private String codiceFiliale;
   
   /** The codice filiale propietaria. */
   private String codiceFilialePropietaria;
   
   /** The codice fiscale. */
   private FiscalId codiceFiscale;
   
   /** The identificativo fiscale. */
   private FiscalId identificativoFiscale;
   
   /** The surname. */
   private String surname;
   
   /** The name. */
   private String name;
   
   /** The birth date. */
   private Date birthDate;
   
   /** The language. */
   private String language;
   
   /** The birth place. */
   private String birthPlace;
   
   /** The birth state. */
   private String birthState;
   
   /** The nsg. */
   private String nsg;
   
   /** The birth province. */
   private String birthProvince;
   
   /** The residency. */
   private Residency residency;
   
   /** The segmento economico. */
   private String segmentoEconomico;
   
   /** The gender. */
   private String gender;
   
   /** The customer type. */
   private String customerType;
   
   /** The employee. */
   private Boolean employee;

   /**
    * Checks if is cliente identita digitale.
    *
    * @return true, if is cliente identita digitale
    */
   public boolean isClienteIdentitaDigitale() {
      return this.clienteIdentitaDigitale;
   }

   /**
    * Sets the cliente identita digitale.
    *
    * @param clienteIdentitaDigitale the new cliente identita digitale
    */
   public void setClienteIdentitaDigitale(boolean clienteIdentitaDigitale) {
      this.clienteIdentitaDigitale = clienteIdentitaDigitale;
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
    * Gets the codice filiale propietaria.
    *
    * @return the codice filiale propietaria
    */
   public String getCodiceFilialePropietaria() {
      return this.codiceFilialePropietaria;
   }

   /**
    * Sets the codice filiale propietaria.
    *
    * @param codiceFilialePropietaria the new codice filiale propietaria
    */
   public void setCodiceFilialePropietaria(String codiceFilialePropietaria) {
      this.codiceFilialePropietaria = codiceFilialePropietaria;
   }

   /**
    * Gets the codice fiscale.
    *
    * @return the codice fiscale
    */
   public FiscalId getCodiceFiscale() {
      return this.codiceFiscale;
   }

   /**
    * Sets the codice fiscale.
    *
    * @param codiceFiscale the new codice fiscale
    */
   public void setCodiceFiscale(FiscalId codiceFiscale) {
      this.codiceFiscale = codiceFiscale;
   }

   /**
    * Gets the identificativo fiscale.
    *
    * @return the identificativo fiscale
    */
   public FiscalId getIdentificativoFiscale() {
      return this.identificativoFiscale;
   }

   /**
    * Sets the identificativo fiscale.
    *
    * @param identificativoFiscale the new identificativo fiscale
    */
   public void setIdentificativoFiscale(FiscalId identificativoFiscale) {
      this.identificativoFiscale = identificativoFiscale;
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
    * Gets the birth date.
    *
    * @return the birth date
    */
   public Date getBirthDate() {
      return this.birthDate;
   }

   /**
    * Sets the birth date.
    *
    * @param birthDate the new birth date
    */
   public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
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
    * Gets the birth place.
    *
    * @return the birth place
    */
   public String getBirthPlace() {
      return this.birthPlace;
   }

   /**
    * Sets the birth place.
    *
    * @param birthPlace the new birth place
    */
   public void setBirthPlace(String birthPlace) {
      this.birthPlace = birthPlace;
   }

   /**
    * Gets the birth state.
    *
    * @return the birth state
    */
   public String getBirthState() {
      return this.birthState;
   }

   /**
    * Sets the birth state.
    *
    * @param birthState the new birth state
    */
   public void setBirthState(String birthState) {
      this.birthState = birthState;
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

   /**
    * Gets the birth province.
    *
    * @return the birth province
    */
   public String getBirthProvince() {
      return this.birthProvince;
   }

   /**
    * Sets the birth province.
    *
    * @param birthProvince the new birth province
    */
   public void setBirthProvince(String birthProvince) {
      this.birthProvince = birthProvince;
   }

   /**
    * Gets the residency.
    *
    * @return the residency
    */
   public Residency getResidency() {
      return this.residency;
   }

   /**
    * Sets the residency.
    *
    * @param residency the new residency
    */
   public void setResidency(Residency residency) {
      this.residency = residency;
   }

   /**
    * Gets the segmento economico.
    *
    * @return the segmento economico
    */
   public String getSegmentoEconomico() {
      return this.segmentoEconomico;
   }

   /**
    * Sets the segmento economico.
    *
    * @param segmentoEconomico the new segmento economico
    */
   public void setSegmentoEconomico(String segmentoEconomico) {
      this.segmentoEconomico = segmentoEconomico;
   }

   /**
    * Gets the gender.
    *
    * @return the gender
    */
   public String getGender() {
      return this.gender;
   }

   /**
    * Sets the gender.
    *
    * @param gender the new gender
    */
   public void setGender(String gender) {
      this.gender = gender;
   }

   /**
    * Gets the customer type.
    *
    * @return the customer type
    */
   public String getCustomerType() {
      return this.customerType;
   }

   /**
    * Sets the customer type.
    *
    * @param customerType the new customer type
    */
   public void setCustomerType(String customerType) {
      this.customerType = customerType;
   }

   /**
    * Gets the employee.
    *
    * @return the employee
    */
   public Boolean getEmployee() {
      return this.employee;
   }

   /**
    * Sets the employee.
    *
    * @param employee the new employee
    */
   public void setEmployee(Boolean employee) {
      this.employee = employee;
   }
}

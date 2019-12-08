/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The Class Customer.
 */
public class Customer implements Serializable {
   
   /** The customer data. */
   private CustomerData customerData;
   
   /** The current company. */
   private CompanyData currentCompany;
   
   /** The contact. */
   private Contact contact;
   
   /** The contract. */
   private Contract contract;
   
   /** The account list. */
   private List<Account> accountList;
   
   /** The branch list. */
   private List<Branch> branchList;
   
   /** The ngs registry list. */
   private List<NsgRegistry> ngsRegistryList;
   
   /** The settings. */
   private Map<String, String> settings;
   
   /** The authorizations. */
   private Map<String, Boolean> authorizations;

   /**
    * Gets the customer data.
    *
    * @return the customer data
    */
   public CustomerData getCustomerData() {
      return this.customerData;
   }

   /**
    * Sets the customer data.
    *
    * @param customerData the new customer data
    */
   public void setCustomerData(CustomerData customerData) {
      this.customerData = customerData;
   }

   /**
    * Gets the current company.
    *
    * @return the current company
    */
   public CompanyData getCurrentCompany() {
      return this.currentCompany;
   }

   /**
    * Sets the current company.
    *
    * @param currentCompany the new current company
    */
   public void setCurrentCompany(CompanyData currentCompany) {
      this.currentCompany = currentCompany;
   }

   /**
    * Gets the contact.
    *
    * @return the contact
    */
   public Contact getContact() {
      return this.contact;
   }

   /**
    * Sets the contact.
    *
    * @param contact the new contact
    */
   public void setContact(Contact contact) {
      this.contact = contact;
   }

   /**
    * Gets the contract.
    *
    * @return the contract
    */
   public Contract getContract() {
      return this.contract;
   }

   /**
    * Sets the contract.
    *
    * @param contract the new contract
    */
   public void setContract(Contract contract) {
      this.contract = contract;
   }

   /**
    * Gets the account list.
    *
    * @return the account list
    */
   public List<Account> getAccountList() {
      return this.accountList;
   }

   /**
    * Sets the account list.
    *
    * @param accountList the new account list
    */
   public void setAccountList(List<Account> accountList) {
      this.accountList = accountList;
   }

   /**
    * Gets the branch list.
    *
    * @return the branch list
    */
   public List<Branch> getBranchList() {
      return this.branchList;
   }

   /**
    * Sets the branch list.
    *
    * @param branchList the new branch list
    */
   public void setBranchList(List<Branch> branchList) {
      this.branchList = branchList;
   }

   /**
    * Gets the ngs registry list.
    *
    * @return the ngs registry list
    */
   public List<NsgRegistry> getNgsRegistryList() {
      return this.ngsRegistryList;
   }

   /**
    * Sets the ngs registry list.
    *
    * @param ngsRegistryList the new ngs registry list
    */
   public void setNgsRegistryList(List<NsgRegistry> ngsRegistryList) {
      this.ngsRegistryList = ngsRegistryList;
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
}

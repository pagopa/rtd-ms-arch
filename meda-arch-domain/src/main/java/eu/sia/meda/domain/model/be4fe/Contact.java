/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class Contact.
 */
public class Contact implements Serializable {
   
   /** The mobile. */
   private String mobile;
   
   /** The formatted mobile. */
   private PhoneContact formattedMobile;
   
   /** The email. */
   private String email;
   
   /** The fax. */
   private String fax;
   
   /** The home phone. */
   private String homePhone;
   
   /** The formatted home phone. */
   private PhoneContact formattedHomePhone;
   
   /** The work phone. */
   private String workPhone;
   
   /** The certified mobile phone. */
   private PhoneContact certifiedMobilePhone;

   /**
    * Gets the mobile.
    *
    * @return the mobile
    */
   public String getMobile() {
      return this.mobile;
   }

   /**
    * Sets the mobile.
    *
    * @param mobile the new mobile
    */
   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   /**
    * Gets the formatted mobile.
    *
    * @return the formatted mobile
    */
   public PhoneContact getFormattedMobile() {
      return this.formattedMobile;
   }

   /**
    * Sets the formatted mobile.
    *
    * @param formattedMobile the new formatted mobile
    */
   public void setFormattedMobile(PhoneContact formattedMobile) {
      this.formattedMobile = formattedMobile;
   }

   /**
    * Gets the email.
    *
    * @return the email
    */
   public String getEmail() {
      return this.email;
   }

   /**
    * Sets the email.
    *
    * @param email the new email
    */
   public void setEmail(String email) {
      this.email = email;
   }

   /**
    * Gets the fax.
    *
    * @return the fax
    */
   public String getFax() {
      return this.fax;
   }

   /**
    * Sets the fax.
    *
    * @param fax the new fax
    */
   public void setFax(String fax) {
      this.fax = fax;
   }

   /**
    * Gets the home phone.
    *
    * @return the home phone
    */
   public String getHomePhone() {
      return this.homePhone;
   }

   /**
    * Sets the home phone.
    *
    * @param homePhone the new home phone
    */
   public void setHomePhone(String homePhone) {
      this.homePhone = homePhone;
   }

   /**
    * Gets the formatted home phone.
    *
    * @return the formatted home phone
    */
   public PhoneContact getFormattedHomePhone() {
      return this.formattedHomePhone;
   }

   /**
    * Sets the formatted home phone.
    *
    * @param formattedHomePhone the new formatted home phone
    */
   public void setFormattedHomePhone(PhoneContact formattedHomePhone) {
      this.formattedHomePhone = formattedHomePhone;
   }

   /**
    * Gets the work phone.
    *
    * @return the work phone
    */
   public String getWorkPhone() {
      return this.workPhone;
   }

   /**
    * Sets the work phone.
    *
    * @param workPhone the new work phone
    */
   public void setWorkPhone(String workPhone) {
      this.workPhone = workPhone;
   }

   /**
    * Gets the certified mobile phone.
    *
    * @return the certified mobile phone
    */
   public PhoneContact getCertifiedMobilePhone() {
      return this.certifiedMobilePhone;
   }

   /**
    * Sets the certified mobile phone.
    *
    * @param certifiedMobilePhone the new certified mobile phone
    */
   public void setCertifiedMobilePhone(PhoneContact certifiedMobilePhone) {
      this.certifiedMobilePhone = certifiedMobilePhone;
   }
}

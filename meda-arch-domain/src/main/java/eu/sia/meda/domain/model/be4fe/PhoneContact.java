/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class PhoneContact.
 */
public class PhoneContact implements Serializable {
   
   /** The phone number. */
   private String phoneNumber;
   
   /** The international prefix. */
   private String internationalPrefix;
   
   /** The prefix. */
   private String prefix;

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
    * Gets the international prefix.
    *
    * @return the international prefix
    */
   public String getInternationalPrefix() {
      return this.internationalPrefix;
   }

   /**
    * Sets the international prefix.
    *
    * @param internationalPrefix the new international prefix
    */
   public void setInternationalPrefix(String internationalPrefix) {
      this.internationalPrefix = internationalPrefix;
   }

   /**
    * Gets the prefix.
    *
    * @return the prefix
    */
   public String getPrefix() {
      return this.prefix;
   }

   /**
    * Sets the prefix.
    *
    * @param prefix the new prefix
    */
   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }
}

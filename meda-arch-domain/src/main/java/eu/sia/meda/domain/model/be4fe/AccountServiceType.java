/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class AccountServiceType.
 */
public class AccountServiceType implements Serializable {
   
   /** The service code. */
   private String serviceCode;

   /**
    * Gets the service code.
    *
    * @return the service code
    */
   public String getServiceCode() {
      return this.serviceCode;
   }

   /**
    * Sets the service code.
    *
    * @param serviceCode the new service code
    */
   public void setServiceCode(String serviceCode) {
      this.serviceCode = serviceCode;
   }
}

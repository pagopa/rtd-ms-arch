/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;

/**
 * The Class BranchId.
 */
public class BranchId implements Serializable {
   
   /** The abi. */
   private String abi;
   
   /** The code. */
   private String code;

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
}

/*
 * 
 */
package eu.sia.meda.transaction.dto;

import java.util.Map;

/**
 * The Class SaveDispoInfoRequestPayloadDto.
 */
public class SaveDispoInfoRequestPayloadDto {
   
   /** The info dispo map. */
   Map<String, String> infoDispoMap;
   
   /** The trxid. */
   String trxid;

   /**
    * Gets the info dispo map.
    *
    * @return the info dispo map
    */
   public Map<String, String> getInfoDispoMap() {
      return this.infoDispoMap;
   }

   /**
    * Sets the info dispo map.
    *
    * @param infoDispoMap the info dispo map
    */
   public void setInfoDispoMap(Map<String, String> infoDispoMap) {
      this.infoDispoMap = infoDispoMap;
   }

   /**
    * Gets the trxid.
    *
    * @return the trxid
    */
   public String getTrxid() {
      return this.trxid;
   }

   /**
    * Sets the trxid.
    *
    * @param trxid the new trxid
    */
   public void setTrxid(String trxid) {
      this.trxid = trxid;
   }
}

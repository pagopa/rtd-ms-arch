/*
 * 
 */
package eu.sia.meda.transaction.dto;

/**
 * The Class SaveDispoInfoRequestDto.
 */
public class SaveDispoInfoRequestDto {
   
   /** The payload. */
   SaveDispoInfoRequestPayloadDto payload;

   /**
    * Gets the payload.
    *
    * @return the payload
    */
   public SaveDispoInfoRequestPayloadDto getPayload() {
      return this.payload;
   }

   /**
    * Sets the payload.
    *
    * @param payload the new payload
    */
   public void setPayload(SaveDispoInfoRequestPayloadDto payload) {
      this.payload = payload;
   }
}

package eu.sia.meda.core.model;

import eu.sia.meda.core.model.siaHeaders.SIAWebservicesHeaderType;

/**
 * The Class SIAContext.
 */
public class SIAContext {
	
	/** The header. */
	SIAWebservicesHeaderType header;

   /**
    * Gets the header.
    *
    * @return the header
    */
   public SIAWebservicesHeaderType getHeader() {
      return this.header;
   }

   /**
    * Sets the header.
    *
    * @param header the new header
    */
   public void setHeader(SIAWebservicesHeaderType header) {
      this.header = header;
   }
}

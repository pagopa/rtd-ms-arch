package eu.sia.meda.error.resource;

import eu.sia.meda.exceptions.resource.ErrorMessageResource;

/**
 * The Class ExtendedErrorMessageResource.
 */
public class ExtendedErrorMessageResource extends ErrorMessageResource {
   
   /** The severity. */
   private String severity;
   
   /** The language. */
   private String language;

   /**
    * Gets the severity.
    *
    * @return the severity
    */
   public String getSeverity() {
      return this.severity;
   }

   /**
    * Sets the severity.
    *
    * @param severity the new severity
    */
   public void setSeverity(String severity) {
      this.severity = severity;
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
}

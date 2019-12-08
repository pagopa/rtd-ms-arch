/*
 * 
 */
package eu.sia.meda.exceptions.model;

import java.io.Serializable;

/**
 * The Class MessageForm.
 */
public class MessageForm implements Serializable {
   
   /** The form name. */
   private String formName;
   
   /** The field name. */
   private String fieldName;

   /**
    * Gets the form name.
    *
    * @return the form name
    */
   public String getFormName() {
      return this.formName;
   }

   /**
    * Sets the form name.
    *
    * @param formName the new form name
    */
   public void setFormName(String formName) {
      this.formName = formName;
   }

   /**
    * Gets the field name.
    *
    * @return the field name
    */
   public String getFieldName() {
      return this.fieldName;
   }

   /**
    * Sets the field name.
    *
    * @param fieldName the new field name
    */
   public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
   }
}

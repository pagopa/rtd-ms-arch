package eu.sia.meda.exceptions.model;

import lombok.Data;

/**
 * The Class ArchRestDefaultSpringError.
 */
@Data
public class ArchRestDefaultSpringError {
   
   /** The timestamp. */
   private String timestamp;
   
   /** The status. */
   private String status;
   
   /** The message. */
   private String message;
   
   /** The error. */
   private String error;
   
   /** The exception. */
   private String exception;
   
   /** The path. */
   private String path;

}

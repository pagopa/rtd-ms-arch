package eu.sia.meda.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The Interface IMedaDomainException.
 */
public interface IMedaDomainException {
   
   /**
    * Gets the code.
    *
    * @return the code
    */
   String getCode();

   /**
    * Gets the response status.
    *
    * @return the response status
    */
   HttpStatus getResponseStatus();
}

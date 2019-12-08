package eu.sia.meda.switcher.dto;

import java.io.Serializable;

/**
 * The Class GetSemaphoreResponse.
 */
public class GetSemaphoreResponse implements Serializable {
   
   /** The semaphore. */
   private SemaphoreDto semaphore;

   /**
    * Gets the semaphore.
    *
    * @return the semaphore
    */
   public SemaphoreDto getSemaphore() {
      return this.semaphore;
   }

   /**
    * Sets the semaphore.
    *
    * @param semaphore the new semaphore
    */
   public void setSemaphore(SemaphoreDto semaphore) {
      this.semaphore = semaphore;
   }
}

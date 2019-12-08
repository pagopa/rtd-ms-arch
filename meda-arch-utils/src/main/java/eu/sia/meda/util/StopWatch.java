package eu.sia.meda.util;

/**
 * The Class StopWatch.
 */
public class StopWatch {
   
   /** The start time. */
   private long startTime;
   
   /** The previous elapsed time. */
   private long previousElapsedTime;
   
   /** The is running. */
   private boolean isRunning;

   /**
    * Start.
    */
   public void start() {
      synchronized(this) {
         this.startTime = System.currentTimeMillis();
         this.isRunning = true;
      }
   }

   /**
    * Pause.
    */
   public void pause() {
      synchronized(this) {
         this.previousElapsedTime += System.currentTimeMillis() - this.startTime;
         this.isRunning = false;
      }
   }

   /**
    * Reset.
    */
   public void reset() {
      synchronized(this) {
         this.startTime = 0L;
         this.previousElapsedTime = 0L;
         this.isRunning = false;
      }
   }

   /**
    * Gets the elapsed time.
    *
    * @return the elapsed time
    */
   public final long getElapsedTime() {
      synchronized(this) {
         long currentElapsedTime = 0L;
         if (this.isRunning) {
            currentElapsedTime = System.currentTimeMillis() - this.startTime;
         }

         return this.previousElapsedTime + currentElapsedTime;
      }
   }

   /**
    * To string.
    *
    * @return the string
    */
   public String toString() {
      return String.format("%d millis", this.getElapsedTime());
   }
}

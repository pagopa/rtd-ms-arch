package eu.sia.meda.switcher.dto;

import java.io.Serializable;

/**
 * The Class SemaphoreDto.
 */
public class SemaphoreDto implements Serializable {
   
   /** The enabled. */
   private boolean enabled;
   
   /** The name. */
   private String name;
   
   /** The latency. */
   private long latency;
   
   /** The sample window end. */
   private long sampleWindowEnd;
   
   /** The sample window start. */
   private long sampleWindowStart;

   /**
    * Checks if is enabled.
    *
    * @return true, if is enabled
    */
   public boolean isEnabled() {
      return this.enabled;
   }

   /**
    * Sets the enabled.
    *
    * @param enabled the new enabled
    */
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * Gets the name.
    *
    * @return the name
    */
   public String getName() {
      return this.name;
   }

   /**
    * Sets the name.
    *
    * @param name the new name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Gets the latency.
    *
    * @return the latency
    */
   public long getLatency() {
      return this.latency;
   }

   /**
    * Sets the latency.
    *
    * @param latency the new latency
    */
   public void setLatency(long latency) {
      this.latency = latency;
   }

   /**
    * Gets the sample window end.
    *
    * @return the sample window end
    */
   public long getSampleWindowEnd() {
      return this.sampleWindowEnd;
   }

   /**
    * Sets the sample window end.
    *
    * @param sampleWindowEnd the new sample window end
    */
   public void setSampleWindowEnd(long sampleWindowEnd) {
      this.sampleWindowEnd = sampleWindowEnd;
   }

   /**
    * Gets the sample window start.
    *
    * @return the sample window start
    */
   public long getSampleWindowStart() {
      return this.sampleWindowStart;
   }

   /**
    * Sets the sample window start.
    *
    * @param sampleWindowStart the new sample window start
    */
   public void setSampleWindowStart(long sampleWindowStart) {
      this.sampleWindowStart = sampleWindowStart;
   }
}

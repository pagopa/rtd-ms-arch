package eu.sia.meda.switcher;

/**
 * The Class CopySwitcherMessage.
 */
public class CopySwitcherMessage {
   
   /** The Constant KAFKA_HEADER_TIMESTAMP. */
   public static final String KAFKA_HEADER_TIMESTAMP = "TIMESTAMP";
   
   /** The name. */
   private String name;
   
   /** The latency. */
   private Long latency;
   
   /** The enabled. */
   private boolean enabled;

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
   public Long getLatency() {
      return this.latency;
   }

   /**
    * Sets the latency.
    *
    * @param latency the new latency
    */
   public void setLatency(Long latency) {
      this.latency = latency;
   }

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
}

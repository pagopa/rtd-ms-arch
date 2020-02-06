package eu.sia.meda.core.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.sia.meda.exceptions.resource.ErrorResource;

/**
 * The Class BaseResource.
 */
public class BaseResource extends ErrorResource {
   
   /** The entity id. */
   private String entityId;

   /**
    * Gets the entity id.
    *
    * @return the entity id
    */
   @JsonProperty("id")
   public String getEntityId() {
      return this.entityId;
   }

   /**
    * Sets the entity id.
    *
    * @param entityId the new entity id
    */
   public void setEntityId(String entityId) {
      this.entityId = entityId;
   }

}

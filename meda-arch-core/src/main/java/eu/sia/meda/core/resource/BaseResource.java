package eu.sia.meda.core.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.sia.meda.exceptions.resource.ErrorResource;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * The Class BaseResource.
 */
@Data
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

   @Column(name = "INSERT_DATE")
   private LocalDateTime insertDate;

   @Column(name = "INSERT_USER")
   private String insertUser;

   @Column(name = "UPDATE_DATE")
   private LocalDateTime updateDate;

   @Column(name = "UPDATE_USER")
   private String updateUser;

   @Column(name = "ENABLED")
   private boolean enabled = true;
}

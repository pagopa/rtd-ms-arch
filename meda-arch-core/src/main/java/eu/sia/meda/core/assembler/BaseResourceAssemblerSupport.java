package eu.sia.meda.core.assembler;

import eu.sia.meda.config.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * The Class BaseResourceAssemblerSupport.
 *
 * @param <T> the generic type
 * @param <D> the generic type
 */
public abstract class BaseResourceAssemblerSupport<T, D extends ResourceSupport> extends ResourceAssemblerSupport<T, D> {
   
   /** The logger. */
   protected final Logger logger = LoggerUtils.getLogger(this.getClass());

   /**
    * Instantiates a new base resource assembler support.
    *
    * @param controllerClass the controller class
    * @param resourceType the resource type
    */
   public BaseResourceAssemblerSupport(Class<?> controllerClass, Class<D> resourceType) {
      super(controllerClass, resourceType);
   }

   /**
    * Builds the resource path.
    *
    * @param id the id
    * @param resourceCollectionName the resource collection name
    * @return the string
    */
   public static String buildResourcePath(String id, String resourceCollectionName) {
      return "/" + resourceCollectionName + "/" + id;
   }

   /**
    * Gets the id from resource link.
    *
    * @param link the link
    * @return the id from resource link
    */
   public static String getIdFromResourceLink(Link link) {
      return link.getHref().replaceFirst(".*/([^/?]+).*", "$1");
   }
}

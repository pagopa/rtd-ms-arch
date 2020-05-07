package eu.sia.meda.core.model;

import eu.sia.meda.config.LoggerUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;

/**
 * The Class ErrorContext.
 */
public class ErrorContext {
   
   /** The logger. */
   Logger logger = LoggerUtils.getLogger(ErrorContext.class);
   
   /** The project id. */
   private String projectId;
   
   /** The error keys. */
   private Set<String> errorKeys;

   /**
    * Instantiates a new error context.
    *
    * @param projectId the project id
    */
   public ErrorContext(String projectId) {
      this.projectId = projectId;
      this.errorKeys = Collections.synchronizedSet(new HashSet());
   }

   /**
    * Gets the error keys.
    *
    * @return the error keys
    */
   public Set<String> getErrorKeys() {
      return new HashSet<>(this.errorKeys);
   }

   /**
    * Adds the error key.
    *
    * @param errorKey the error key
    */
   public void addErrorKey(String errorKey) {
      this.logger.info("adding errorKey: {}", errorKey);
      this.errorKeys.add(this.formatErrorKey(errorKey));
   }

   /**
    * Adds the error keys.
    *
    * @param errorKeys the error keys
    */
   public void addErrorKeys(Collection<String> errorKeys) {
		((Collection) Objects.requireNonNull(errorKeys, "null error keys")).forEach((k) -> {
			this.logger.info("adding errorKey: {}", k);
		});
		this.errorKeys.addAll((Collection) errorKeys.stream().filter(k -> k != null).map(k -> this.formatErrorKey(k))
				.collect(Collectors.toSet()));
	}

   /**
    * Removes the error key.
    *
    * @param errorKey the error key
    */
   public void removeErrorKey(String errorKey) {
      this.logger.info("removing errorKey: {}", errorKey);
      this.errorKeys.remove(this.formatErrorKey(errorKey));
   }

   /**
    * Removes the error keys.
    *
    * @param errorKeys the error keys
    */
	public void removeErrorKeys(Collection<String> errorKeys) {
		errorKeys.forEach(k -> this.logger.info("removing errorKey: {}", k));
		this.errorKeys.removeAll(errorKeys.stream().filter((k) -> {
			return k != null;
		}).map(k -> this.formatErrorKey(k)).collect(Collectors.toSet()));
	}

   /**
    * Format error key.
    *
    * @param errorKey the error key
    * @return the string
    */
   private String formatErrorKey(String errorKey) {
      return (Objects.requireNonNull(errorKey, "null errorKey")).toLowerCase();
   }
}

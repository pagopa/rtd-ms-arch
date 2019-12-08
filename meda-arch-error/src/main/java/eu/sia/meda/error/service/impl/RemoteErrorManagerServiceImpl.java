package eu.sia.meda.error.service.impl;

import eu.sia.meda.error.cache.MedaRemoteErrorCache;
import eu.sia.meda.error.condition.RemoteErrorManagerCondition;
import eu.sia.meda.error.config.LocalErrorConfig;
import eu.sia.meda.error.connector.GetErrorMessagesRestConnector;
import eu.sia.meda.error.connector.transformer.GetErrorMessagesRestRequestTransformer;
import eu.sia.meda.error.connector.transformer.GetErrorMessagesRestResponseTransformer;
import eu.sia.meda.error.service.ErrorManagerService;
import eu.sia.meda.exceptions.model.ErrorMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class RemoteErrorManagerServiceImpl.
 */
@Service
@Conditional({RemoteErrorManagerCondition.class})
public class RemoteErrorManagerServiceImpl extends ErrorManagerService {
   
   /** The Constant DEFAULT_LANGUAGE. */
   private static final String DEFAULT_LANGUAGE = "IT";
   
   /** The local error config. */
   @Autowired
   private LocalErrorConfig localErrorConfig;
   
   /** The meda remote error cache. */
   @Autowired
   private MedaRemoteErrorCache medaRemoteErrorCache;
   
   /** The get error messages rest connector. */
   @Autowired
   private GetErrorMessagesRestConnector getErrorMessagesRestConnector;
   
   /** The get error messages rest request transformer. */
   @Autowired
   private GetErrorMessagesRestRequestTransformer getErrorMessagesRestRequestTransformer;
   
   /** The get error messages rest response transformer. */
   @Autowired
   private GetErrorMessagesRestResponseTransformer getErrorMessagesRestResponseTransformer;

   /**
    * Gets the error messages.
    *
    * @param errorKeys the error keys
    * @param defaultSeverity the default severity
    * @return the error messages
    */
   public Map<String, List<ErrorMessage>> getErrorMessages(Set<String> errorKeys, String defaultSeverity) {
      if (errorKeys == null) {
         return new HashMap<>();
      } else {
         errorKeys.removeAll(Collections.singletonList((Object)null));
         if (errorKeys.isEmpty()) {
            return new HashMap<>();
         } else {
            Map<String, ErrorMessage> errorMap = this.localErrorConfig.getMessages();

            try {
               Map<String, List<ErrorMessage>> remoteCachedErrorMap = this.medaRemoteErrorCache.getAll(errorKeys);
               String language = DEFAULT_LANGUAGE;
               errorMap.putAll(this.filterErrorMessages(remoteCachedErrorMap, language));
					Set<String> missingErrorKeys = errorKeys.stream().filter(k -> !remoteCachedErrorMap.containsKey(k)).collect(Collectors.toSet());
               if (!missingErrorKeys.isEmpty()) {
                  Map<String, List<ErrorMessage>> remoteErrorListMap = this.getErrorMessagesRestConnector.call(missingErrorKeys, this.getErrorMessagesRestRequestTransformer, this.getErrorMessagesRestResponseTransformer, 0);
                  missingErrorKeys.forEach(key -> {
                     if (!remoteErrorListMap.containsKey(key)) {
                        remoteErrorListMap.put(key, new ArrayList<>());
                     }
                  });
                  this.medaRemoteErrorCache.putAll(remoteErrorListMap);
                  errorMap.putAll(this.filterErrorMessages(remoteErrorListMap, language));
               }
            } catch (Exception var8) {
               this.logger.error("error retrieving remote error messages: {}", var8);
            }

            return this.toSeverityMap(errorKeys, errorMap, defaultSeverity);
         }
      }
   }

   /**
    * Filter error messages.
    *
    * @param errorListMap the error list map
    * @param language the language
    * @return the map
    */
   private Map<String, ErrorMessage> filterErrorMessages(Map<String, List<ErrorMessage>> errorListMap, String language) {
      if (errorListMap == null) {
         return new HashMap<>();
      } else {
         Map<String, ErrorMessage> errorMap = new HashMap<>();
         Iterator<Entry<String, List<ErrorMessage>>> var4 = errorListMap.entrySet().iterator();

         while(var4.hasNext()) {
            Entry<String, List<ErrorMessage>> entry = var4.next();
            ErrorMessage message = this.getErrorMessageByLanguage(entry.getValue(), language);
            if (message != null) {
               errorMap.put(entry.getKey(), message);
            }
         }

         return errorMap;
      }
   }

   /**
    * Gets the error message by language.
    *
    * @param errorList the error list
    * @param language the language
    * @return the error message by language
    */
   private ErrorMessage getErrorMessageByLanguage(List<ErrorMessage> errorList, String language) {
      if (errorList != null && !errorList.isEmpty()) {
			if (errorList.stream().anyMatch(m -> language.equals(m.getLanguage()))) {
				return errorList.stream().filter(m -> language.equals(m.getLanguage())).findFirst().orElse(null);
         } else {
				return errorList.stream().anyMatch(m -> this.isDefaultLanguage(m.getLanguage())) ? errorList.stream()
						.filter(m -> this.isDefaultLanguage(m.getLanguage())).findFirst().orElse(null) : null;
	         }
      } else {
         return null;
      }
   }

   /**
    * Checks if is default language.
    *
    * @param language the language
    * @return true, if is default language
    */
   private boolean isDefaultLanguage(String language) {
      return language == null || language.isEmpty() || "IT".equalsIgnoreCase(language);
   }
}

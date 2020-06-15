package eu.sia.meda.error.service.impl;

import eu.sia.meda.error.config.LocalErrorConfig;
import eu.sia.meda.error.service.ErrorManagerService;
import eu.sia.meda.exceptions.model.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The Class LocalErrorManagerServiceImpl.
 */
@Service
public class LocalErrorManagerServiceImpl extends ErrorManagerService {

    /**
     * The local error config.
     */
    @Autowired
    private LocalErrorConfig localErrorConfig;

    /**
     * Gets the error messages.
     *
     * @param errorKeys       the error keys
     * @param defaultSeverity the default severity
     * @return the error messages
     */
   public Map<String, List<ErrorMessage>> getErrorMessages(Set<String> errorKeys, String defaultSeverity) {
      if (errorKeys == null) {
         return new HashMap<>();
      } else {
          errorKeys.removeAll(Collections.singletonList((Object) null));
         if (errorKeys.isEmpty()) {
            return new HashMap<>();
         } else {
            Map<String, ErrorMessage> errorMap = this.localErrorConfig.getMessages();
            return this.toSeverityMap(errorKeys, errorMap, defaultSeverity);
         }
      }
   }
}

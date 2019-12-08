package eu.sia.meda.async.util;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.interceptors.RequestContextHolder;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.AuthorizationContext;
import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.core.model.ErrorContext;
import eu.sia.meda.core.model.SIAContext;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * The Class AsyncUtils.
 */
@Service
public class AsyncUtils {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(AsyncUtils.class);

   /**
    * Call async service.
    *
    * @param <F> the generic type
    * @param supplier the supplier
    * @param siaContext the sia context
    * @param applicationContext the application context
    * @param authorizationContext the authorization context
    * @param errorContext the error context
    * @param sessionContext the session context
    * @param request the request
    * @return the completable future
    */
   @Async
   public <F> CompletableFuture<F> callAsyncService(Supplier<F> supplier, SIAContext siaContext, ApplicationContext applicationContext, AuthorizationContext authorizationContext, ErrorContext errorContext, BaseContext sessionContext, HttpServletRequest request) {
      if (applicationContext != null) {
         BaseContextHolder.forceSetApplicationContext(applicationContext);
      }

      if (authorizationContext != null) {
         BaseContextHolder.forceSetAuthorizationContext(authorizationContext);
      }

      if (siaContext != null) {
         BaseContextHolder.forceSetSiaContext(siaContext);
      }

      if (errorContext != null) {
         BaseContextHolder.forceSetErrorContext(errorContext);
      }

      if (sessionContext != null) {
         BaseContextHolder.forceSetSessionContext(sessionContext);
      }

      if (request != null) {
         RequestContextHolder.forceSetRequest(request);
      }

      if(log.isInfoEnabled()) {
    	  log.info(LoggerUtils.formatArchRow("Thread name: {} throws callAsync <CompletableFuture>"), Thread.currentThread().getName());
      }
      return CompletableFuture.completedFuture(supplier.get());
   }
}

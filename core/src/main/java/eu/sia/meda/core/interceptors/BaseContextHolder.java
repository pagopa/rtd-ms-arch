package eu.sia.meda.core.interceptors;

import java.util.Objects;

import org.slf4j.Logger;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.core.model.AuthorizationContext;
import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.core.model.ErrorContext;

/**
 * The Class BaseContextHolder.
 */
public abstract class BaseContextHolder {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(BaseContextHolder.class);
   
   /** The Constant applicationContext. */
   private static final InheritableThreadLocal<ApplicationContext> applicationContext = new InheritableThreadLocal<>();
   
   /** The Constant sessionContext. */
   private static final InheritableThreadLocal<BaseContext> sessionContext = new InheritableThreadLocal<>();
   
   /** The Constant authorizationContext. */
   private static final InheritableThreadLocal<AuthorizationContext> authorizationContext = new InheritableThreadLocal<>();
   
//   /** The Constant siaContext. */
//   private static final InheritableThreadLocal<SIAContext> siaContext = new InheritableThreadLocal<>();
//   
   /** The Constant errorContext. */
   private static final InheritableThreadLocal<ErrorContext> errorContext = new InheritableThreadLocal<>();

   /**
    * Instantiates a new base context holder.
    */
   protected BaseContextHolder() {
   }

   /**
    * Gets the application context.
    *
    * @return the application context
    */
   public static ApplicationContext getApplicationContext() {
      ApplicationContext applicationContext = BaseContextHolder.applicationContext.get();
      if (applicationContext == null) {
         applicationContext = ApplicationContext.initWithDefault();
         log.debug("Thread {}-{}, null ApplicationContext: returning default initialization", Thread.currentThread().getName(), Thread.currentThread().getId());
      }

      return applicationContext;
   }

   /**
    * Gets the session context.
    *
    * @param <T> the generic type
    * @return the session context
    */
   public static <T extends BaseContext> T getSessionContext() {
      return (T)sessionContext.get();
   }

   /**
    * Gets the authorization context.
    *
    * @return the authorization context
    */
   public static AuthorizationContext getAuthorizationContext() {
      return authorizationContext.get();
   }

   /**
    * Sets the session context.
    *
    * @param <T> the generic type
    * @param session the new session context
    */
   public static <T extends BaseContext> void setSessionContext(T session) {
      Objects.requireNonNull(session, "SessionContext cannot be null");
      if (sessionContext.get() != null) {
         throw new IllegalStateException("SessionContext already initialized");
      } else {
         log.debug("Thread {}-{}, init SessionContext variable", Thread.currentThread().getName(), Thread.currentThread().getId());
         sessionContext.set(session);
      }
   }

   /**
    * Force set session context.
    *
    * @param <T> the generic type
    * @param session the session
    */
   public static <T extends BaseContext> void forceSetSessionContext(T session) {
      Objects.requireNonNull(session, "SessionContext cannot be null");
      log.debug("Forcing setting of session context in Thread {}-{} ", Thread.currentThread().getName(), Thread.currentThread().getId());
      sessionContext.set(session);
   }

   /**
    * Sets the application context.
    *
    * @param application the new application context
    */
   public static void setApplicationContext(ApplicationContext application) {
      Objects.requireNonNull(application, "ApplicationContext cannot be null");
      if (applicationContext.get() != null) {
         throw new IllegalStateException("ApplicationContext already initialized");
      } else {
         log.debug("Thread {}-{}, init ApplicationContext variable", Thread.currentThread().getName(), Thread.currentThread().getId());
         applicationContext.set(application);
      }
   }

   /**
    * Force set application context.
    *
    * @param application the application
    */
   public static void forceSetApplicationContext(ApplicationContext application) {
      log.debug("Forcing setting of application context on Thread {}-{}", Thread.currentThread().getName(), Thread.currentThread().getId());
      applicationContext.set(application);
   }

   /**
    * Sets the authorization context.
    *
    * @param authorization the new authorization context
    */
   public static void setAuthorizationContext(AuthorizationContext authorization) {
      Objects.requireNonNull(authorization, "AuthorizationContext cannot be null");
      if (authorizationContext.get() != null) {
         throw new IllegalStateException("AuthorizationContext already initialized");
      } else {
         log.debug("Thread {}-{}, init AuthorizationContext variable", Thread.currentThread().getName(), Thread.currentThread().getId());
         authorizationContext.set(authorization);
      }
   }

   /**
    * Force set authorization context.
    *
    * @param authorization the authorization
    */
   public static void forceSetAuthorizationContext(AuthorizationContext authorization) {
      Objects.requireNonNull(authorization, "AuthorizationContext cannot be null");
      log.debug("Forcing setting of authorization context in Thread {}-{}", Thread.currentThread().getName(), Thread.currentThread().getId());
      authorizationContext.set(authorization);
   }

//   /**
//    * Sets the sia context.
//    *
//    * @param siaCont the new sia context
//    */
//   public static void setSiaContext(SIAContext siaCont) {
//      Objects.requireNonNull(siaCont, "SIAContext cannot be null");
//      if (siaContext.get() != null) {
//         throw new IllegalStateException("SIAContext already initialized");
//      } else {
//         log.debug("Thread {}-{}, init SIAContext variable", Thread.currentThread().getName(), Thread.currentThread().getId());
//         siaContext.set(siaCont);
//      }
//   }

//   /**
//    * Force set sia context.
//    *
//    * @param siaCont the sia cont
//    */
//   public static void forceSetSiaContext(SIAContext siaCont) {
//      Objects.requireNonNull(siaCont, "SIAContext cannot be null");
//      log.debug("Forcing setting of siaContext in Thread {}-{} ", Thread.currentThread().getName(), Thread.currentThread().getId());
//      siaContext.set(siaCont);
//   }

//   /**
//    * Gets the SIA context.
//    *
//    * @return the SIA context
//    */
//   public static SIAContext getSIAContext() {
//      return siaContext.get();
//   }

   /**
    * Sets the error context.
    *
    * @param error the new error context
    */
   public static void setErrorContext(ErrorContext error) {
      Objects.requireNonNull(error, "ErrorContext cannot be null");
      if (errorContext.get() != null) {
         throw new IllegalStateException("ErrorContext already initialized");
      } else {
         log.debug("Thread {}-{}, init ErrorContext variable", Thread.currentThread().getName(), Thread.currentThread().getId());
         errorContext.set(error);
      }
   }

   /**
    * Force set error context.
    *
    * @param error the error
    */
   public static void forceSetErrorContext(ErrorContext error) {
      Objects.requireNonNull(error, "ErrorContext cannot be null");
      log.debug("Forcing setting of ErrorContext in Thread {}-{} ", Thread.currentThread().getName(), Thread.currentThread().getId());
      errorContext.set(error);
   }

   /**
    * Gets the error context.
    *
    * @return the error context
    */
   public static ErrorContext getErrorContext() {
      return errorContext.get();
   }

   /**
    * Clear.
    */
   public static void clear() {
      log.debug("Thread {}-{}, clear Threadlocal variables", Thread.currentThread().getName(), Thread.currentThread().getId());

      try {
         sessionContext.remove();
      } catch (Exception var5) {
         log.warn("Swallowed error while clearing SessionContext", var5);
      }

      try {
         applicationContext.remove();
      } catch (Exception var4) {
         log.warn("Swallowed error while clearing ApplicationContext", var4);
      }

      try {
         authorizationContext.remove();
      } catch (Exception var3) {
         log.warn("Swallowed error while clearing AuthorizationContext", var3);
      }

//      try {
//         siaContext.remove();
//      } catch (Exception var2) {
//         log.warn("Swallowed error while clearing SIA Context", var2);
//      }

      try {
         errorContext.remove();
      } catch (Exception var1) {
         log.warn("Swallowed error while clearing ErrorContext", var1);
      }

   }
}

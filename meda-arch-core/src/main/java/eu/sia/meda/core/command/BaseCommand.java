package eu.sia.meda.core.command;

import eu.sia.meda.async.util.AsyncUtils;
import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.interceptors.RequestContextHolder;
import eu.sia.meda.core.model.ApplicationContext;
import eu.sia.meda.exceptions.MedaForbiddenException;
import eu.sia.meda.exceptions.MedaRollbackException;
import eu.sia.meda.exceptions.MedaTransactionException;
import eu.sia.meda.switcher.CopySwitcherSemaphoreRegistry;
import eu.sia.meda.transactions.MedaTransactionManager;
import eu.sia.meda.transactions.TransactionStep;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * The Class BaseCommand.
 *
 * @param <R> the generic type
 */
public abstract class BaseCommand<R> implements Command<R> {
   
   /** The logger. */
   protected final Logger logger = LoggerFactory.getLogger(this.getClass());
   
   /** The meda transaction manager. */
   private MedaTransactionManager medaTransactionManager;
   
   /** The trx ID. */
   private String trxID;
   
   /** The Constant DUMMY_ROLLBACK_STEP. */
   protected static final Supplier<Void> DUMMY_ROLLBACK_STEP = () -> {
      return null;
   };
   
   /** The application context. */
   @Autowired
   protected ApplicationContext applicationContext;
   
   /** The async utils. */
   @Autowired
   protected AsyncUtils asyncUtils;
   
   /** The copy switcher. */
   @Autowired
   private CopySwitcherSemaphoreRegistry copySwitcher;

   /**
    * Can execute.
    *
    * @return true, if successful
    * @throws MedaForbiddenException the meda forbidden exception
    */
   protected boolean canExecute() throws MedaForbiddenException {
      return true;
   }

   /**
    * Do execute.
    *
    * @return the r
    * @throws Exception the exception
    */
   protected R doExecute() throws Exception {
      return null;
   }

   /**
    * Execute.
    *
    * @return the r
    * @throws Exception the exception
    */
   public R execute() throws Exception {
      if (this.canExecute()) {
         return this.doExecute();
      } else {
         throw new MedaForbiddenException(LoggerUtils.formatArchRow("Cannot execute command"));
      }
   }

   /**
    * Gets the meda transaction manager.
    *
    * @return the meda transaction manager
    */
   private MedaTransactionManager getMedaTransactionManager() {
      if (this.medaTransactionManager == null) {
         this.medaTransactionManager = new MedaTransactionManager();
      }

      return this.medaTransactionManager;
   }

   /**
    * Gets the instance transaction ID.
    *
    * @return the instance transaction ID
    */
   private String getInstanceTransactionID() {
      if (this.trxID == null) {
         if (this.applicationContext.getTransactionId() != null) {
            this.trxID = this.applicationContext.getTransactionId();
         } else {
            this.trxID = UUID.randomUUID().toString();
         }
      }

      return this.trxID;
   }

   /**
    * Call async service.
    *
    * @param <F> the generic type
    * @param t the t
    * @return the completable future
    */
   @Async
   public <F> CompletableFuture<F> callAsyncService(Supplier<F> t) {
      return this.asyncUtils.callAsyncService(t, BaseContextHolder.getSIAContext(), BaseContextHolder.getApplicationContext(), BaseContextHolder.getAuthorizationContext(), BaseContextHolder.getErrorContext(), BaseContextHolder.getSessionContext(), RequestContextHolder.getRequest());
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param <G> the generic type
    * @param t the t
    * @param b the b
    * @return the f
    * @throws Exception the exception
    */
   protected <F, G> F callInTransaction(Supplier<F> t, Supplier<G> b) throws Exception {
      return this.callInTransaction(t, b, this.getInstanceTransactionID());
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param <G> the generic type
    * @param t the t
    * @param b the b
    * @return the f
    * @throws Exception the exception
    */
   protected <F, G> F callInTransaction(Supplier<F> t, Consumer<G> b) throws Exception {
      return this.callInTransaction(t, b, this.getInstanceTransactionID());
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param f the f
    * @return the f
    * @throws Exception the exception
    */
   protected <F> F callInTransaction(Supplier<F> f) throws Exception {
      return this.callInTransaction(f, this.getInstanceTransactionID());
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param <G> the generic type
    * @param f the f
    * @param g the g
    * @throws Exception the exception
    */
   protected <F, G> void callInTransaction(Consumer<F> f, Consumer<G> g) throws Exception {
      this.callInTransaction(f, g, this.getInstanceTransactionID());
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param f the f
    * @throws Exception the exception
    */
   protected <F> void callInTransaction(Consumer<F> f) throws Exception {
      this.callInTransaction(f, this.getInstanceTransactionID());
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param <G> the generic type
    * @param t the t
    * @param b the b
    * @param transactionId the transaction id
    * @return the f
    * @throws Exception the exception
    */
   protected <F, G> F callInTransaction(Supplier<F> t, Supplier<G> b, String transactionId) throws Exception {
      try {
         F returnvalue = t.get();
         this.getMedaTransactionManager().appendStep(transactionId, new TransactionStep<>(t, b));
         return returnvalue;
      } catch (Exception var7) {
         this.logger.error(LoggerUtils.formatArchRow("Exception in transaction execution"), var7);

         try {
            this.logger.debug(LoggerUtils.formatArchRow("Transaction map {}"), this.getMedaTransactionManager().toString());
            this.getMedaTransactionManager().rollback(transactionId);
         } catch (Exception var6) {
            throw new MedaRollbackException("Exception in transaction rollback", var6);
         }

         throw new MedaTransactionException("Exception in transaction execution", var7);
      }
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param <G> the generic type
    * @param t the t
    * @param b the b
    * @param transactionId the transaction id
    * @return the f
    * @throws Exception the exception
    */
   protected <F, G> F callInTransaction(Supplier<F> t, Consumer<G> b, String transactionId) throws Exception {
      Supplier<Void> rollback = () -> {
         b.accept(null);
         return null;
      };
      return this.callInTransaction(t, rollback, transactionId);
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param <G> the generic type
    * @param t the t
    * @param b the b
    * @param transactionId the transaction id
    * @throws Exception the exception
    */
   protected <F, G> void callInTransaction(Consumer<F> t, Consumer<G> b, String transactionId) throws Exception {
      Supplier<Void> step = () -> {
         t.accept(null);
         return null;
      };
      Supplier<Void> rollback = () -> {
         b.accept(null);
         return null;
      };
      this.callInTransaction(step, rollback, transactionId);
   }

   /**
    * Call in transaction.
    *
    * @param <F> the generic type
    * @param t the t
    * @param transactionId the transaction id
    * @return the f
    * @throws Exception the exception
    */
   protected <F> F callInTransaction(Supplier<F> t, String transactionId) throws Exception {
      return this.callInTransaction(t, DUMMY_ROLLBACK_STEP, transactionId);
   }

   /**
    * Call in transaction.
    *
    * @param <G> the generic type
    * @param t the t
    * @param transactionId the transaction id
    * @throws Exception the exception
    */
   protected <G> void callInTransaction(Consumer<G> t, String transactionId) throws Exception {
      Supplier<Void> step = () -> {
         t.accept(null);
         return null;
      };
      this.callInTransaction(step, DUMMY_ROLLBACK_STEP, transactionId);
   }

}

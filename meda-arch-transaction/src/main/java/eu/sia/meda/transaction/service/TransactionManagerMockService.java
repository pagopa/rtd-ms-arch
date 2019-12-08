/*
 * 
 */
package eu.sia.meda.transaction.service;

import eu.sia.meda.service.BaseService;
import eu.sia.meda.transaction.cache.TransactionMockCache;
import eu.sia.meda.transaction.condition.TransactionManagerMockedCondition;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * The Class TransactionManagerMockService.
 *
 * @param <T> the generic type
 */
@Service
@Conditional({TransactionManagerMockedCondition.class})
public class TransactionManagerMockService<T> extends BaseService implements TransactionManagerService<T> {
   
   /** The logger. */
   protected final Logger logger = LoggerFactory.getLogger(this.getClass());
   
   /** The transaction mock cache. */
   @Autowired
   TransactionMockCache<T> transactionMockCache;

   /**
    * Generate id op.
    *
    * @return the string
    */
   private String generateIdOp() {
      UUID uuid = UUID.randomUUID();
      return uuid.toString();
   }

   /**
    * Save transaction info.
    *
    * @param idOp the id op
    * @param transactionInfo the transaction info
    * @return the string
    */
   private String saveTransactionInfo(String idOp, T transactionInfo) {
      this.logger.debug("saving transactionInfo with idOp: {}", idOp);

      try {
         if (!this.transactionMockCache.putIfAbsent(idOp, transactionInfo)) {
            throw new IllegalArgumentException("idOp already existing");
         } else {
            this.logger.info("cache entry inserted");
            return idOp;
         }
      } catch (Exception var4) {
         this.logger.error("error inserting cache entry: ", var4);
         throw var4;
      }
   }

   /**
    * Put transaction info.
    *
    * @param idOp the id op
    * @param transactionInfo the transaction info
    * @param dispoValues the dispo values
    * @return the string
    */
   public String putTransactionInfo(String idOp, T transactionInfo, Map<String, String> dispoValues) {
      if (transactionInfo == null) {
         throw new IllegalArgumentException("transactionInfo is null");
      } else if (idOp != null && !idOp.isEmpty()) {
         return this.saveTransactionInfo(idOp, transactionInfo);
      } else {
         throw new IllegalArgumentException("idOp is empty");
      }
   }

   /**
    * Put transaction info.
    *
    * @param transactionInfo the transaction info
    * @param dispoValues the dispo values
    * @return the string
    */
   public String putTransactionInfo(T transactionInfo, Map<String, String> dispoValues) {
      if (transactionInfo == null) {
         throw new IllegalArgumentException("transactionInfo is null");
      } else {
         String idOp = this.generateIdOp();
         return this.saveTransactionInfo(idOp, transactionInfo);
      }
   }

   /**
    * Gets the transaction info.
    *
    * @param idOp the id op
    * @return the transaction info
    */
   public T getTransactionInfo(String idOp) {
      this.logger.debug("getting transaction info having idOp {}", idOp);
      if (idOp != null && !idOp.isEmpty()) {
         try {
            T transactionInfo = this.transactionMockCache.get(idOp);
            if (transactionInfo == null) {
               throw new IllegalArgumentException(idOp);
            } else {
               this.logger.info("cache entry found");
               return transactionInfo;
            }
         } catch (Exception var3) {
            this.logger.error("error retrieving cache entry: ", var3);
            throw var3;
         }
      } else {
         throw new IllegalArgumentException("idOp is empty");
      }
   }

   /**
    * Delete transaction info.
    *
    * @param idOp the id op
    * @return true, if successful
    */
   public boolean deleteTransactionInfo(String idOp) {
      this.logger.debug("deleting transactionInfo: {}", idOp);

      try {
         if (idOp != null && !idOp.isEmpty()) {
            this.transactionMockCache.remove(idOp);
            this.logger.info("cache entry removed");
            return true;
         } else {
            throw new IllegalArgumentException("idOp is empty");
         }
      } catch (Exception var3) {
         this.logger.error("error removing cache entry: ", var3);
         return false;
      }
   }

   /**
    * Sets the clazz.
    *
    * @param type the new clazz
    */
   public void setClazz(Type type) {
   }
}

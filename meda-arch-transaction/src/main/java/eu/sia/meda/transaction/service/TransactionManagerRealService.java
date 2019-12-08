/*
 * 
 */
package eu.sia.meda.transaction.service;

import eu.sia.meda.service.BaseService;
import eu.sia.meda.transaction.condition.TransactionManagerRealCondition;
import eu.sia.meda.transaction.connector.DeleteTransactionInfoRestConnector;
import eu.sia.meda.transaction.connector.GetTransactionInfoRestConnector;
import eu.sia.meda.transaction.connector.SaveDispoInfoRestConnector;
import eu.sia.meda.transaction.connector.SaveTransactionInfoRestConnector;
import eu.sia.meda.transaction.connector.transformer.DeleteTransactionInfoRestRequestTransformer;
import eu.sia.meda.transaction.connector.transformer.DeleteTransactionInfoRestResponseTransformer;
import eu.sia.meda.transaction.connector.transformer.GetTransactionInfoRestRequestTransformer;
import eu.sia.meda.transaction.connector.transformer.GetTransactionInfoRestResponseTransformer;
import eu.sia.meda.transaction.connector.transformer.SaveDispoInfoRestRequestTransformer;
import eu.sia.meda.transaction.connector.transformer.SaveDispoInfoRestResponseTransformer;
import eu.sia.meda.transaction.connector.transformer.SaveTransactionInfoRestRequestTransformer;
import eu.sia.meda.transaction.connector.transformer.SaveTransactionInfoRestResponseTransformer;
import eu.sia.meda.transaction.model.SaveDispoInfoRequest;
import eu.sia.meda.transaction.model.SaveDispoInfoResponse;
import eu.sia.meda.transaction.model.TransactionInfoWithIdOp;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * The Class TransactionManagerRealService.
 *
 * @param <T> the generic type
 */
@Service
@Scope("prototype")
@Conditional({TransactionManagerRealCondition.class})
public class TransactionManagerRealService<T> extends BaseService implements TransactionManagerService<T> {
   
   /** The Constant EXIT_CODE_OK. */
   private static final String EXIT_CODE_OK = "OK";
   
   /** The logger. */
   protected final Logger logger = LoggerFactory.getLogger(this.getClass());
   
   /** The bean factory. */
   @Autowired
   private BeanFactory beanFactory;
   
   /** The get transaction info rest connector. */
   private GetTransactionInfoRestConnector<T> getTransactionInfoRestConnector;
   
   /** The get transaction info rest request transformer. */
   @Autowired
   private GetTransactionInfoRestRequestTransformer getTransactionInfoRestRequestTransformer;
   
   /** The get transaction info rest response transformer. */
   @Autowired
   private GetTransactionInfoRestResponseTransformer<T> getTransactionInfoRestResponseTransformer;
   
   /** The save transaction info rest connector. */
   @Autowired
   private SaveTransactionInfoRestConnector<T> saveTransactionInfoRestConnector;
   
   /** The save transaction info rest request transformer. */
   @Autowired
   private SaveTransactionInfoRestRequestTransformer<T> saveTransactionInfoRestRequestTransformer;
   
   /** The save transaction info rest response transformer. */
   @Autowired
   private SaveTransactionInfoRestResponseTransformer saveTransactionInfoRestResponseTransformer;
   
   /** The delete transaction info rest connector. */
   @Autowired
   private DeleteTransactionInfoRestConnector deleteTransactionInfoRestConnector;
   
   /** The delete transaction info rest request transformer. */
   @Autowired
   private DeleteTransactionInfoRestRequestTransformer deleteTransactionInfoRestRequestTransformer;
   
   /** The delete transaction info rest response transformer. */
   @Autowired
   private DeleteTransactionInfoRestResponseTransformer deleteTransactionInfoRestResponseTransformer;
   
   /** The save dispo info rest connector. */
   @Autowired(
      required = false
   )
   private SaveDispoInfoRestConnector saveDispoInfoRestConnector;
   
   /** The save dispo info rest request transformer. */
   @Autowired(
      required = false
   )
   private SaveDispoInfoRestRequestTransformer saveDispoInfoRestRequestTransformer;
   
   /** The save dispo info rest response transformer. */
   @Autowired(
      required = false
   )
   private SaveDispoInfoRestResponseTransformer saveDispoInfoRestResponseTransformer;
   
   /** The put dispo info enabled. */
   @Value("${transaction-manager.putDispoInfoEnabled:false}")
   private boolean putDispoInfoEnabled;

   /**
    * Post construct.
    */
   @PostConstruct
   public void postConstruct() {
      this.getTransactionInfoRestConnector = (GetTransactionInfoRestConnector)this.beanFactory.getBean(GetTransactionInfoRestConnector.class);
   }

   /**
    * Save transaction info.
    *
    * @param idOp the id op
    * @param transactionInfo the transaction info
    * @param dispoValues the dispo values
    * @return the string
    */
   private String saveTransactionInfo(String idOp, T transactionInfo, Map<String, String> dispoValues) {
      this.logger.debug("saving transactionInfo with idOp: {}", idOp);
      TransactionInfoWithIdOp transactionInfoWithIdOp = new TransactionInfoWithIdOp(idOp, transactionInfo, dispoValues);

      try {
         idOp = (String)this.saveTransactionInfoRestConnector.call(transactionInfoWithIdOp, this.saveTransactionInfoRestRequestTransformer, this.saveTransactionInfoRestResponseTransformer, new Object[0]);
         this.logger.debug("transactionInfo saved: {}", idOp);
         if (this.putDispoInfoEnabled) {
            this.putDispoInfo(idOp, dispoValues);
         }

         return idOp;
      } catch (Exception var6) {
         this.logger.error("error saving transactionInfo: ", var6);
         throw var6;
      }
   }

   /**
    * Put dispo info.
    *
    * @param idOp the id op
    * @param dispoValues the dispo values
    */
   private void putDispoInfo(String idOp, Map<String, String> dispoValues) {
      SaveDispoInfoRequest saveDispoInfoRequest = new SaveDispoInfoRequest();
      saveDispoInfoRequest.setIdOp(idOp);
      saveDispoInfoRequest.setDispoValues(dispoValues);

      try {
         SaveDispoInfoResponse saveDispoInfoResponse = (SaveDispoInfoResponse)this.saveDispoInfoRestConnector.call(saveDispoInfoRequest, this.saveDispoInfoRestRequestTransformer, this.saveDispoInfoRestResponseTransformer, new Object[0]);
         if (saveDispoInfoResponse == null || !"OK".equals(saveDispoInfoResponse.getExitCode())) {
            if (saveDispoInfoResponse != null) {
               this.logger.error("saveDispoInfoResponse is KO: ", saveDispoInfoResponse.getMessage());
            } else {
               this.logger.error("saveDispoInfoResponse is null");
            }

            throw new RuntimeException("saveDispoInfoResponse KO");
         }
      } catch (Exception var5) {
         this.logger.error("error saving dispoInfo");
         throw var5;
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
         return this.saveTransactionInfo(idOp, transactionInfo, dispoValues);
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
         return this.saveTransactionInfo((String)null, transactionInfo, dispoValues);
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
            T transactionInfo = this.getTransactionInfoRestConnector.call(idOp, this.getTransactionInfoRestRequestTransformer, this.getTransactionInfoRestResponseTransformer, new Object[0]);
            this.logger.debug("retrieved transactionInfo: {}", transactionInfo);
            return transactionInfo;
         } catch (Exception var3) {
            this.logger.error("error retrieving transactionInfo: ", var3);
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
            this.deleteTransactionInfoRestConnector.call(idOp, this.deleteTransactionInfoRestRequestTransformer, this.deleteTransactionInfoRestResponseTransformer, new Object[0]);
            this.logger.debug("transactionInfo deleted");
            return true;
         } else {
            throw new IllegalArgumentException("idOp is empty");
         }
      } catch (Exception var3) {
         this.logger.error("error deleting transactionInfo: ", var3);
         return false;
      }
   }

   /**
    * Sets the clazz.
    *
    * @param type the new clazz
    */
   public void setClazz(Type type) {
      this.getTransactionInfoRestConnector.setClazz(type);
   }
}

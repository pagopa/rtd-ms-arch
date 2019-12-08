/*
 * 
 */
package eu.sia.meda.transaction.service;

import eu.sia.meda.service.BaseService;
import eu.sia.meda.transaction.resource.TransactionInfoResource;
import eu.sia.meda.util.ReflectionUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * The Class BaseTransactionManagerService.
 *
 * @param <T> the generic type
 */
public abstract class BaseTransactionManagerService<T> extends BaseService {
   
   /** The bean factory. */
   @Autowired
   private BeanFactory beanFactory;
   
   /** The transaction manager service. */
   private TransactionManagerService<T> transactionManagerService;

   /**
    * Post construct.
    */
   @PostConstruct
   public void postConstruct() {
      this.transactionManagerService = (TransactionManagerService)this.beanFactory.getBean(TransactionManagerService.class);
      ParameterizedType type = ParameterizedTypeImpl.make(TransactionInfoResource.class, new Type[]{(Type)ReflectionUtils.getGenericTypeClass(this.getClass(), 0)}, (Type)null);
      this.transactionManagerService.setClazz(type);
   }

   /**
    * Put transaction info.
    *
    * @param transactionInfo the transaction info
    * @param dispoValues the dispo values
    * @return the string
    */
   public String putTransactionInfo(T transactionInfo, Map<String, String> dispoValues) {
      return this.transactionManagerService.putTransactionInfo(transactionInfo, dispoValues);
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
      return this.transactionManagerService.putTransactionInfo(idOp, transactionInfo, dispoValues);
   }

   /**
    * Gets the transaction info.
    *
    * @param idOp the id op
    * @return the transaction info
    */
   public T getTransactionInfo(String idOp) {
      return this.transactionManagerService.getTransactionInfo(idOp);
   }

   /**
    * Delete transaction info.
    *
    * @param idOp the id op
    * @return true, if successful
    */
   public boolean deleteTransactionInfo(String idOp) {
      return this.transactionManagerService.deleteTransactionInfo(idOp);
   }
}

/*
 * 
 */
package eu.sia.meda.connector.jdbc.response;

import eu.sia.meda.layers.connector.response.BaseConnectorResponse;
import java.util.List;
import java.util.Map;

/**
 * The Class JDBCResponse.
 *
 * @param <T> the generic type
 */
public class JDBCResponse<T> extends BaseConnectorResponse<List<T>> {
   
   /** The result. */
   private List<T> result;
   
   /** The result set extr. */
   private Object resultSetExtr;
   
   /** The stored procedure result. */
   private Map<String, Object> storedProcedureResult;

   /**
    * Gets the result.
    *
    * @return the result
    */
   public List<T> getResult() {
      return this.result;
   }

   /**
    * Sets the result.
    *
    * @param result the new result
    */
   public void setResult(List<T> result) {
      this.result = result;
   }

   /**
    * Gets the result set extr.
    *
    * @return the result set extr
    */
   public Object getResultSetExtr() {
      return this.resultSetExtr;
   }

   /**
    * Sets the result set extr.
    *
    * @param resultSetExtr the new result set extr
    */
   public void setResultSetExtr(Object resultSetExtr) {
      this.resultSetExtr = resultSetExtr;
   }

   /**
    * Gets the stored procedure result.
    *
    * @return the stored procedure result
    */
   public Map<String, Object> getStoredProcedureResult() {
      return this.storedProcedureResult;
   }

   /**
    * Sets the stored procedure result.
    *
    * @param storedProcedureResult the stored procedure result
    */
   public void setStoredProcedureResult(Map<String, Object> storedProcedureResult) {
      this.storedProcedureResult = storedProcedureResult;
   }
}

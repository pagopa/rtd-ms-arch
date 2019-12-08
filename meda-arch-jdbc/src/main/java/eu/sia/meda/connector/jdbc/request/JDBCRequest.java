/*
 * 
 */
package eu.sia.meda.connector.jdbc.request;

import eu.sia.meda.connector.jdbc.JDBCQueryType;
import eu.sia.meda.layers.connector.request.BaseConnectorRequest;
import java.util.List;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;

/**
 * The Class JDBCRequest.
 *
 * @param <INPUT> the generic type
 */
public class JDBCRequest<INPUT> extends BaseConnectorRequest {
   
   /** The query. */
   private String query;
   
   /** The row mapper. */
   private RowMapper rowMapper;
   
   /** The result set extractor. */
   private ResultSetExtractor resultSetExtractor;
   
   /** The params. */
   private Object[] params;
   
   /** The type. */
   private JDBCQueryType type;
   
   /** The stored procedure parameters. */
   private List<SqlParameter> storedProcedureParameters;
   
   /** The callable statement creator. */
   private CallableStatementCreator callableStatementCreator;

   /**
    * Instantiates a new JDBC request.
    */
   public JDBCRequest() {
   }

   /**
    * Instantiates a new JDBC request.
    *
    * @param query the query
    * @param rowMapper the row mapper
    * @param params the params
    */
   public JDBCRequest(String query, RowMapper rowMapper, Object[] params) {
      this.query = query;
      this.rowMapper = rowMapper;
      this.params = params;
   }

   /**
    * Instantiates a new JDBC request.
    *
    * @param query the query
    * @param resultSetExtractor the result set extractor
    * @param params the params
    */
   public JDBCRequest(String query, ResultSetExtractor resultSetExtractor, Object[] params) {
      this.query = query;
      this.resultSetExtractor = resultSetExtractor;
      this.params = params;
   }

   /**
    * Gets the query.
    *
    * @return the query
    */
   public String getQuery() {
      return this.query;
   }

   /**
    * Sets the query.
    *
    * @param query the new query
    */
   public void setQuery(String query) {
      this.query = query;
   }

   /**
    * Gets the row mapper.
    *
    * @return the row mapper
    */
   public RowMapper getRowMapper() {
      return this.rowMapper;
   }

   /**
    * Sets the row mapper.
    *
    * @param rowMapper the new row mapper
    */
   public void setRowMapper(RowMapper rowMapper) {
      this.rowMapper = rowMapper;
   }

   /**
    * Gets the params.
    *
    * @return the params
    */
   public Object[] getParams() {
      return this.params;
   }

   /**
    * Sets the params.
    *
    * @param params the new params
    */
   public void setParams(Object[] params) {
      this.params = params;
   }

   /**
    * Gets the type.
    *
    * @return the type
    */
   public JDBCQueryType getType() {
      return this.type;
   }

   /**
    * Sets the type.
    *
    * @param type the new type
    */
   public void setType(JDBCQueryType type) {
      this.type = type;
   }

   /**
    * Gets the result set extractor.
    *
    * @return the result set extractor
    */
   public ResultSetExtractor getResultSetExtractor() {
      return this.resultSetExtractor;
   }

   /**
    * Sets the result set extractor.
    *
    * @param resultSetExtractor the new result set extractor
    */
   public void setResultSetExtractor(ResultSetExtractor resultSetExtractor) {
      this.resultSetExtractor = resultSetExtractor;
   }

   /**
    * Gets the stored procedure parameters.
    *
    * @return the stored procedure parameters
    */
   public List<SqlParameter> getStoredProcedureParameters() {
      return this.storedProcedureParameters;
   }

   /**
    * Sets the stored procedure parameters.
    *
    * @param storedProcedureParameters the new stored procedure parameters
    */
   public void setStoredProcedureParameters(List<SqlParameter> storedProcedureParameters) {
      this.storedProcedureParameters = storedProcedureParameters;
   }

   /**
    * Gets the callable statement creator.
    *
    * @return the callable statement creator
    */
   public CallableStatementCreator getCallableStatementCreator() {
      return this.callableStatementCreator;
   }

   /**
    * Sets the callable statement creator.
    *
    * @param callableStatementCreator the new callable statement creator
    */
   public void setCallableStatementCreator(CallableStatementCreator callableStatementCreator) {
      this.callableStatementCreator = callableStatementCreator;
   }
}

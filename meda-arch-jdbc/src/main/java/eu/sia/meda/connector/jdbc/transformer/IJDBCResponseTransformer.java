/*
 * 
 */
package eu.sia.meda.connector.jdbc.transformer;

import eu.sia.meda.connector.jdbc.response.JDBCResponse;

/**
 * The Interface IJDBCResponseTransformer.
 *
 * @param <RESOURCE> the generic type
 * @param <OUTPUT> the generic type
 */
public interface IJDBCResponseTransformer<RESOURCE, OUTPUT> {
   
   /**
    * Transform.
    *
    * @param jdbcResponse the jdbc response
    * @return the output
    */
   OUTPUT transform(JDBCResponse<RESOURCE> jdbcResponse);
}

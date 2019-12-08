/*
 * 
 */
package eu.sia.meda.connector.jdbc.transformer;

import eu.sia.meda.connector.jdbc.request.JDBCRequest;

/**
 * The Interface IJDBCRequestTransformer.
 *
 * @param <INPUT> the generic type
 * @param <DTO> the generic type
 */
public interface IJDBCRequestTransformer<INPUT, DTO> {
   
   /**
    * Transform.
    *
    * @param om the om
    * @param args the args
    * @return the JDBC request
    */
   JDBCRequest<DTO> transform(INPUT om, Object... args);
}

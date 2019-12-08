/*
 * 
 */
package eu.sia.meda.connector.jdbc;

/**
 * The Enum JDBCQueryType.
 */
public enum JDBCQueryType {
   
   /** The find. */
   FIND("FIND"),
   
   /** The update. */
   UPDATE("UPDATE"),
   
   /** The execute. */
   EXECUTE("EXECUTE");

   /** The type. */
   private final String type;

   /**
    * Instantiates a new JDBC query type.
    *
    * @param type the type
    */
   private JDBCQueryType(String type) {
      this.type = type;
   }

   /**
    * To string.
    *
    * @return the string
    */
   public String toString() {
      return this.type;
   }
}

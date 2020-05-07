package eu.sia.meda.layers.connector;

/**
 * The Interface AuditableConnectorConfiguration.
 */
public interface AuditableConnectorConfiguration extends TraceableConnectorConfiguration {
   
   /** The audit enabled key. */
   String AUDIT_ENABLED_KEY = "auditEnabled";
   
   /** The cod service key. */
   String COD_SERVICE_KEY = "codService";

   /**
    * Checks if is audit enabled.
    *
    * @return the boolean
    */
   Boolean isAuditEnabled();

   /**
    * Sets the audit enabled.
    *
    * @param auditEnabled the new audit enabled
    */
   void setAuditEnabled(Boolean auditEnabled);

   /**
    * Gets the cod service.
    *
    * @return the cod service
    */
   String getCodService();

   /**
    * Sets the cod service.
    *
    * @param codService the new cod service
    */
   void setCodService(String codService);
}

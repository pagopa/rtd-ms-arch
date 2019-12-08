package eu.sia.meda.tracing.config;

import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.AuditableConnectorConfiguration;
import eu.sia.meda.layers.connector.TraceableConnectorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class TracingConfiguration.
 */
@Configuration
@PropertySource(
   value = {"classpath:tracing.properties"},
   name = "localTracingConfig"
)
public class TracingConfiguration {
   
   /** The Constant TRACING_PROPERTY_PREFIX. */
   public static final String TRACING_PROPERTY_PREFIX = "tracing";
   
   /** The Constant TRACING_ENABLED_SUBPROP. */
   public static final String TRACING_ENABLED_SUBPROP = "enabled";
   
   /** The Constant TRACING_MOCKED_SUBPROP. */
   public static final String TRACING_MOCKED_SUBPROP = "mocked";
   
   /** The Constant TRACING_AUDIT_MOCKED_SUBPROP. */
   public static final String TRACING_AUDIT_MOCKED_SUBPROP = "auditMocked";
   
   /** The Constant TRACING_ENABLED_PROPERTY. */
   public static final String TRACING_ENABLED_PROPERTY = "tracing.enabled";
   
   /** The properties manager. */
   private final PropertiesManager propertiesManager;

   /**
    * Instantiates a new tracing configuration.
    *
    * @param propertiesManager the properties manager
    */
   @Autowired
   public TracingConfiguration(PropertiesManager propertiesManager) {
      this.propertiesManager = propertiesManager;
   }

   /**
    * Checks if is tracing enabled for.
    *
    * @param connectorConfiguration the connector configuration
    * @return true, if is tracing enabled for
    */
   public boolean isTracingEnabledFor(TraceableConnectorConfiguration connectorConfiguration) {
      Boolean tracingEnabled = this.propertiesManager.get(TRACING_ENABLED_PROPERTY, Boolean.class);
      boolean traceableConnector = !Boolean.FALSE.equals(connectorConfiguration.isEnabledTracing());
      boolean mockedConnector = connectorConfiguration.isMocked() != null && connectorConfiguration.isMocked();
      return Boolean.TRUE.equals(tracingEnabled) && traceableConnector && !mockedConnector;
   }

   /**
    * Checks if is audit enabled for.
    *
    * @param connectorConfiguration the connector configuration
    * @return true, if is audit enabled for
    */
   public boolean isAuditEnabledFor(AuditableConnectorConfiguration connectorConfiguration) {
      Boolean tracingEnabled = this.propertiesManager.get(TRACING_ENABLED_PROPERTY, Boolean.class);
      boolean auditableConnector = Boolean.TRUE.equals(connectorConfiguration.isAuditEnabled()) && connectorConfiguration.getCodService() != null;
      boolean mockedConnector = connectorConfiguration.isMocked() != null && connectorConfiguration.isMocked();
      return Boolean.TRUE.equals(tracingEnabled) && auditableConnector && !mockedConnector;
   }
}

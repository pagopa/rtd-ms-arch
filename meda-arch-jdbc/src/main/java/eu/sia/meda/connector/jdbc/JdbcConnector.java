/*
 * 
 */
package eu.sia.meda.connector.jdbc;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.jdbc.configuration.ArchJDBCConfigurationService;
import eu.sia.meda.connector.jdbc.request.JDBCRequest;
import eu.sia.meda.connector.jdbc.response.JDBCResponse;
import eu.sia.meda.connector.jdbc.transformer.IJDBCRequestTransformer;
import eu.sia.meda.connector.jdbc.transformer.IJDBCResponseTransformer;
import eu.sia.meda.core.properties.PropertiesManager;
import eu.sia.meda.layers.connector.BaseConnector;
import eu.sia.meda.tracing.TracingManager;
import eu.sia.meda.tracing.TracingMessageBuilder;
import eu.sia.meda.tracing.config.TracingConfiguration;
import eu.sia.meda.util.MockUtils;
import eu.sia.meda.util.StringUtils;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.Assert;

/**
 * The Class JdbcConnector.
 *
 * @param <INPUT> the generic type
 * @param <OUTPUT> the generic type
 * @param <DTO> the generic type
 * @param <RESOURCE> the generic type
 */
public abstract class JdbcConnector<INPUT, OUTPUT, DTO, RESOURCE> extends BaseConnector<JDBCRequest<DTO>, JDBCResponse<RESOURCE>> {
   
   /** The Constant sqlPlaceholder. */
   private static final Pattern sqlPlaceholder = Pattern.compile("\\?");
   
   /** The jdbc template. */
   private JdbcTemplate jdbcTemplate;
   
   /** The jdbc configuration. */
   private ArchJDBCConfigurationService.JDBCConfiguration jdbcConfiguration;
   
   /** The should trace. */
   private boolean shouldTrace;
   
   /** The configuration. */
   @Autowired
   private ArchJDBCConfigurationService configuration;
   
   /** The tracing manager. */
   @Autowired(
      required = false
   )
   private TracingManager tracingManager;
   
   /** The tracing configuration. */
   @Autowired
   private TracingConfiguration tracingConfiguration;
   
   /** The properties manager. */
   @Autowired
   private PropertiesManager propertiesManager;

   /**
    * Inits the.
    */
   @PostConstruct
   private void init() {
      this.jdbcConfiguration = this.configuration.retrieveJDBCConfiguration(this.getClass().getSimpleName());
      if (this.jdbcConfiguration == null) {
         throw new ExceptionInInitializerError();
      } else {
         this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for connector: {}"), this.getClass().getSimpleName());
         if (this.jdbcConfiguration.isMocked()) {
            this.logger.warn(LoggerUtils.formatArchRow("No JDBC configuration found. H2 in-memory DataBase pulled up"));
            String name = this.getClass().getSimpleName();
            EmbeddedDatabaseBuilder edb = (new EmbeddedDatabaseBuilder()).setName(name).setType(EmbeddedDatabaseType.H2).setScriptEncoding("UTF-8");

            try {
               List<String> mockLocations = MockUtils.getMockResourcePath(this.jdbcConfiguration.getPath(), this.jdbcConfiguration.getFiles(), "sql");
               Iterator var4 = mockLocations.iterator();

               while(var4.hasNext()) {
                  String location = (String)var4.next();
                  edb.addScript(location);
               }
            } catch (IOException var6) {
               this.logger.error(LoggerUtils.formatArchRow("Error retrieving mock init files: {}"), var6.getMessage());
            }

            DataSource dataSource = edb.continueOnError(true).ignoreFailedDrops(true).build();
            this.jdbcTemplate = new JdbcTemplate(dataSource);
         } else {
            HikariDataSource ds = new HikariDataSource();
            ds.setMaximumPoolSize(this.jdbcConfiguration.getConnectionPoolSize());
            ds.setConnectionTimeout((long)this.jdbcConfiguration.getTimeout());
            ds.setDriverClassName(this.jdbcConfiguration.getDriverClassName());
            ds.setJdbcUrl(this.jdbcConfiguration.getUri());
            ds.setUsername(this.jdbcConfiguration.getUsername());
            ds.setPassword(this.jdbcConfiguration.getPassword());
            this.jdbcTemplate = new JdbcTemplate(ds);
         }

         this.shouldTrace = this.tracingConfiguration.isTracingEnabledFor(this.jdbcConfiguration);
      }
   }

   /**
    * Do execute.
    *
    * @param request the request
    * @return the JDBC response
    */
   protected JDBCResponse doExecute(JDBCRequest<DTO> request) {
      JDBCResponse<RESOURCE> response = new JDBCResponse();
      Integer resultSize = null;
      JDBCQueryType type = request.getType();
      String query = request.getQuery();
      Object[] params = request.getParams();
      RowMapper rowMapper = request.getRowMapper();
      ResultSetExtractor resultSetExtractor = request.getResultSetExtractor();
      boolean isRowMapper = false;
      if (request.getType().equals(JDBCQueryType.FIND)) {
         if (request.getRowMapper() != null) {
            isRowMapper = true;
         } else if (request.getResultSetExtractor() == null) {
            throw new IllegalStateException("Either RowMapper or ResultSetExtractor must be set");
         }
      }

      switch(type) {
      case FIND:
         try {
            if (isRowMapper) {
               List<RESOURCE> resultList = this.jdbcTemplate.query(query, rowMapper, params);
               resultSize = resultList.size();
               response.setResult(resultList);
            } else {
               response.setResultSetExtr(this.jdbcTemplate.query(query, params, resultSetExtractor));
            }
            break;
         } catch (Throwable var13) {
            this.trace(type, query, params, var13);
            throw var13;
         }
      case UPDATE:
         try {
            resultSize = this.jdbcTemplate.update(query, params);
         } catch (Throwable var12) {
            this.trace(type, query, params, var12);
            throw var12;
         }

         if (resultSize > 0) {
            response.setResult(new ArrayList());
         } else {
            this.logger.error(LoggerUtils.formatArchRow("Failed Update JDBC Query."));
         }
         break;
      case EXECUTE:
         try {
            Assert.notNull(request.getCallableStatementCreator(), "CallableStatementCreator must not be null");
            Assert.notNull(request.getStoredProcedureParameters(), "Callback object must not be null");
            response.setStoredProcedureResult(this.jdbcTemplate.call(request.getCallableStatementCreator(), request.getStoredProcedureParameters()));
            break;
         } catch (Throwable var11) {
            this.trace(type, request.getCallableStatementCreator().toString(), request.getStoredProcedureParameters().toArray(), var11);
            throw var11;
         }
      default:
         this.logger.error(LoggerUtils.formatArchRow("Invalid JDBC query type not defined."));
      }

      this.trace(type, query, params, resultSize);
      return response;
   }

   /**
    * Call.
    *
    * @param input the input
    * @param requestTransformer the request transformer
    * @param responseTransformer the response transformer
    * @param args the args
    * @return the output
    */
   public final OUTPUT call(INPUT input, IJDBCRequestTransformer<INPUT, DTO> requestTransformer, IJDBCResponseTransformer<RESOURCE, OUTPUT> responseTransformer, Object... args) {
      JDBCRequest<DTO> jdbcRequest = requestTransformer.transform(input, args);
      JDBCResponse<RESOURCE> jdbcResponse = (JDBCResponse)this.execute(jdbcRequest);
      return responseTransformer.transform(jdbcResponse);
   }

   /**
    * Trace.
    *
    * @param type the type
    * @param query the query
    * @param params the params
    * @param resultSize the result size
    */
   private void trace(JDBCQueryType type, String query, Object[] params, Integer resultSize) {
      this.trace(type, query, params, (appender) -> {
         appender.newSection("RESPONSE").newEntry("ResultSetSize", resultSize != null ? Integer.toString(resultSize) : "[Unknown]");
      });
   }

   /**
    * Trace.
    *
    * @param type the type
    * @param query the query
    * @param params the params
    * @param t the t
    */
   private void trace(JDBCQueryType type, String query, Object[] params, Throwable t) {
      this.trace(type, query, params, TracingMessageBuilder.appending(t));
   }

   /**
    * Trace.
    *
    * @param type the type
    * @param query the query
    * @param params the params
    * @param appending the appending
    */
   private void trace(JDBCQueryType type, String query, Object[] params, Consumer<TracingMessageBuilder> appending) {
      if (this.shouldTrace) {
         String completeQuery = StringUtils.replaceInOrder(query, sqlPlaceholder, params);
         TracingMessageBuilder tracingMessage = new TracingMessageBuilder("JDBC CONNECTOR");
         tracingMessage.newSection("REQUEST").newEntry("Url", this.jdbcConfiguration.getUri()).newEntry("Type", type).newEntry("Query", completeQuery);
         appending.accept(tracingMessage);
         this.tracingManager.trace(tracingMessage);
      }

   }
}

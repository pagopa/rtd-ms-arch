package eu.sia.meda.layers.connector.http;

import eu.sia.meda.config.http.HttpConnectionPoolConfiguration;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

/**
 * The Class HttpConnectionPool.
 */
public class HttpConnectionPool {
   
   /** The connection manager. */
   private PoolingHttpClientConnectionManager connectionManager;
   
   /** The http client. */
   private CloseableHttpClient httpClient;
   
   /** The connection sweeper. */
   private ScheduledFuture<?> connectionSweeper;
   
   /** The shutdown latch. */
   private CountDownLatch shutdownLatch = new CountDownLatch(1);
   
   /** The logger. */
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   /**
    * Instantiates a new http connection pool.
    *
    * @param configuration the configuration
    * @param credentials the credentials
    * @param connectionPoolSweeperScheduler the connection pool sweeper scheduler
    */
   public HttpConnectionPool(HttpConnectionPoolConfiguration configuration, Credentials credentials, HttpConnectionPoolSweeperScheduler connectionPoolSweeperScheduler) {
      this.logger.info("Starting connection pool for connector '{}'", configuration.getConnectorClassName());
      SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(configuration.getReadTimeout()).setSoKeepAlive(true).build();
      this.connectionManager = new PoolingHttpClientConnectionManager();
      this.connectionManager.setDefaultConnectionConfig(ConnectionConfig.DEFAULT);
      this.connectionManager.setMaxTotal(configuration.getMaxConnections());
      this.connectionManager.setDefaultMaxPerRoute(configuration.getMaxConnections());
      this.connectionManager.setDefaultSocketConfig(socketConfig);
      CookieStore cookieStore = new BasicCookieStore();
      CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
      if (credentials != null) {
         credentialsProvider.setCredentials(AuthScope.ANY, credentials);
      }

      RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec("default").setExpectContinueEnabled(true).setConnectTimeout(configuration.getConnectTimeout()).setConnectionRequestTimeout(configuration.getConnectRequestTimeout()).setSocketTimeout(configuration.getReadTimeout()).build();
      HttpClientBuilder builder = HttpClients.custom();
      builder.setConnectionManager(this.connectionManager).setDefaultCookieStore(cookieStore).setDefaultCredentialsProvider(credentialsProvider).setDefaultRequestConfig(defaultRequestConfig).setConnectionManagerShared(true).addInterceptorFirst(new RemoveSoapHeadersInterceptor());
      this.httpClient = builder.build();
      this.connectionSweeper = connectionPoolSweeperScheduler.addSweeper(() -> {
         this.logger.debug("ConnectionSweeper task for connector '{}' stats: [available: {} leased: {} max: {} pending: {}]", new Object[]{configuration.getConnectorClassName(), this.connectionManager.getTotalStats().getAvailable(), this.connectionManager.getTotalStats().getLeased(), this.connectionManager.getTotalStats().getMax(), this.connectionManager.getTotalStats().getPending()});
         this.connectionManager.closeExpiredConnections();
         this.connectionManager.closeIdleConnections((long)configuration.getIdleTimeout(), TimeUnit.MILLISECONDS);
      }, (long)configuration.getConnectionSweeperInterval());
   }

   /**
    * Gets the http client.
    *
    * @return the http client
    */
   public CloseableHttpClient getHttpClient() {
      return this.httpClient;
   }

   /**
    * Gets the max connections.
    *
    * @return the max connections
    */
   public int getMaxConnections() {
      return this.connectionManager.getMaxTotal();
   }

   /**
    * Shutdown.
    *
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public void shutdown() throws IOException {
      try {
         this.connectionSweeper.cancel(false);
         this.httpClient.close();
      } finally {
         this.connectionManager.close();
      }

   }

   /**
    * Shutdown async.
    *
    * @return the completable future
    */
   public CompletableFuture<Void> shutdownAsync() {
      return CompletableFuture.runAsync(() -> {
         try {
            this.shutdown();
         } catch (IOException var2) {
            throw new CompletionException(var2);
         }
      });
   }
}

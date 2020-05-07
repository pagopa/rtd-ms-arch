package eu.sia.meda.service;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.config.http.HttpConnectionPoolConfiguration;
import eu.sia.meda.core.model.BaseContext;
import eu.sia.meda.layers.connector.http.HttpConnectionPool;
import eu.sia.meda.layers.connector.http.HttpConnectionPoolSweeperScheduler;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.auth.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The Class SessionContextRetriever.
 */
@Service
public class SessionContextRetriever extends BaseService {
   
   /** The Constant SESSION_TOKEN. */
   public static final String SESSION_TOKEN = "X-MEDA-SESSION-TOKEN";
   
   /** The log. */
   private final Logger log = LoggerFactory.getLogger(this.getClass());
   
   /** The rt. */
   private RestTemplate rt;
   
   /** The session context type. */
   private Class<?> sessionContextType;
   
   /** The http connection pool. */
   private HttpConnectionPool httpConnectionPool = null;
   
   /** The http connection pool sweeper scheduler. */
   @Autowired
   private HttpConnectionPoolSweeperScheduler httpConnectionPoolSweeperScheduler;
   
   /** The session context type provider. */
   @Autowired(
      required = false
   )
   private SessionContextTypeProvider sessionContextTypeProvider;
   
   /** The bean factory. */
   @Autowired
   private ListableBeanFactory beanFactory;
   
   /** The rest template builder. */
   @Autowired
   private RestTemplateBuilder restTemplateBuilder;
   
   /** The url. */
   @Value("${session-manager.endpoint:none}")
   private String url;
   
   /** The allow guest session. */
   @Value("${session-manager.allow-guest-session:true}")
   private Boolean allowGuestSession;
   
   /** The allow empty session id. */
   @Value("${session-manager.allow-empty-session-id:false}")
   private Boolean allowEmptySessionId;
   
   /** The mock session manager. */
   @Value("${session-manager.mocked:false}")
   private Boolean mockSessionManager;
   
   /** The read timeout. */
   @Value("${session-manager.connectionPool.readTimeout:10000}")
   private Integer readTimeout;
   
   /** The connect timeout. */
   @Value("${session-manager.connectionPool.connectTimeout:5000}")
   private Integer connectTimeout;
   
   /** The connect request timeout. */
   @Value("${session-manager.connectionPool.connectRequestTimeout:5000}")
   private Integer connectRequestTimeout;
   
   /** The idle timeout. */
   @Value("${session-manager.connectionPool.idleTimeout:60000}")
   private Integer idleTimeout;
   
   /** The connection sweeper interval. */
   @Value("${session-manager.connectionPool.connectionSweeperInterval:5000}")
   private Integer connectionSweeperInterval;
   
   /** The max connections. */
   @Value("${session-manager.connectionPool.maxConnections:100}")
   private Integer maxConnections;
   
   /** The connection pool enabled. */
   @Value("${session-manager.connectionPool.enabled:true}")
   private Boolean connectionPoolEnabled;

   /**
    * Load session context.
    *
    * @param <T> the generic type
    * @param request the request
    * @return the t
    */
   public <T extends BaseContext> T loadSessionContext(HttpServletRequest request) {
      String sessionId = request.getHeader("X-MEDA-SESSION-TOKEN");
      if ((sessionId == null || sessionId.isEmpty()) && request.getCookies() != null) {
         this.log.debug(LoggerUtils.formatArchRow("Trying to read Session Token from Cookie"));
         Cookie[] var3 = request.getCookies();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Cookie cookie = var3[var5];
            if ("cdil0Session".equalsIgnoreCase(cookie.getName())) {
               this.log.debug(LoggerUtils.formatArchRow("Session Token Cookie found"));
               sessionId = cookie.getValue();
               break;
            }
         }
      }

      if (sessionId == null || sessionId.isEmpty()) {
         if (!this.allowEmptySessionId) {
            throw new IllegalArgumentException("Session Token not found in Http Request");
         }

         sessionId = UUID.randomUUID().toString();
      }

      return this.loadSessionContext(sessionId);
   }

   /**
    * Load session context.
    *
    * @param <T> the generic type
    * @param sessionId the session id
    * @return the t
    */
   public <T extends BaseContext> T loadSessionContext(String sessionId) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      this.log.debug(LoggerUtils.formatArchRow("Authentication object {}"), authentication);
      if (this.mockSessionManager) {
         return (T) this.sessionContextTypeProvider.getMockedContext();
      } else {
         if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            this.log.debug(LoggerUtils.formatArchRow("Anonymous Token create EMPTY SESSION [{}]"), this.allowGuestSession);
            if (this.allowGuestSession) {
               if (this.sessionContextTypeProvider != null) {
                  return (T) this.sessionContextTypeProvider.getEmptyContext();
               }

               return (T) BaseContext.EMPTY_SESSION;
            }
         }

         this.log.debug(LoggerUtils.formatArchRow("Authenticated user {} - {}"), authentication, authentication != null ? authentication.getPrincipal() : "null");
         HttpHeaders headers = new HttpHeaders();
         HttpEntity httpEntity = new HttpEntity(headers);
         this.log.debug(LoggerUtils.formatArchRow("Load session at {} / {}"), this.url, sessionId);
         ResponseEntity<T> response = this.rt.exchange(this.url, HttpMethod.GET, httpEntity, this.getContextType(), new Object[]{sessionId});
         this.log.debug(LoggerUtils.formatArchRow("Session Context {} - {}"), sessionId, response);
         return response.getBody();
      }
   }

   /**
    * Gets the context type.
    *
    * @param <T> the generic type
    * @return the context type
    */
   public <T extends BaseContext> Class<T> getContextType() {
      return (Class<T>) this.sessionContextType;
   }

   /**
    * Inits the.
    */
   @PostConstruct
   public void init() {
      for(this.rt = this.restTemplateBuilder.setConnectTimeout(5000).setReadTimeout(10000).build(); this.url.endsWith("/"); this.url = this.url.substring(0, this.url.length() - 1)) {
      }

      this.url = this.url + "/{sessionId}";
      if (this.connectionPoolEnabled) {
         HttpConnectionPoolConfiguration configuration = new HttpConnectionPoolConfiguration();
         configuration.setReadTimeout(this.readTimeout);
         configuration.setConnectTimeout(this.connectTimeout);
         configuration.setConnectRequestTimeout(this.connectRequestTimeout);
         configuration.setIdleTimeout(this.idleTimeout);
         configuration.setConnectionSweeperInterval(this.connectionSweeperInterval);
         configuration.setMaxConnections(this.maxConnections);
         configuration.setConnectorClassName(SessionContextRetriever.class.getName());
         this.httpConnectionPool = new HttpConnectionPool(configuration, (Credentials)null, this.httpConnectionPoolSweeperScheduler);
         this.rt.setRequestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(this.httpConnectionPool.getHttpClient())));
      } else {
         this.rt.setRequestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));
      }

      try {
         this.log.debug(LoggerUtils.formatArchRow("Loading SessionContext type"));
         this.sessionContextType = this.sessionContextTypeProvider.getContextType();
         this.log.debug(LoggerUtils.formatArchRow("Use SessionContext type {}"), this.sessionContextType.getCanonicalName());
      } catch (NullPointerException var2) {
         this.log.warn("Unable to find a proper SessionContextTypeProvider defined in Application Context.");
      }

   }
}

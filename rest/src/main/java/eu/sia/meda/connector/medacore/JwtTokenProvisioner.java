package eu.sia.meda.connector.medacore;

import eu.sia.meda.config.LoggerUtils;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * The Class JwtTokenProvisioner.
 */
@Service
public class JwtTokenProvisioner implements IJwtTokenProvisioner {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerUtils.getLogger(JwtTokenProvisioner.class);
   
   /** The jwt token. */
   private volatile String jwtToken;
   
   /** The queued. */
   private volatile boolean queued = false;
   
   /** The attempt since last regen. */
   private AtomicInteger attemptSinceLastRegen = new AtomicInteger(0);
   
   /** The last blocking invocation. */
   private DateTime lastBlockingInvocation;
   
   /** The token expire time. */
   private DateTime tokenExpireTime;

   /**
    * Manipulate token.
    *
    * @param configuration the configuration
    * @return the string
    */
   public String manipulateToken(ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration configuration) {
      logger.debug(LoggerUtils.formatArchRow("Entered manipulateToken(...) with jwtToken={} and queued={}"), this.jwtToken, this.queued);
      String internal = this.jwtToken;
      if (internal == null) {
         logger.debug(LoggerUtils.formatArchRow("manipulateToken(...) internal is null"));
         this.queued = true;
         synchronized(this) {
            if (this.queued) {
               logger.debug(LoggerUtils.formatArchRow("manipulateToken(...) queued is true"));
               internal = null;
               this.jwtToken = null;
               if (internal == null) {
                  if ((this.lastBlockingInvocation == null || Minutes.minutesBetween(this.lastBlockingInvocation, DateTime.now()).isGreaterThan(Minutes.minutes(2))) && this.attemptSinceLastRegen.get() < 30) {
                     this.doRenewal(configuration);
                     internal = this.jwtToken;
                     if (internal != null) {
                        this.queued = false;
                     }
                  }
               } else {
                  this.queued = false;
               }
            }
         }
      } else {
         logger.debug(LoggerUtils.formatArchRow("manipulateToken(...) internal={}"), internal);
         if (this.tokenExpireTime.isBefore(DateTime.now())) {
            this.doRenewal(configuration);
            internal = this.jwtToken;
         }
      }

      logger.debug(LoggerUtils.formatArchRow("Returning manipulateToken(...) with internal={}"), internal);
      return internal;
   }

   /**
    * Do renewal.
    *
    * @param configuration the configuration
    */
   public void doRenewal(ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration configuration) {
      logger.debug(LoggerUtils.formatArchRow("Entering doRenewal(...)"));

      try {
         JwtTokenDTO token = this.generateToken(configuration);
         logger.debug(LoggerUtils.formatArchRow("JwtTokenDTO={}"), token.toString());
         this.jwtToken = token.access_token;
         this.lastBlockingInvocation = DateTime.now();
         this.tokenExpireTime = this.lastBlockingInvocation.plusSeconds(token.expires_in);
         this.attemptSinceLastRegen.set(0);
      } catch (Exception var3) {
         logger.debug(LoggerUtils.formatArchRow("Exception in doRenewal(...), {}"), var3.getMessage());
         this.attemptSinceLastRegen.incrementAndGet();
         this.lastBlockingInvocation = DateTime.now();
      }

   }

   /**
    * Generate token.
    *
    * @param configuration the configuration
    * @return the jwt token DTO
    */
   private JwtTokenDTO generateToken(ArchMedaCoreConnectorConfigurationService.MedaCoreConfiguration configuration) {
      String auth = configuration.getClient() + ":" + configuration.getClientSecret();
      logger.debug(LoggerUtils.formatArchRow("Generating token using credentials: {}"), auth);
      byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      headers.set("Authorization", "Basic " + new String(encodedAuth));
      MultiValueMap<String, String> data = new LinkedMultiValueMap();
      data.add("grant_type", "client_credentials");
      if (configuration.getScope() != null) {
         data.add("scope", configuration.getScope());
      }

      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(data, headers);
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<JwtTokenDTO> response = restTemplate.exchange(configuration.getOauthUrl() + "/v2/token", HttpMethod.POST, httpEntity, JwtTokenDTO.class, new Object[0]);
      logger.debug(LoggerUtils.formatArchRow("Generate token response: code={}, body={}"), response.getStatusCode(), response.toString());
      return (JwtTokenDTO)response.getBody();
   }
}

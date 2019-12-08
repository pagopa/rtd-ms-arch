package eu.sia.meda.switcher;

import eu.sia.meda.config.CopySwitcherDefaultSemaphoreConfig;
import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.switcher.dto.GetSemaphoreResponse;
import eu.sia.meda.switcher.dto.SemaphoreDto;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * The Class CopySwitcherSemaphoreRegistry.
 */
@Service
public class CopySwitcherSemaphoreRegistry implements DisposableBean {
   
   /** The logger. */
   private final Logger logger = LoggerFactory.getLogger(CopySwitcherSemaphoreRegistry.class);
   
   /** The rest base url. */
   private String restBaseUrl;
   
   /** The rt. */
   private RestTemplate rt;
   
   /** The scheduled executor service. */
   private ScheduledExecutorService scheduledExecutorService;
   
   /** The actual semaphore map. */
   private Map<String, CopySwitcherSemaphore> actualSemaphoreMap = new ConcurrentHashMap<>();

   /**
    * Instantiates a new copy switcher semaphore registry.
    *
    * @param config the config
    * @param restTemplateBuilder the rest template builder
    */
   @Autowired
   public CopySwitcherSemaphoreRegistry(CopySwitcherDefaultSemaphoreConfig config, RestTemplateBuilder restTemplateBuilder) {
      try {
         List<String> initSemaphoreList = config.getSemaphores();
         String configBaseUrl = config.getConfigBaseUrl();
         Map<String, Long> validities = config.getValidities();
         Long refreshDelay = config.getRefreshDelaySeconds();
         if (config.getSemaphores().isEmpty()) {
            this.logger.info(LoggerUtils.formatArchRow("No semaphore in semaphore list for copy-switcher: going always on CORE"));
            return;
         }

         if (configBaseUrl == null) {
            throw new IllegalStateException("ConfigBaseUrl (where to retrieve the semaphores data) is null");
         }

         String baseUrl = configBaseUrl;
         if (!configBaseUrl.endsWith("/")) {
            baseUrl = configBaseUrl + "/";
         }

         this.restBaseUrl = baseUrl;
         this.rt = restTemplateBuilder.build();
         this.getSemaphoresStatus(initSemaphoreList, (semaphore) -> {
            Long validity = (Long)validities.get(semaphore.getName());
            semaphore.setMillisOfValidity(validity != null ? validity : 60000L);
         });
         if (!this.actualSemaphoreMap.isEmpty() && refreshDelay != null) {
            this.logger.debug(LoggerUtils.formatArchRow("Preparing scheduled refresh task to run each {} seconds"), refreshDelay);
            this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
            this.scheduledExecutorService.scheduleWithFixedDelay(this::scheduledRefresh, refreshDelay, refreshDelay, TimeUnit.SECONDS);
         }
      } catch (Throwable var8) {
         this.logger.warn(LoggerUtils.formatArchRow("Exception in CopySwitcherSemaphoreRegistry initialization."), var8);
      }

   }

   /**
    * Open switch.
    *
    * @param semaphoreName the semaphore name
    * @param acceptableThreshold the acceptable threshold
    * @return true, if successful
    */
   public boolean openSwitch(String semaphoreName, long acceptableThreshold) {
      this.logger.debug(LoggerUtils.formatArchRow("evaluating semaphore [{}]"), semaphoreName);

      try {
         CopySwitcherSemaphore actualSemaphore = (CopySwitcherSemaphore)this.actualSemaphoreMap.get(semaphoreName);
         if (actualSemaphore == null) {
            this.logger.warn(LoggerUtils.formatArchRow("Semaphore [{}] NOT MAPPED - circuit open"), semaphoreName);
            return true;
         } else if (!actualSemaphore.isEnabled()) {
            this.logger.warn(LoggerUtils.formatArchRow("Semaphore [{}] NOT ENABLED - circuit open"), semaphoreName);
            return true;
         } else if (this.isExpired(actualSemaphore)) {
            this.logger.warn(LoggerUtils.formatArchRow("Semaphore [{}] EXPIRED - circuit open"), semaphoreName);
            return true;
         } else {
            this.logger.debug("semaphore [{}] actualLatency [{}] threshold [{}]", new Object[]{semaphoreName, actualSemaphore.getLatency(), acceptableThreshold});
            if (actualSemaphore.getLatency() > acceptableThreshold) {
               this.logger.warn(LoggerUtils.formatArchRow("Semaphore [{}] has not acceptable latency"), semaphoreName);
               return true;
            } else {
               return false;
            }
         }
      } catch (Throwable var5) {
         this.logger.warn(LoggerUtils.formatArchRow("{} Exception in switcher evaluation return TRUE "), var5.getMessage());
         return true;
      }
   }

   /**
    * Checks if is expired.
    *
    * @param actualSemaphore the actual semaphore
    * @return true, if is expired
    */
   private boolean isExpired(CopySwitcherSemaphore actualSemaphore) {
      return actualSemaphore.getLastUpdateTimestamp() + actualSemaphore.getMillisOfValidity() < getCurrentTimeMillis();
   }

   /**
    * Gets the current time millis.
    *
    * @return the current time millis
    */
   private static long getCurrentTimeMillis() {
      return Instant.now().toEpochMilli();
   }

   /**
    * Perform switch.
    *
    * @param <F> the generic type
    * @param copyCall the copy call
    * @param coreCall the core call
    * @param semaphoreName the semaphore name
    * @param acceptableThreshold the acceptable threshold
    * @return the f
    */
   public <F> F performSwitch(Supplier<F> copyCall, Supplier<F> coreCall, String semaphoreName, long acceptableThreshold) {
      if (this.openSwitch(semaphoreName, acceptableThreshold)) {
         this.logger.info(LoggerUtils.formatArchRow("Semaphore [{}] - Going on CORE"), semaphoreName);
         return coreCall.get();
      } else {
         this.logger.info(LoggerUtils.formatArchRow("Semaphore [{}] - Going on COPY"), semaphoreName);
         return copyCall.get();
      }
   }

   /**
    * Update.
    *
    * @param message the message
    * @param lastUpdateTimestamp the last update timestamp
    */
   void update(CopySwitcherMessage message, Long lastUpdateTimestamp) {
      if (message == null) {
         this.logger.warn(LoggerUtils.formatArchRow("Received a null event message"));
      } else {
         if (this.actualSemaphoreMap.containsKey(message.getName())) {
            CopySwitcherSemaphore semaphore = new CopySwitcherSemaphore();
            semaphore.setName(message.getName());
            semaphore.setLatency(message.getLatency());
            semaphore.setEnabled(message.isEnabled());
            semaphore.setLastUpdateTimestamp(lastUpdateTimestamp);
            this.logger.debug(LoggerUtils.formatArchRow("Copy Switcher updated new value: {} "), semaphore);
            this.actualSemaphoreMap.put(semaphore.getName(), semaphore);
         } else {
            this.logger.warn(LoggerUtils.formatArchRow("Received an event for a non-mapped semaphore: {}"), message);
         }

      }
   }

   /**
    * Gets the semaphores status.
    *
    * @param semaphoreList the semaphore list
    * @return the semaphores status
    */
   private void getSemaphoresStatus(List<String> semaphoreList) {
      this.getSemaphoresStatus(semaphoreList, (Consumer)null);
   }

   /**
    * Gets the semaphores status.
    *
    * @param semaphoreList the semaphore list
    * @param manipulation the manipulation
    * @return the semaphores status
    */
   private void getSemaphoresStatus(List<String> semaphoreList, Consumer<CopySwitcherSemaphore> manipulation) {
      HttpHeaders headers = new HttpHeaders();
      HttpEntity httpEntity = new HttpEntity(headers);
      Iterator var5 = semaphoreList.iterator();

      while(var5.hasNext()) {
         String semaphoreName = (String)var5.next();

         try {
            String url = this.restBaseUrl + semaphoreName;
            ResponseEntity<GetSemaphoreResponse> responseEntity = this.rt.exchange(url, HttpMethod.GET, httpEntity, GetSemaphoreResponse.class, new Object[0]);
            this.logger.debug(LoggerUtils.formatArchRow("Copy Switcher - response for {} semaphore: {}"), semaphoreName, responseEntity);
            GetSemaphoreResponse response = (GetSemaphoreResponse)responseEntity.getBody();
            if (response != null && response.getSemaphore() != null) {
               SemaphoreDto semaphoreDto = response.getSemaphore();
               CopySwitcherSemaphore semaphore = new CopySwitcherSemaphore();
               semaphore.setLatency(semaphoreDto.getLatency());
               semaphore.setEnabled(semaphoreDto.isEnabled());
               semaphore.setName(semaphoreDto.getName());
               semaphore.setLastUpdateTimestamp(getCurrentTimeMillis());
               if (!semaphoreName.equals(semaphoreDto.getName())) {
                  this.logger.warn(LoggerUtils.formatArchRow("Semaphore name in config and in variablePath of rest service ({}) is DIFFERENT from name in response body ({})"), semaphoreName, semaphoreDto.getName());
                  semaphore.setName(semaphoreName);
               }

               if (manipulation != null) {
                  manipulation.accept(semaphore);
               }

               this.actualSemaphoreMap.put(semaphoreName, semaphore);
               this.logger.debug(LoggerUtils.formatArchRow("Added semaphore {} with value {}"), semaphoreName, semaphore);
            } else {
               this.logger.error(LoggerUtils.formatArchRow("Error in response format: no semaphore data"));
            }
         } catch (RestClientException var12) {
            this.logger.error(LoggerUtils.formatArchRow("Rest error retrieving {} semaphore data: {}"), semaphoreName, var12);
         }
      }

   }

   /**
    * Scheduled refresh.
    */
   private void scheduledRefresh() {
      this.logger.debug(LoggerUtils.formatArchRow("Scheduled refresh: look for expired semaphores data"));
      List<String> expiredSemaphores = (List)this.actualSemaphoreMap.values().stream().filter(this::isExpired).map(CopySwitcherSemaphore::getName).collect(Collectors.toList());
      if (!expiredSemaphores.isEmpty()) {
         this.logger.debug(LoggerUtils.formatArchRow("Starting scheduled refresh for semaphores: {}"), expiredSemaphores);
         this.getSemaphoresStatus(expiredSemaphores);
      }

   }

   /**
    * Destroy.
    *
    * @throws Exception the exception
    */
   public void destroy() throws Exception {
      if (this.scheduledExecutorService != null) {
         this.scheduledExecutorService.shutdown();
      }

   }
}

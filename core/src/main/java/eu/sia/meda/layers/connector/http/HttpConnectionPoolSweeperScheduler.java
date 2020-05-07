package eu.sia.meda.layers.connector.http;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

/**
 * The Class HttpConnectionPoolSweeperScheduler.
 */
@Component
public class HttpConnectionPoolSweeperScheduler {
   
   /** The Constant poolSize. */
   private static final int POOL_SIZE = 1;
   
   /** The scheduler. */
   private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(POOL_SIZE);

   /**
    * Adds the sweeper.
    *
    * @param task the task
    * @param intervalMillis the interval millis
    * @return the scheduled future
    */
   ScheduledFuture<?> addSweeper(Runnable task, long intervalMillis) {
      return this.scheduler.scheduleWithFixedDelay(task, intervalMillis, intervalMillis, TimeUnit.MILLISECONDS);
   }
}

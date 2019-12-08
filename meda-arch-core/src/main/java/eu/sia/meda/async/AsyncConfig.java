package eu.sia.meda.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

/**
 * The Class AsyncConfig.
 */
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {

   /**
    * Gets the async executor.
    *
    * @return the async executor
    */
   @Override
   public Executor getAsyncExecutor() {
      return new SimpleAsyncTaskExecutor();
   }
   
}

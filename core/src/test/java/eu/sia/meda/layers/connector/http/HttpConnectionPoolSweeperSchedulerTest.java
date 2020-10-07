package eu.sia.meda.layers.connector.http;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

public class HttpConnectionPoolSweeperSchedulerTest {

    @Test
    public void test() throws InterruptedException, ExecutionException {
        HttpConnectionPoolSweeperScheduler scheduler = new HttpConnectionPoolSweeperScheduler();
        ScheduledFuture<?> scheduledFuture = scheduler.addSweeper(() -> {}, 1);
        Assert.assertNotNull(scheduledFuture);
    }
}

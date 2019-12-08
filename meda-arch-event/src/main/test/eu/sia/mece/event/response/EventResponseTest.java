package eu.sia.meda.event.response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;
import org.springframework.kafka.support.SendResult;

public class EventResponseTest  {

  @Test
  public void test0()  throws Throwable  {
      EventResponse<Object> eventResponse0 = new EventResponse<Object>(true, "", null);
      Boolean boolean0 = eventResponse0.getResult();
      assertTrue(boolean0);
  }

  @Test
  public void test1()  throws Throwable  {
      EventResponse<String> eventResponse0 = new EventResponse<String>(true, null, null);
      assertNull(eventResponse0.getMessage());
      assertTrue(eventResponse0.getResult());
  }

  @Test
  public void test2()  throws Throwable  {
      EventResponse<Boolean> eventResponse0 = new EventResponse<Boolean>(false, "RQ?^]?KF-PrfAU\"\"]E");
      assertNotNull(eventResponse0.getMessage());
      assertFalse(eventResponse0.getResult());
  }

  @Test
  public void test3()  throws Throwable  {
      EventResponse<Boolean> eventResponse0 = new EventResponse<Boolean>(false, "", null);
      eventResponse0.getSendResultFuture();
      assertFalse(eventResponse0.getResult());
  }

  @Test
  public void test4()  throws Throwable  {
      EventResponse<Boolean> eventResponse0 = new EventResponse<Boolean>(false, "", null);
      eventResponse0.getMessage();
      assertFalse(eventResponse0.getResult());
  }

  @Test
  public void test5()  throws Throwable  {
      EventResponse<SendResult<Object, Object>> eventResponse0 = new EventResponse<SendResult<Object, Object>>(false, "");
      Boolean boolean0 = eventResponse0.getResult();
      assertFalse(boolean0);
  }
}

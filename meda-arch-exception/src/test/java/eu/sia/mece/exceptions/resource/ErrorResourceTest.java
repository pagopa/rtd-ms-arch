package eu.sia.meda.exceptions.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import eu.sia.meda.exceptions.model.ErrorMessage;

public class ErrorResourceTest {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      ErrorResource errorResource0 = new ErrorResource();
      Map<String, List<ErrorMessage>> map0 = errorResource0.getReturnMessages();
      assertNull(map0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      ErrorResource errorResource0 = new ErrorResource();
      errorResource0.setReturnMessages((Map<String, List<ErrorMessage>>) null);
      assertFalse(errorResource0.hasLinks());
  }
}

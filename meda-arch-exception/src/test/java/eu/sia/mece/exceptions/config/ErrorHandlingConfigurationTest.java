package eu.sia.meda.exceptions.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;



public class ErrorHandlingConfigurationTest {

  @Test
  public void test00()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      errorHandlingConfiguration0.setEnableUnknowExceptionMapping(true);
      boolean boolean0 = errorHandlingConfiguration0.isEnableUnknowExceptionMapping();
      assertTrue(boolean0);
  }

  @Test
  public void test01()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      errorHandlingConfiguration0.setEnableStacktraceLogging(true);
      boolean boolean0 = errorHandlingConfiguration0.isEnableStacktraceLogging();
      assertTrue(boolean0);
  }

  @Test
  public void test02()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      errorHandlingConfiguration0.setEnableExceptionLogging(true);
      boolean boolean0 = errorHandlingConfiguration0.isEnableExceptionLogging();
      assertTrue(boolean0);
  }

  @Test
  public void test03()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      errorHandlingConfiguration0.setEnableExceptionDecoding(true);
      boolean boolean0 = errorHandlingConfiguration0.isEnableExceptionDecoding();
      assertTrue(boolean0);
  }

  @Test
  public void test04()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      errorHandlingConfiguration0.setUnknowExceptionTag(":rY^a(5{s3RE!M~uKQ");
      String string0 = errorHandlingConfiguration0.getUnknowExceptionTag();
      assertEquals(":rY^a(5{s3RE!M~uKQ", string0);
  }

  @Test
  public void test05()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      errorHandlingConfiguration0.setEnableExceptionEvent(true);
      boolean boolean0 = errorHandlingConfiguration0.isEnableExceptionEvent();
      assertTrue(boolean0);
  }

  @Test
  public void test06()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      boolean boolean0 = errorHandlingConfiguration0.isEnableExceptionDecoding();
      assertFalse(boolean0);
  }

  @Test
  public void test07()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      boolean boolean0 = errorHandlingConfiguration0.isEnableExceptionEvent();
      assertFalse(boolean0);
  }

  @Test
  public void test08()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      boolean boolean0 = errorHandlingConfiguration0.isEnableStacktraceLogging();
      assertFalse(boolean0);
  }

  @Test
  public void test09()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      errorHandlingConfiguration0.setUnknowExceptionTag("");
      String string0 = errorHandlingConfiguration0.getUnknowExceptionTag();
      assertEquals("", string0);
  }

  @Test
  public void test10()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      boolean boolean0 = errorHandlingConfiguration0.isEnableUnknowExceptionMapping();
      assertFalse(boolean0);
  }

  @Test
  public void test11()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      boolean boolean0 = errorHandlingConfiguration0.isEnableExceptionLogging();
      assertFalse(boolean0);
  }

  @Test
  public void test12()  throws Throwable  {
      ErrorHandlingConfiguration errorHandlingConfiguration0 = new ErrorHandlingConfiguration();
      String string0 = errorHandlingConfiguration0.getUnknowExceptionTag();
      assertNull(string0);
  }
}

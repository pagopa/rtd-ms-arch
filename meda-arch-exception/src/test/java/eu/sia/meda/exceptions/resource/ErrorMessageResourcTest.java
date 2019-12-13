package eu.sia.meda.exceptions.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import eu.sia.meda.exceptions.model.MessageForm;

public class ErrorMessageResourcTest {

	@Test(timeout = 4000)
	public void test00() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessageTitle("eu.sia.meda.exceptions.resource.ErrorMessageResource");
		String string0 = errorMessageResource0.getMessageTitle();
		assertEquals("eu.sia.meda.exceptions.resource.ErrorMessageResource", string0);
	}

	@Test(timeout = 4000)
	public void test01() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessageTitle("");
		String string0 = errorMessageResource0.getMessageTitle();
		assertEquals("", string0);
	}

	@Test(timeout = 4000)
	public void test02() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessageKey("");
		String string0 = errorMessageResource0.getMessageKey();
		assertEquals("", string0);
	}

	@Test(timeout = 4000)
	public void test03() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessage("READ_WRITE");
		String string0 = errorMessageResource0.getMessage();
		assertEquals("READ_WRITE", string0);
	}

	@Test(timeout = 4000)
	public void test04() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessage("");
		String string0 = errorMessageResource0.getMessage();
		assertEquals("", string0);
	}

	@Test(timeout = 4000)
	public void test05() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setErrorCode("sV");
		String string0 = errorMessageResource0.getErrorCode();
		assertEquals("sV", string0);
	}

	@Test(timeout = 4000)
	public void test06() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setErrorCode("");
		String string0 = errorMessageResource0.getErrorCode();
		assertEquals("", string0);
	}

	@Test(timeout = 4000)
	public void test07() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessagesForm((MessageForm) null);
		assertNull(errorMessageResource0.getErrorCode());
	}

	@Test(timeout = 4000)
	public void test08() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		String string0 = errorMessageResource0.getMessageTitle();
		assertNull(string0);
	}

	@Test(timeout = 4000)
	public void test09() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		String string0 = errorMessageResource0.getMessageKey();
		assertNull(string0);
	}

	@Test(timeout = 4000)
	public void test10() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessagesForm((List<MessageForm>) null);
		assertNull(errorMessageResource0.getMessageKey());
	}

	@Test(timeout = 4000)
	public void test11() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		Boolean boolean0 = errorMessageResource0.getRetry();
		assertNull(boolean0);
	}

	@Test(timeout = 4000)
	public void test12() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		String string0 = errorMessageResource0.getErrorCode();
		assertNull(string0);
	}

	@Test(timeout = 4000)
	public void test13() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setMessageKey("next");
		String string0 = errorMessageResource0.getMessageKey();
		assertEquals("next", string0);
	}

	@Test(timeout = 4000)
	public void test14() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		String string0 = errorMessageResource0.getMessage();
		assertNull(string0);
	}

	@Test(timeout = 4000)
	public void test15() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		errorMessageResource0.setRetry((Boolean) null);
		assertNull(errorMessageResource0.getErrorCode());
	}

	@Test(timeout = 4000)
	public void test16() throws Throwable {
		ErrorMessageResource errorMessageResource0 = new ErrorMessageResource();
		List<MessageForm> list0 = errorMessageResource0.getMessagesForm();
		assertNull(list0);
	}
}

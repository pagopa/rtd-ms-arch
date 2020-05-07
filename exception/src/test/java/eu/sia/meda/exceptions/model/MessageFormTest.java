/*
 * This file was automatically generated by EvoSuite
 * Fri Nov 22 23:42:33 GMT 2019
 */

package eu.sia.meda.exceptions.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MessageFormTest {

	@Test(timeout = 4000)
	public void test0() throws Throwable {
		MessageForm messageForm0 = new MessageForm();
		messageForm0.setFormName("eu.sia.meda.exceptions.model.MessageForm");
		String string0 = messageForm0.getFormName();
		assertEquals("eu.sia.meda.exceptions.model.MessageForm", string0);
	}

	@Test(timeout = 4000)
	public void test1() throws Throwable {
		MessageForm messageForm0 = new MessageForm();
		messageForm0.setFieldName("eu.sia.meda.exceptions.model.MessageForm");
		String string0 = messageForm0.getFieldName();
		assertEquals("eu.sia.meda.exceptions.model.MessageForm", string0);
	}

	@Test(timeout = 4000)
	public void test2() throws Throwable {
		MessageForm messageForm0 = new MessageForm();
		messageForm0.setFieldName("");
		String string0 = messageForm0.getFieldName();
		assertEquals("", string0);
	}

	@Test(timeout = 4000)
	public void test3() throws Throwable {
		MessageForm messageForm0 = new MessageForm();
		String string0 = messageForm0.getFieldName();
		assertNull(string0);
	}

	@Test(timeout = 4000)
	public void test4() throws Throwable {
		MessageForm messageForm0 = new MessageForm();
		String string0 = messageForm0.getFormName();
		assertNull(string0);
	}

	@Test(timeout = 4000)
	public void test5() throws Throwable {
		MessageForm messageForm0 = new MessageForm();
		messageForm0.setFormName("");
		String string0 = messageForm0.getFormName();
		assertEquals("", string0);
	}
}

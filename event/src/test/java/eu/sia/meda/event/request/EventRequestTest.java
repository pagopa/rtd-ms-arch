package eu.sia.meda.event.request;

import org.apache.kafka.common.header.Headers;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventRequestTest {

	@Test
	public void test00() throws Throwable {
		EventRequest<Object> eventRequest0 = new EventRequest<Object>();
		eventRequest0.setTopic("topic");
		String string0 = eventRequest0.getTopic();
		assertEquals("topic", string0);
	}

	@Test
	public void testSetTopic() throws Throwable {
		EventRequest<Object> eventRequest0 = new EventRequest<Object>();
		eventRequest0.setTopic("");
		String string0 = eventRequest0.getTopic();
		assertEquals("", string0);
	}

	@Test
	public void testGetPayload0() throws Throwable {
		EventRequest<Object> eventRequest0 = new EventRequest<Object>();
		byte[] byteArray0 = new byte[2];
		eventRequest0.setPayload(byteArray0);
		byte[] byteArray1 = eventRequest0.getPayload();
		assertEquals(2, byteArray1.length);
	}

	@Test
	public void testSetkey() throws Throwable {
		EventRequest<Integer> eventRequest0 = new EventRequest<Integer>();
		eventRequest0.setKey("=fCxONgLQXm");
		String string0 = eventRequest0.getKey();
		assertEquals("=fCxONgLQXm", string0);
	}

	@Test
	public void testgetKey() throws Throwable {
		EventRequest<Integer> eventRequest0 = new EventRequest<Integer>();
		eventRequest0.setKey("");
		String string0 = eventRequest0.getKey();
		assertEquals("", string0);
	}

	@Test
	public void testGetHeaders() throws Throwable {
		EventRequest<Integer> eventRequest0 = new EventRequest<Integer>();
		Headers headers0 = eventRequest0.getHeaders();
		assertNull(headers0);
	}

	@Test
	public void testGetkey() throws Throwable {
		EventRequest<Object> eventRequest0 = new EventRequest<Object>();
		String string0 = eventRequest0.getKey();
		assertNull(string0);
	}

	@Test
	public void testgetPayload() throws Throwable {
		EventRequest<Integer> eventRequest0 = new EventRequest<Integer>();
		byte[] byteArray0 = new byte[0];
		eventRequest0.setPayload(byteArray0);
		byte[] byteArray1 = eventRequest0.getPayload();
		assertArrayEquals(new byte[] {}, byteArray1);
	}

	@Test
	public void testSetHeaders() throws Throwable {
		EventRequest<Object> eventRequest0 = new EventRequest<Object>();
		eventRequest0.setHeaders((Headers) null);
		assertNull(eventRequest0.getTopic());
	}

	@Test
	public void testGetTopic() throws Throwable {
		EventRequest<Object> eventRequest0 = new EventRequest<Object>();
		String string0 = eventRequest0.getTopic();
		assertNull(string0);
	}

	@Test
	public void testGetPayload1() throws Throwable {
		EventRequest<String> eventRequest0 = new EventRequest<String>();
		byte[] byteArray0 = eventRequest0.getPayload();
		assertNull(byteArray0);
	}
}

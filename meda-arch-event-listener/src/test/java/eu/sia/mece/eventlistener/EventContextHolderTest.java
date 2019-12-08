package eu.sia.meda.eventlistener;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;


public class EventContextHolderTest {

	@Test
	public void testForceSetRecord() throws Throwable {
		try {
			EventContextHolder.forceSetRecord(null);
			fail("Expecting exception: NullPointerException");

		} catch (NullPointerException e) {
			assertTrue(true, "Null record not allowed!");
		}
	}
	
	@Test
	public void testNullSetRecord() throws Throwable {
		try {
			EventContextHolder.setRecord(null);
			fail("Expecting exception: NullPointerException");

		} catch (NullPointerException e) {
			assertTrue(true, "Null record not allowed!");
		}
	}
	
	@Test
	void testSetGetRecord() {
		// Setup
		final ConsumerRecord<String, String> rec = new ConsumerRecord<>("topic", 0, 0L, "key", "value");

		// Run the test
		EventContextHolder.setRecord(rec);

		// Verify the results

		final ConsumerRecord<String, String> result = EventContextHolder.getRecord();

		assertEquals(rec, result);
	}

	@Test
	void testClear() {
		// Setup
		final ConsumerRecord<String, String> rec = new ConsumerRecord<>("topic", 0, 0L, "key", "value");
		EventContextHolder.setRecord(rec);

		// Verify the results
		assertNotNull(EventContextHolder.getRecord());

		// Run the test
		EventContextHolder.clear();

		// Verify the results
		assertNull(EventContextHolder.getRecord());
	}

}

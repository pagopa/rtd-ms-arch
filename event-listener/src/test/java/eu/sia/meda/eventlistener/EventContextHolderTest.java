package eu.sia.meda.eventlistener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;


public class EventContextHolderTest {

    @BeforeEach
    public void init() {
        EventContextHolder.clear();
    }


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

    @Test
    public void testSetRecordInitialized() {
        ConsumerRecord<String, String> rec = new ConsumerRecord<>("topic", 0, 0L, "key", "value");

        EventContextHolder.setRecord(rec);

        try {
            EventContextHolder.setRecord(rec);
            Assert.fail("exception expected");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testForceSetRecordValid() {
        final ConsumerRecord<String, String> rec = new ConsumerRecord<>("topic", 0, 0L, "key", "value");

        EventContextHolder.forceSetRecord(rec);

        final ConsumerRecord<String, String> result = EventContextHolder.getRecord();

        Assert.assertEquals(rec, result);
    }

}

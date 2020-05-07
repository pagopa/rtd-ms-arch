package eu.sia.meda.core.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseResourceTest {

    private BaseResource baseResourceUnderTest;

    @BeforeEach
    void setUp() {
        baseResourceUnderTest = new BaseResource();
    }
    
    @Test
    void testGetEntityId() {
        // Setup
    	baseResourceUnderTest.setEntityId("entityId");

        // Verify the results
        assertEquals("entityId", baseResourceUnderTest.getEntityId());
    }
}

package eu.sia.meda.core.assembler;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseResourceAssemblerSupportTest {

    @Test
    void testBuildResourcePath() {
        assertEquals("/resourceCollectionName/id", BaseResourceAssemblerSupport.buildResourcePath("id", "resourceCollectionName"));
    }

    @Test
    void testGetIdFromResourceLink() {
        // Setup
        final Link link = new Link("href", "rel");
        final String expectedResult = "href";

        // Run the test
        final String result = BaseResourceAssemblerSupport.getIdFromResourceLink(link);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

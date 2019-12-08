package eu.sia.meda.connector.rest.mock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeaderEntryMatcherTest {

    private HeaderEntryMatcher headerEntryMatcherUnderTest;

    @BeforeEach
    void setUp() {
        headerEntryMatcherUnderTest = new HeaderEntryMatcher();
    }

    @Test
    void testGetName() {
        // Setup
        final String expectedResult = "result";

        // Run the test
        final String result = headerEntryMatcherUnderTest.getName();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testMatch() {
        // Setup
        final Request request = null;
        final Parameters parameters = new Parameters();
        final MatchResult expectedResult = MatchResult.partialMatch(0.0);

        // Run the test
        final MatchResult result = headerEntryMatcherUnderTest.match(request, parameters);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

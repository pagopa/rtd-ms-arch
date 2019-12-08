package eu.sia.meda.connector.medacore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtTokenDTOTest {

    private JwtTokenDTO jwtTokenDTOUnderTest;

    @BeforeEach
    void setUp() {
        jwtTokenDTOUnderTest = new JwtTokenDTO();
    }

    @Test
    void testToString() {
        // Setup
        final String expectedResult = "result";

        // Run the test
        final String result = jwtTokenDTOUnderTest.toString();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

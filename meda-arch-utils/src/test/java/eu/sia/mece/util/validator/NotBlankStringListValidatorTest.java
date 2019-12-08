package eu.sia.meda.util.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NotBlankStringListValidatorTest {

    private NotBlankStringListValidator notBlankStringListValidatorUnderTest;

    @BeforeEach
    void setUp() {
        notBlankStringListValidatorUnderTest = new NotBlankStringListValidator();
    }

    @Test
    void testIsValid() {
        // Setup
        final List<String> strings = Arrays.asList();
        final ConstraintValidatorContext constraintValidatorContext = null;

        // Run the test
        final boolean result = notBlankStringListValidatorUnderTest.isValid(strings, constraintValidatorContext);

        // Verify the results
        assertTrue(result);
    }
}

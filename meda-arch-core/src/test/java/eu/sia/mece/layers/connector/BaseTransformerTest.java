package eu.sia.meda.layers.connector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BaseTransformerTest {

    private BaseTransformer<Long, String> baseTransformerUnderTest;

    @BeforeEach
    void setUp() {
        baseTransformerUnderTest = new BaseTransformer<Long, String>() {
            @Override
            public String transform(Long from) {
                return String.valueOf(from);
            }
        };
    }

    @Test
    void testTransform() {
        // Setup
        final Collection<Long> from = Arrays.asList(1L, 2L, 3L);
 
        // Run the test
        final Collection<String> result = baseTransformerUnderTest.transform(from);

        // Verify the results
        assertNotNull(result);
        assertEquals(3, result.size());
        from.forEach(a -> result.contains(a.toString()));
    }
}

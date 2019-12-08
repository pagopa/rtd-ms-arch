package eu.sia.meda.connector.jpa.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultQueryHintsExtTest {

    private DefaultQueryHintsExt defaultQueryHintsExtUnderTest;

    @BeforeEach
    void setUp() {
        defaultQueryHintsExtUnderTest = null /* TODO: construct the instance */;
    }

    @Test
    void testWithFetchGraphs() {
        // Setup
        final EntityManager em = null;
        final QueryHintsExt expectedResult = null;

        // Run the test
        final QueryHintsExt result = defaultQueryHintsExtUnderTest.withFetchGraphs(em);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testIterator() {
        // Setup
        final Iterator<Map.Entry<String, Object>> expectedResult = null;

        // Run the test
        final Iterator<Map.Entry<String, Object>> result = defaultQueryHintsExtUnderTest.iterator();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testAsMap() {
        // Setup
        final Map<String, Object> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, Object> result = defaultQueryHintsExtUnderTest.asMap();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetFetchGraphs() {
        // Setup
        final Map<String, Object> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, Object> result = defaultQueryHintsExtUnderTest.getFetchGraphs();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetEntityGraph() {
        // Setup
        final EntityGraph entityGraph = null;
        final JpaEntityGraph expectedResult = new JpaEntityGraph("name", EntityGraph.EntityGraphType.LOAD, new String[]{});

        // Run the test
        final JpaEntityGraph result = defaultQueryHintsExtUnderTest.getEntityGraph(entityGraph);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testOf() {
        // Setup
        final JpaEntityInformation<?, ?> information = null;
        final CrudMethodMetadata metadata = null;
        final QueryHintsExt expectedResult = null;

        // Run the test
        final QueryHintsExt result = DefaultQueryHintsExt.of(information, metadata);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}

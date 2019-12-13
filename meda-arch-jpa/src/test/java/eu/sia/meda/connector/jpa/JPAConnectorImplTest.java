package eu.sia.meda.connector.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

class JPAConnectorImplTest {

    @Mock
    private JpaEntityInformation<JpaEntityTest, ?> mockEntityInformation;
    @Mock
    private EntityManager mockEntityManager;

    private JPAConnectorImpl<JpaEntityTest, Long> jpaConnectorImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        jpaConnectorImplUnderTest = new JPAConnectorImpl<>(mockEntityInformation, mockEntityManager);
    }

    @Test
    void testDeleteById() {
        // Setup
        final Long id = null;
        when(mockEntityManager.contains(any(Object.class))).thenReturn(false);
        when(mockEntityManager.merge(any(JpaEntityTest.class))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(new HashMap<>()))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(LockModeType.READ), eq(new HashMap<>()))).thenReturn(null);
        when(mockEntityInformation.getJavaType()).thenReturn(JpaEntityTest.class);

        // Run the test
        jpaConnectorImplUnderTest.deleteById(id);

        // Verify the results
        verify(mockEntityManager).remove(any(Object.class));
    }

    @Test
    void testDelete() {
        // Setup
        final JpaEntityTest entity = null;
        when(mockEntityManager.contains(any(Object.class))).thenReturn(false);
        when(mockEntityManager.merge(any(JpaEntityTest.class))).thenReturn(null);

        // Run the test
        jpaConnectorImplUnderTest.delete(entity);

        // Verify the results
        verify(mockEntityManager).remove(any(Object.class));
    }

    @Test
    void testDeleteAll() {
        // Setup
        final Iterable<? extends JpaEntityTest> entities = Arrays.asList();
        when(mockEntityManager.contains(any(Object.class))).thenReturn(false);
        when(mockEntityManager.merge(any(JpaEntityTest.class))).thenReturn(null);

        // Run the test
        jpaConnectorImplUnderTest.deleteAll(entities);

        // Verify the results
        verify(mockEntityManager).remove(any(Object.class));
    }

    @Test
    void testDeleteInBatch() {
        // Setup
        final Iterable<JpaEntityTest> entities = Arrays.asList();
        when(mockEntityInformation.getEntityName()).thenReturn("result");

        // Run the test
        jpaConnectorImplUnderTest.deleteInBatch(entities);

        // Verify the results
    }

    @Test
    void testDeleteAll1() {
        // Setup
        when(mockEntityManager.contains(any(Object.class))).thenReturn(false);
        when(mockEntityManager.merge(any(JpaEntityTest.class))).thenReturn(null);

        // Run the test
        jpaConnectorImplUnderTest.deleteAll();

        // Verify the results
        verify(mockEntityManager).remove(any(Object.class));
    }

    @Test
    void testDeleteAllInBatch() {
        // Setup
        when(mockEntityManager.createQuery("qlString")).thenReturn(null);
        when(mockEntityInformation.getEntityName()).thenReturn("result");

        // Run the test
        jpaConnectorImplUnderTest.deleteAllInBatch();

        // Verify the results
    }

    @Test
    void testFindById() {
        // Setup
        final Long id = null;
        final Optional<JpaEntityTest> expectedResult = Optional.empty();
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(new HashMap<>()))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(LockModeType.READ), eq(new HashMap<>()))).thenReturn(null);

        // Run the test
        final Optional<JpaEntityTest> result = jpaConnectorImplUnderTest.findById(id);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetOne() {
        // Setup
        final Long id = null;
        final JpaEntityTest expectedResult = null;
        when(mockEntityManager.getReference(eq(JpaEntityTest.class), any(Object.class))).thenReturn(null);

        // Run the test
        final JpaEntityTest result = jpaConnectorImplUnderTest.getOne(id);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testExistsById() {
        // Setup
        final Long id = null;
        when(mockEntityInformation.getIdAttribute()).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(new HashMap<>()))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(LockModeType.READ), eq(new HashMap<>()))).thenReturn(null);
        when(mockEntityInformation.getEntityName()).thenReturn("result");
        when(mockEntityInformation.getIdAttributeNames()).thenReturn(Arrays.asList());
        when(mockEntityManager.createQuery("qlString", Long.class)).thenReturn(null);
        when(mockEntityInformation.hasCompositeId()).thenReturn(false);
        when(mockEntityInformation.getCompositeIdAttributeValue(any(Object.class), eq("idAttribute"))).thenReturn("result");

        // Run the test
        final boolean result = jpaConnectorImplUnderTest.existsById(id);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testFindAll() {
        // Setup
        final List<JpaEntityTest> expectedResult = Arrays.asList();

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAllById() {
        // Setup
        final Iterable<Long> ids = Arrays.asList();
        final List<JpaEntityTest> expectedResult = Arrays.asList();
        when(mockEntityInformation.hasCompositeId()).thenReturn(false);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(new HashMap<>()))).thenReturn(null);
        when(mockEntityManager.find(eq(JpaEntityTest.class), any(Object.class), eq(LockModeType.READ), eq(new HashMap<>()))).thenReturn(null);

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.findAllById(ids);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAll1() {
        // Setup
        final Sort sort = new Sort(Sort.Direction.ASC, "properties");
        final List<JpaEntityTest> expectedResult = Arrays.asList();

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(sort);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAll2() {
        // Setup
        final Pageable pageable = null;
        final Page<JpaEntityTest> expectedResult = null;

        // Run the test
        final Page<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(pageable);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindOne() {
        // Setup
        final Specification<JpaEntityTest> spec = null;
        final Optional<JpaEntityTest> expectedResult = Optional.empty();

        // Run the test
        final Optional<JpaEntityTest> result = jpaConnectorImplUnderTest.findOne(spec);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAll3() {
        // Setup
        final Specification<JpaEntityTest> spec = null;
        final List<JpaEntityTest> expectedResult = Arrays.asList();

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(spec);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAll4() {
        // Setup
        final Specification<JpaEntityTest> spec = null;
        final Pageable pageable = null;
        final Page<JpaEntityTest> expectedResult = null;

        // Run the test
        final Page<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(spec, pageable);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAll5() {
        // Setup
        final Specification<JpaEntityTest> spec = null;
        final Sort sort = new Sort(Sort.Direction.ASC, "properties");
        final List<JpaEntityTest> expectedResult = Arrays.asList();

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(spec, sort);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testCount() {
        // Setup
        final Example<JpaEntityTest> example = null;
        final long expectedResult = 0L;
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(null);
        when(mockEntityManager.createQuery(any(CriteriaQuery.class))).thenReturn(null);

        // Run the test
        final long result = jpaConnectorImplUnderTest.count(example);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testExists() {
        // Setup
        final Example<JpaEntityTest> example = null;
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(null);
        when(mockEntityManager.createQuery(any(CriteriaQuery.class))).thenReturn(null);

        // Run the test
        final boolean result = jpaConnectorImplUnderTest.exists(example);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testFindAll6() {
        // Setup
        final Example<JpaEntityTest> example = null;
        final List<JpaEntityTest> expectedResult = Arrays.asList();
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(null);
        when(mockEntityManager.createQuery(any(CriteriaQuery.class))).thenReturn(null);

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(example);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAll7() {
        // Setup
        final Example<JpaEntityTest> example = null;
        final Sort sort = new Sort(Sort.Direction.ASC, "properties");
        final List<JpaEntityTest> expectedResult = Arrays.asList();
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(null);
        when(mockEntityManager.createQuery(any(CriteriaQuery.class))).thenReturn(null);

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(example, sort);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAll8() {
        // Setup
        final Example<JpaEntityTest> example = null;
        final Pageable pageable = null;
        final Page<JpaEntityTest> expectedResult = null;

        // Run the test
        final Page<JpaEntityTest> result = jpaConnectorImplUnderTest.findAll(example, pageable);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testCount1() {
        // Setup
        final long expectedResult = 0L;
        when(mockEntityManager.createQuery("qlString", Long.class)).thenReturn(null);
        when(mockEntityInformation.getEntityName()).thenReturn("result");

        // Run the test
        final long result = jpaConnectorImplUnderTest.count();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testCount2() {
        // Setup
        final Specification<JpaEntityTest> spec = null;
        final long expectedResult = 0L;
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(null);
        when(mockEntityManager.createQuery(any(CriteriaQuery.class))).thenReturn(null);

        // Run the test
        final long result = jpaConnectorImplUnderTest.count(spec);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSave() {
        // Setup
        final JpaEntityTest entity = null;
        final JpaEntityTest expectedResult = null;
        when(mockEntityInformation.isNew(any(JpaEntityTest.class))).thenReturn(false);
        when(mockEntityManager.merge(any(JpaEntityTest.class))).thenReturn(null);

        // Run the test
        final JpaEntityTest result = jpaConnectorImplUnderTest.save(entity);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockEntityManager).persist(any(Object.class));
    }

    @Test
    void testSaveAndFlush() {
        // Setup
        final JpaEntityTest entity = null;
        final JpaEntityTest expectedResult = null;
        when(mockEntityInformation.isNew(any(JpaEntityTest.class))).thenReturn(false);
        when(mockEntityManager.merge(any(JpaEntityTest.class))).thenReturn(null);

        // Run the test
        final JpaEntityTest result = jpaConnectorImplUnderTest.saveAndFlush(entity);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockEntityManager).persist(any(Object.class));
        verify(mockEntityManager).flush();
    }

    @Test
    void testSaveAll() {
        // Setup
        final Iterable<JpaEntityTest> entities = Arrays.asList();
        final List<JpaEntityTest> expectedResult = Arrays.asList();
        when(mockEntityInformation.isNew(any(JpaEntityTest.class))).thenReturn(false);
        when(mockEntityManager.merge(any(JpaEntityTest.class))).thenReturn(null);

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.saveAll(entities);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockEntityManager).persist(any(Object.class));
    }

    @Test
    void testFlush() {
        // Setup

        // Run the test
        jpaConnectorImplUnderTest.flush();

        // Verify the results
        verify(mockEntityManager).flush();
    }

    @Test
    void testExecuteSQLQuery() throws Exception {
        // Setup
        final List<JpaEntityTest> expectedResult = Arrays.asList();
        when(mockEntityManager.createNativeQuery("sqlString")).thenReturn(null);

        // Run the test
        final List<JpaEntityTest> result = jpaConnectorImplUnderTest.executeSQLQuery("query");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testExecuteSQLQuery_ThrowsException() {
        // Setup
        when(mockEntityManager.createNativeQuery("sqlString")).thenReturn(null);

        // Run the test
        assertThrows(Exception.class, () -> {
            jpaConnectorImplUnderTest.executeSQLQuery("query");
        });
    }
    
    private class JpaEntityTest {
    	private String attr1;
    	private String attr2;
    }
}

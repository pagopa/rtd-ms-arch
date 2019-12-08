package eu.sia.meda.connector.jpa.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class JPAConnectorConfigTest {

    @Mock
    private ArchJPAConfigurationService mockConfigurations;

    @InjectMocks
    private JPAConnectorConfig jpaConnectorConfigUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testJpaDataSource() {
        // Setup
        final DataSource expectedResult = null;
        when(mockConfigurations.retrieveJPAConnection("className")).thenReturn(new ArchJPAConfigurationService.JPAConnection());

        // Run the test
        final DataSource result = jpaConnectorConfigUnderTest.jpaDataSource();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testEntityManagerFactory() {
        // Setup
        final LocalContainerEntityManagerFactoryBean expectedResult = new LocalContainerEntityManagerFactoryBean();
        when(mockConfigurations.retrieveJPAConnection("className")).thenReturn(new ArchJPAConfigurationService.JPAConnection());

        // Run the test
        final LocalContainerEntityManagerFactoryBean result = jpaConnectorConfigUnderTest.entityManagerFactory();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testJpaVendorAdapter() {
        // Setup
        final JpaVendorAdapter expectedResult = null;
        when(mockConfigurations.retrieveJPAConnection("className")).thenReturn(new ArchJPAConfigurationService.JPAConnection());

        // Run the test
        final JpaVendorAdapter result = jpaConnectorConfigUnderTest.jpaVendorAdapter();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testTransactionManager() throws Exception {
        // Setup
        final PlatformTransactionManager expectedResult = null;
        when(mockConfigurations.retrieveJPAConnection("className")).thenReturn(new ArchJPAConfigurationService.JPAConnection());

        // Run the test
        final PlatformTransactionManager result = jpaConnectorConfigUnderTest.transactionManager();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testTransactionManager_ThrowsSQLException() {
        // Setup
        when(mockConfigurations.retrieveJPAConnection("className")).thenReturn(new ArchJPAConfigurationService.JPAConnection());

        // Run the test
        assertThrows(SQLException.class, () -> {
            jpaConnectorConfigUnderTest.transactionManager();
        });
    }
}

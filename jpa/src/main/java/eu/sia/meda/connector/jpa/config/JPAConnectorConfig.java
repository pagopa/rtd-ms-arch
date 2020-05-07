/*
 * 
 */
package eu.sia.meda.connector.jpa.config;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.connector.jpa.JPAConnectorImpl;
import eu.sia.meda.util.MockUtils;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Class JPAConnectorConfig.
 */
@Configuration
@EnableTransactionManagement
@ConditionalOnProperty(
   prefix = "connectors",
   name = {"jpaConfigurations.connection.url"}
)
@EnableJpaRepositories(
   repositoryBaseClass = JPAConnectorImpl.class,
   basePackages = {"eu.sia.meda"}
)
public class JPAConnectorConfig {
   
   /** The Constant log. */
   private static final Logger log = LoggerUtils.getLogger(JPAConnectorConfig.class);
   
   /** The configurations. */
   @Autowired
   @Qualifier("JPAConfiguration")
   private ArchJPAConfigurationService configurations;

   /**
    * Jpa data source.
    *
    * @return the data source
    */
   @Bean(
      name = {"JPADataSource"}
   )
   public DataSource jpaDataSource() {
      ArchJPAConfigurationService.JPAConnection jpaConnection = this.configurations.retrieveJPAConnection((String)null);
      if (jpaConnection == null) {
         throw new ExceptionInInitializerError();
      } else if (jpaConnection.isMocked()) {
         log.warn(LoggerUtils.formatArchRow("No JPA configuration found. H2 in-memory DataBase pulled up"));
         EmbeddedDatabaseBuilder edb = (new EmbeddedDatabaseBuilder()).setType(EmbeddedDatabaseType.H2).setScriptEncoding("UTF-8");

         try {
            List<String> mockLocations = MockUtils.getMockResourcePath(jpaConnection.getPath(), jpaConnection.getFiles(), "sql");
            Iterator var4 = mockLocations.iterator();

            while(var4.hasNext()) {
               String location = (String)var4.next();
               edb.addScript(location);
               if(log.isDebugEnabled()){
                   log.debug(String.format("Loading H2 sql script: %s", location));
               }
            }
         } catch (IOException var6) {
            log.error("Error retrieving mock init files: {}", var6.getMessage());
         }

         return edb.continueOnError(true).ignoreFailedDrops(true).build();
      } else {
         HikariDataSource ds = new HikariDataSource();
         ds.setMaximumPoolSize(jpaConnection.getConnectionPoolSize());
         ds.setConnectionTimeout((long)jpaConnection.getTimeout());
         ds.setDriverClassName(jpaConnection.getDriverClassName());
         ds.setJdbcUrl(jpaConnection.getUrl());
         ds.setUsername(jpaConnection.getUsername());
         ds.setPassword(jpaConnection.getPassword());
         if(StringUtils.isNotBlank(jpaConnection.getSchema())) {
        	 ds.setSchema(jpaConnection.getSchema());
         }
         return ds;
      }
   }

   /**
    * Entity manager factory.
    *
    * @return the local container entity manager factory bean
    */
   @Bean(
      name = {"entityManagerFactory"}
   )
   @Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		ArchJPAConfigurationService.JPAConnection jpaConnection = this.configurations
				.retrieveJPAConnection((String) null);
		List<String> propertyPackages = jpaConnection.getEntityPackages();
		if (propertyPackages.isEmpty()) {
			propertyPackages.add("eu.sia.meda");
		}

		String[] packagesToScan = (String[]) propertyPackages.toArray(new String[propertyPackages.size()]);
		Properties jpaProperties = new Properties();

		
		jpaProperties.put("hibernate.dialect", jpaConnection.getHibernateDialect());
		jpaProperties.put("hibernate.show_sql", jpaConnection.isShowSql());
		jpaProperties.put("hibernate.jdbc.batch_size", jpaConnection.getBatchSize());
		jpaProperties.put("hibernate.order_inserts", jpaConnection.isOrderInserts());
		jpaProperties.put("hibernate.order_updates", jpaConnection.isOrderUpdates());
		jpaProperties.put("hibernate.jdbc.batch_versioned_data", jpaConnection.isBatchVersionedData());
		jpaProperties.put("hibernate.id.new_generator_mappings", jpaConnection.isNewGeneratorMappings());
		jpaProperties.put("hibernate.jdbc.lob.non_contextual_creation",
				Objects.isNull(jpaConnection.getLobNonCotextualCreation()) ? Boolean.TRUE
						: jpaConnection.getLobNonCotextualCreation());
		if (jpaConnection.isMocked()) {
			log.warn(LoggerUtils.formatArchRow("No JPA configuration found. H2 in-memory DataBase pulled up"));
			jpaProperties.put("hibernate.hbm2ddl.auto", "none");
		} else {
			jpaProperties.put("hibernate.hbm2ddl.auto", jpaConnection.getHbm2ddl());
		}

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(this.jpaDataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(this.jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(packagesToScan);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}

   /**
    * Jpa vendor adapter.
    *
    * @return the jpa vendor adapter
    */
   @Bean(
      name = {"H2JPAVendorAdapter"}
   )
   public JpaVendorAdapter jpaVendorAdapter() {
      ArchJPAConfigurationService.JPAConnection jpaConnection = this.configurations.retrieveJPAConnection((String)null);
      if (jpaConnection.isMocked()) {
         HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
         bean.setDatabase(Database.H2);
         bean.setGenerateDdl(true);
         return bean;
      } else {
         return new HibernateJpaVendorAdapter();
      }
   }

   /**
    * Transaction manager.
    *
    * @return the platform transaction manager
    * @throws SQLException the SQL exception
    */
   @Bean(
      name = {"transactionManager"}
   )
   @Primary
   public PlatformTransactionManager transactionManager() throws SQLException {
      return new JpaTransactionManager(this.entityManagerFactory().getObject());
   }
}

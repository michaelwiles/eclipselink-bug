package model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.jolbox.bonecp.BoneCPDataSource;

import org.eclipse.persistence.jpa.PersistenceProvider;

import javax.sql.DataSource;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "model")
@ComponentScan(basePackages = { "model" })
public class ApplicationContext {

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setJdbcUrl("jdbc:hsqldb:mem:aname");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setLogStatementsEnabled(true);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());

        entityManagerFactoryBean.setPersistenceProviderClass(PersistenceProvider.class);

        Properties jpaProperties = new Properties();

        jpaProperties.put("eclipselink.ddl-generation", "create-tables");
        jpaProperties.put("eclipselink.weaving", "static");
        jpaProperties.put("eclipselink.ddl-generation.output-mode", "database");
        jpaProperties.put("eclipselink.target-database", "HSQL");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
}

package model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.jolbox.bonecp.BoneCPDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.eclipse.persistence.jpa.PersistenceProvider;

import javax.sql.DataSource;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "model")
@ComponentScan(basePackages = { "model" })
public class ApplicationContext {

    @Bean(name = "dataSource")
    @Profile("hsqldb")
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setJdbcUrl("jdbc:hsqldb:mem:aname");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setLogStatementsEnabled(true);
        return dataSource;
    }

    @Profile("x")
    public DataSource MysqlDataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ashes?autoReconnect=true&amp;sessionVariables=storage_engine=InnoDB;");

        dataSource.setUsername("ashes");
        dataSource.setPassword("ashes");

        dataSource.setMaxConnectionsPerPartition(1);
        dataSource.setPartitionCount(1);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setLogStatementsEnabled(true);
        return dataSource;
    }

    /**
     * <bean id="mysqlHikariDataSource"
     * class="com.zaxxer.hikari.HikariDataSource"> <constructor-arg> <bean
     * class="com.zaxxer.hikari.HikariConfig"> <property name="driverClassName"
     * value="com.mysql.jdbc.Driver" /> <property name="username"
     * value="${db.username}" /> <property name="password"
     * value="${db.password}" /> <property name="jdbcUrl" value="${db.url}" />
     * <property name="maximumPoolSize" value="25" /> <property name="poolName"
     * value="${poolName}" /> <property name="transactionIsolation"
     * value="${transactionIsolationLevelString}" /> </bean> </constructor-arg>
     * </bean>
     */
    @Bean(name = "dataSource")
    @Profile("mysql")
    public DataSource HikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/ashes?autoReconnect=true&amp;sessionVariables=storage_engine=InnoDB;");

        config.setUsername("root");
        config.setPassword("*#idoJ0=-");

        config.setMaximumPoolSize(2);
        config.setMinimumIdle(1);

        return new HikariDataSource(config);
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
        //jpaProperties.put("eclipselink.target-database", "HSQL");
        jpaProperties.put("eclipselink.target-database", "MYSQL");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
}

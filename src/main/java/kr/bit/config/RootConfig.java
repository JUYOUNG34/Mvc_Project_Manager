package kr.bit.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


import javax.sql.DataSource;

@Import({AopConfig.class}) // 명시적으로 AOP 설정 포함
@Configuration
@EnableTransactionManagement(proxyTargetClass = true) // JDK 동적 프록시 사용
@MapperScan(basePackages = {"kr.bit.mapper"})
@PropertySource({"classpath:db.properties"})
@ComponentScan(basePackages = "kr.bit")
@ComponentScan(basePackages = {"kr.bit.service", "kr.bit.dao", "kr.bit.security","kr.bit.aspect", "kr.bit.customHandler"})
public class RootConfig implements TransactionManagementConfigurer {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));
        hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
        hikariConfig.setUsername(env.getProperty("jdbc.user"));
        hikariConfig.setPassword(env.getProperty("jdbc.password"));

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }

    @Bean
    public SqlSessionFactory sessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());

        // MyBatis 설정 추가
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class); // SQL 로그 출력
        sessionFactoryBean.setConfiguration(configuration);

        return sessionFactoryBean.getObject();
    }


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
}
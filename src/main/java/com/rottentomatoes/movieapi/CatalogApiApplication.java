package com.rottentomatoes.movieapi;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.rottentomatoes.movieapi.filter.ExpandJsonResponseFilter;

@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CatalogApiApplication extends SpringBootServletInitializer {

    @Autowired
    Environment env;

    // Share username/password config (so we don't have to setup one per db)
    public SqlSessionFactory flxSqlSessionFactory(String dbUrl) throws Exception {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(dbUrl);
        dataSource.setUsername(env.getProperty("datasource.primary.username"));
        dataSource.setPassword(env.getProperty("datasource.primary.password"));
        dataSource.setDriverClassName(env.getProperty("datasource.primary.driver-class-name"));
        dataSource.setDefaultReadOnly(true);

        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resourceResolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("classpath:mappers/**.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    // Shared DB
    @Bean
    public SqlSession sqlSession() throws Exception {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(env.getProperty("datasource.primary.url"));
        dataSource.setUsername(env.getProperty("datasource.primary.username"));
        dataSource.setPassword(env.getProperty("datasource.primary.password"));
        dataSource.setDriverClassName(env.getProperty("datasource.primary.driver-class-name"));
        dataSource.setDefaultReadOnly(true);

        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resourceResolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("classpath:mappers/**.xml"));

        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
        return sqlSession;
    }

    // Talk DB
    @Bean
    public SqlSession talkSession() throws Exception {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(env.getProperty("datasource.talk.url"));
        dataSource.setUsername(env.getProperty("datasource.primary.username"));
        dataSource.setPassword(env.getProperty("datasource.primary.password"));
        dataSource.setDriverClassName(env.getProperty("datasource.primary.driver-class-name"));
        dataSource.setDefaultReadOnly(true);

        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resourceResolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("classpath:mappers/dbtalk/**.xml"));

        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
        return sqlSession;
    }

    @Bean
    public FilterRegistrationBean ExpandJsonResponseFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ExpandJsonResponseFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ExpandJsonResponseFilter");
        registration.setOrder(1);
        return registration;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CatalogApiApplication.class, args);
    }
}

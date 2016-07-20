package com.rottentomatoes.movieapi;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.rottentomatoes.movieapi.filter.ExpandJsonResponseFilter;

@SpringBootApplication
public class CatalogApiApplication extends SpringBootServletInitializer {

    @Bean
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource dataSource() {
        BasicDataSource dataSource = (BasicDataSource) DataSourceBuilder.create()
                .type(org.apache.commons.dbcp2.BasicDataSource.class)
                .build();
        dataSource.setInitialSize(2);
        dataSource.setDefaultReadOnly(true);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfigLocation(resourceResolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("classpath:mappers/**.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSession sqlSession() throws Exception {
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory());
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

package com.rottentomatoes.movieapi;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class CatalogApiApplication extends SpringBootServletInitializer {

    /* I can't make ApplicationConfiguration.java work.
     * If you can figure out how to make properties inject, by all means move the beans there -p */
    @Bean
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
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


    public static void main(String[] args) throws Exception {
        SpringApplication.run(CatalogApiApplication.class, args);
    }
}

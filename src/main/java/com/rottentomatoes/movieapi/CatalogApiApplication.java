package com.rottentomatoes.movieapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rottentomatoes.movieapi.filter.ExpandJsonResponseFilter;

@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CatalogApiApplication extends SpringBootServletInitializer {

    @Autowired
    Environment env;

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

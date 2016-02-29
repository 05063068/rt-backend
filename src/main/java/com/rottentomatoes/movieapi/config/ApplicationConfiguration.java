package com.rottentomatoes.movieapi.config;

import javax.servlet.Filter;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rottentomatoes.movieapi.filter.CatalogApiKatharsisFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationConfiguration {

    @Bean
    public Filter catalogApiKatharsisFilter() {
        CatalogApiKatharsisFilter filter = new CatalogApiKatharsisFilter();
        return filter;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/movie/**");
            }
        };
    }

	@Bean
	public MapperScannerConfigurer mapperScanner(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.rottentomatoes.movieapi.mappers");
		return mapperScannerConfigurer;
	}
	

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setLocation(new ClassPathResource("application.properties"));
        pspc.setIgnoreUnresolvablePlaceholders(false);

		return pspc;	
	}

} 
package com.rottentomatoes.movieapi;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.rottentomatoes.movieapi.filter.CatalogApiKatharsisFilter;

/**
 * Declares Katharsis Filter as a Bean in this configuration class.
 */
@Configuration
@ImportResource("classpath:applicationContext.xml")
public class WebConfig {

    @Bean
    public Filter catalogApiKatharsisFilter() {
        CatalogApiKatharsisFilter filter = new CatalogApiKatharsisFilter();
        return filter;
    }

}

package com.rottentomatoes.movieapi.domain.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Console;

@Component
public class EmsConfig {

    @Autowired
    @Getter
    @Setter
    Environment env;
    
    protected String EMS_BASE_URL = null; // "http://ems-staging.aws.prod.flixster.com/";

    public String getHostUrl() {
        if (EMS_BASE_URL == null) {
            EMS_BASE_URL = env.getProperty("datasource.ems.url");
        }
        return EMS_BASE_URL;
    }
    
    public void log(String s, Throwable e) {
        String exceptionMessage = e.getMessage();
        if (exceptionMessage == null) {
            exceptionMessage = s;
        }
        Console con = System.console();
        if (con != null) {
           con.printf("Ems error: %1s", exceptionMessage);
        }
    }
}

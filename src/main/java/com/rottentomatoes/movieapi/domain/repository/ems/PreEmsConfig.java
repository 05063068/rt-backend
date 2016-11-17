package com.rottentomatoes.movieapi.domain.repository.ems;

import java.io.Console;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PreEmsConfig {

    @Autowired
    @Getter
    @Setter
    Environment env;
    
    protected String PRE_EMS_BASE_URL = null; //"http://flx-pre-ems-v01.us-west-2.elasticbeanstalk.com"; //"http://pre-ems.aws.prod.flixster.com";

    public String getHostUrl() {
        if (PRE_EMS_BASE_URL == null) {
            PRE_EMS_BASE_URL = env.getProperty("datasource.pre-ems.url");
        }
        return PRE_EMS_BASE_URL;
    }
    
    public void log(String s, Throwable e) {
        String exceptionMessage = e.getMessage();
        if (exceptionMessage == null) {
            exceptionMessage = s;
        }
        Console con = System.console();
        if (con != null) {
           con.printf("Pre ems error: %1s", exceptionMessage);
        }
    }
}

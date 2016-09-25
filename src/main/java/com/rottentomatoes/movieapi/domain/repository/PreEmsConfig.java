package com.rottentomatoes.movieapi.domain.repository;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.sun.media.jfxmedia.logging.Logger;

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
        System.console().printf("Pre ems error: %1s", e.getMessage());
    }
}

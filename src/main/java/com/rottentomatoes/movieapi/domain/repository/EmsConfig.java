package com.rottentomatoes.movieapi.domain.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EmsConfig {

    @Autowired
    @Getter
    @Setter
    Environment env;

    private static final String PRE_EMS_DATASOURCE_PROPERTY = "datasource.pre-ems.url";
    private static final String TV_EMS_DATASOURCE_PROPERTY = "datasource.tv-ems.url";
    
    protected static final String EMS_BASE_URL = null; // "http://ems-staging.aws.prod.flixster.com/"; //"http://pre-ems.aws.prod.flixster.com"
    protected String tvEmsHost;
    protected String preEmsHost;

    public String getHost(String property) {
        if (property.equals(PRE_EMS_DATASOURCE_PROPERTY)) {
            if (preEmsHost == null) {
                preEmsHost = env.getProperty(PRE_EMS_DATASOURCE_PROPERTY);
            }
            return preEmsHost;
        } else if (property.equals(TV_EMS_DATASOURCE_PROPERTY)) {
            if (tvEmsHost == null) {
                tvEmsHost = env.getProperty(TV_EMS_DATASOURCE_PROPERTY);
            }
            return tvEmsHost;
        }
        return "";
    }

    public void log(String s) {
        Console con = System.console();
        if (con != null) {
            con.printf(s);
        }
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

    // Temporary list until we can get the endpoint routes fed to us from the backend
    private static final List<String> TV_EMS_PATHS = Arrays.asList("tv/episode", "tv/season", "tv/series", "franchise");

    public EmsClient fetchEmsClientForEndpoint(String basePath) {
        if (TV_EMS_PATHS.contains(basePath)) {
            return new TvEmsClient(this, getHost(TV_EMS_DATASOURCE_PROPERTY));
        }
        return new PreEmsClient(this, getHost(PRE_EMS_DATASOURCE_PROPERTY));
    }
}

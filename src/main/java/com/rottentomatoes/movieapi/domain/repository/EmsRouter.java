package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.ems.PreEmsClient;
import com.rottentomatoes.movieapi.domain.ems.TvEmsClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

@Component
public class EmsRouter {

    @Autowired
    @Getter
    @Setter
    Environment env;

    private static final String PRE_EMS_DATASOURCE_PROPERTY = "datasource.pre-ems.url";
    private static final String TV_EMS_DATASOURCE_PROPERTY = "datasource.tv-ems.url";

    // Temporary list until we can get the endpoint routes fed to us from the backend
    private static final List<String> TV_EMS_REPOSITORIES = Arrays.asList(
            "TvVanityTokenRepository",
            // TV Episodes
            "TvEpisodeRepository",
            "TvEpisodeToReviewInfoRepository",
            "TvEpisodeToImageRepository",
            "TvEpisodeToVideoClipRepository",
            "TvEpisodeToPersonnelRepository",
            "TvEpisodeToTvSeasonRepository",
            "TvEpisodeToTvSeriesRepository",
            "TvEpisodeToFranchiseRepository",
            // TV Seasons
            "TvSeasonRepository",
            "TvSeasonToReviewInfoRepository",
            "TvSeasonToImageRepository",
            "TvSeasonToVideoClipRepository",
            "TvSeasonToPersonnelRepository",
            "TvSeasonToTvEpisodeRepository",
            "TvSeasonToTvSeriesRepository",
            "TvSeasonToFranchiseRepository",
            // TV Series
            "TvSeriesRepository",
            "TvSeriesToFranchiseRepository",
            // Franchise
            "FranchiseRepository");
    private static final List<String> TV_EMS_PATHS = Arrays.asList(
            // Top Lists
            "top-for-year",
            "top-for-theater",
            "top-for-dvd",
            "top-for-genre",
            "top-ever");

    protected String tvEmsHost;
    protected String preEmsHost;

    public EmsClient fetchEmsClientForPath(String path) {
        if (TV_EMS_PATHS.contains(path)) {
            return new TvEmsClient(this, getHost(TV_EMS_DATASOURCE_PROPERTY));
        }
        return new PreEmsClient(this, getHost(PRE_EMS_DATASOURCE_PROPERTY));
    }

    public EmsClient fetchEmsClientForEndpoint(Class repository) {
        if (TV_EMS_REPOSITORIES.contains(repository.getSimpleName())) {
            return new TvEmsClient(this, getHost(TV_EMS_DATASOURCE_PROPERTY));
        }
        return new PreEmsClient(this, getHost(PRE_EMS_DATASOURCE_PROPERTY));
    }

    private String getHost(String property) {
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
}

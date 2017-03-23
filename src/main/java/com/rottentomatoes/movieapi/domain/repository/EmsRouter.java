package com.rottentomatoes.movieapi.domain.repository;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.clients.ems.PreEmsClient;
import com.rottentomatoes.movieapi.domain.clients.ems.TvEmsClient;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that handles routing to different service endpoints
 * 
 * Deprecated - use {@link AbstractApiCallDelegator} to route service API calls
 */
@Component
@Deprecated
@SuppressWarnings("rawtypes")
public class EmsRouter {

    @Autowired
    @Getter
    @Setter
    Environment env;

    private static final String PRE_EMS_DATASOURCE_PROPERTY = "datasource.pre-ems.url";
    private static final String TV_EMS_DATASOURCE_PROPERTY = "datasource.tv-ems.url";
    private static final String TV_EMS_AUTH_HEADER = "datasource.tv-ems.auth";

    // Temporary list until we can get the endpoint routes fed to us from the backend
    private static final List<String> TV_EMS_REPOSITORIES = Arrays.asList(
            "MovieToFranchiseRepository",
            "PersonToTvFilmographyItemRepository",
            // Vanity
            "FranchiseVanityTokenRepository",
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
            "TvSeasonToAudienceReviewsRepository",
            "TvSeasonToTvSeriesRepository",
            "TvSeasonToFranchiseRepository",
            "TvSeasonToTopTomatometerRepository",
            // TV Series
            "TvSeriesRepository",
            "TvSeriesToFranchiseRepository",
            "TvSeriesToTvSeasonRepository",
            "TvSeriesToTvEpisodeRepository",
            "TvSeriesToImageRepository",
            "TvSeriesToVideoClipRepository",
            "TvSeriesToPersonnelRepository",
            "TvSeriesToTopTomatometerRepository",
            // Franchise
            "FranchiseRepository",
            "FranchiseToMovieRepository",
            "FranchiseToTvSeriesRepository",
            "FranchiseToImageRepository",
            "FranchiseToVideoClipRepository");
    private static final List<String> TV_EMS_PATHS = Arrays.asList(
            "critic/tvReviews",
            "publication/tvReviews",
            // Top Lists
            "top-for-year",
            "top-for-theater",
            "top-for-dvd",
            "top-for-genre",
            "top-ever");

    public EmsClient fetchEmsClientForPath(String path) {
        if (TV_EMS_PATHS.contains(path)) {
            return new TvEmsClient(this, env.getProperty(TV_EMS_DATASOURCE_PROPERTY), env.getProperty(TV_EMS_AUTH_HEADER));
        }
        return new PreEmsClient(this, env.getProperty(PRE_EMS_DATASOURCE_PROPERTY));
    }

    public EmsClient fetchEmsClientForEndpoint(Class repository) {
        if (TV_EMS_REPOSITORIES.contains(repository.getSimpleName())) {
            return new TvEmsClient(this, env.getProperty(TV_EMS_DATASOURCE_PROPERTY), env.getProperty(TV_EMS_AUTH_HEADER));
        }
        return new PreEmsClient(this, env.getProperty(PRE_EMS_DATASOURCE_PROPERTY));
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

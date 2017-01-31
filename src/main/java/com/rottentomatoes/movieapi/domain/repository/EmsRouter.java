package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.ems.PerryEmsClient;
import com.rottentomatoes.movieapi.domain.ems.PreEmsClient;
import com.rottentomatoes.movieapi.domain.ems.TvEmsClient;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieListToMovieRepository;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieRepository;

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
    private static final String PERRY_EMS_DATASOURCE_PROPERTY = "datasource.perry-ems.url";
    private static final String TV_EMS_AUTH_HEADER = "datasource.tv-ems.auth";
    private static final String PERRY_EMS_AUTH_HEADER = "datasource.perry-ems.auth";

    // Temporary list until we can get the endpoint routes fed to us from the backend
    private static final List<String> PERRY_EMS_REPOSITORIES = Arrays.asList(
            // TODO: remove comments when perry ems production ready
            //"MovieToAffiliatesRepository",
            //"AffiliateRepository"
             );
    // Temporary list until we can get the endpoint routes fed to us from the backend
    private static final List<String> TV_EMS_REPOSITORIES = Arrays.asList(
            "PersonToTvFilmographyItemRepository",
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
            "publication/tvReviews",
            // Top Lists
            "top-for-year",
            "top-for-theater",
            "top-for-dvd",
            "top-for-genre",
            "top-ever");
    private static final List<String> PERRY_EMS_PATHS = Arrays.asList(
            "all-box-office");

    protected String tvEmsHost;
    protected String preEmsHost;

    public EmsClient fetchEmsClientForPath(String path) {
        if (TV_EMS_PATHS.contains(path)) {
            return new TvEmsClient(this, env.getProperty(TV_EMS_DATASOURCE_PROPERTY), env.getProperty(TV_EMS_AUTH_HEADER));
        } else if (PERRY_EMS_PATHS.contains(path)) {
            return new PerryEmsClient(this, env.getProperty(PERRY_EMS_DATASOURCE_PROPERTY), MovieListToMovieRepository.class, env.getProperty(PERRY_EMS_AUTH_HEADER));            
        }
        return new PreEmsClient(this, env.getProperty(PRE_EMS_DATASOURCE_PROPERTY));
    }

    public EmsClient fetchEmsClientForEndpoint(Class repository) {
        if (PERRY_EMS_REPOSITORIES.contains(repository.getSimpleName())) {
            return new PerryEmsClient(this, env.getProperty(PERRY_EMS_DATASOURCE_PROPERTY), repository, env.getProperty(PERRY_EMS_AUTH_HEADER));
        }
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

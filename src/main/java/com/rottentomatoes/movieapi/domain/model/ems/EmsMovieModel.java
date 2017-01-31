package com.rottentomatoes.movieapi.domain.model.ems;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieBoxOfficeInfo;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmsMovieModel extends EmsModel<Movie, EmsMovieModel> {
    protected Integer rank;
    protected Integer lastRank;
    protected Integer totalGross;
    protected String title;
    protected Integer weekendGross;
    protected Integer screens;
    protected Integer weekNumber;
    protected Integer openingDays;
    protected EmsMovieFeaturesModel movieFeatures;
    
    @Override
    public Movie convert(EmsMovieModel emsMovieModel) {
        Movie movie = new Movie();
        movie.setId(emsMovieModel.movieFeatures.rtInfo.movieId);
        movie.setTitle(emsMovieModel.title);
        movie.setYear(emsMovieModel.movieFeatures.rtInfo.releaseYear);
        movie.setVanity(emsMovieModel.movieFeatures.rtInfo.vanityUrl);
        if (emsMovieModel.movieFeatures.tomatometer != null &&
                emsMovieModel.movieFeatures.tomatometer.numReviews != null &&
                emsMovieModel.movieFeatures.tomatometer.numReviews >= 5) {
            String tomatoMeterState = "rotten";
            if (movieFeatures.tomatometer.certifiedFresh) {
                tomatoMeterState = "certified_fresh";
            } else if (movieFeatures.tomatometer.tomatometer >= 60) {
                tomatoMeterState = "fresh";
            }
            movie.set("value", movieFeatures.tomatometer.tomatometer.toString());
            movie.set("state", tomatoMeterState);
        }
        // need to add box office data
        MovieBoxOfficeInfo movieBoxOfficeInfo = new MovieBoxOfficeInfo();
        movie.setMovieBoxOfficeInfo(movieBoxOfficeInfo);
        movieBoxOfficeInfo.setId(emsMovieModel.movieFeatures.rtInfo.movieId);
        movieBoxOfficeInfo.setRank(emsMovieModel.rank);
        movieBoxOfficeInfo.setTotalGross(emsMovieModel.totalGross);
        movieBoxOfficeInfo.setWeekendGross(emsMovieModel.weekendGross);
        movieBoxOfficeInfo.setScreens(emsMovieModel.screens);
        movieBoxOfficeInfo.setWeekNumber(emsMovieModel.weekNumber);
        movieBoxOfficeInfo.setOpeningDays(emsMovieModel.openingDays);
        movieBoxOfficeInfo.setLastRank(emsMovieModel.lastRank);
        Integer theaterAverage = null;
        if (emsMovieModel.screens != null && emsMovieModel.screens > 0 && emsMovieModel.weekendGross != null) {
            theaterAverage = emsMovieModel.weekendGross / emsMovieModel.screens;
        }
        movieBoxOfficeInfo.setTheaterAverage(theaterAverage);
        return movie;
    }
}

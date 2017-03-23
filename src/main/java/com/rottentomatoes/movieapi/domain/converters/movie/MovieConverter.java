package com.rottentomatoes.movieapi.domain.converters.movie;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieBoxOfficeInfo;
import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.responses.ems.MovieResponse;
import com.rottentomatoes.movieapi.domain.responses.ems.MpaaRatingResponse;
import com.rottentomatoes.movieapi.domain.responses.ems.RtInfoResponse;
import com.rottentomatoes.movieapi.domain.responses.ems.TomatometerResponse;

public class MovieConverter implements AbstractConverter<Movie> {

    private static final String TOMATOMETER_STATE_CERTIFIED_FRESH = "certified_fresh";
    private static final String TOMATOMETER_STATE_FRESH = "fresh";
    private static final String TOMATOMETER_STATE_ROTTEN = "rotten";
    private static final String TOMATOMETER_STATE_NA = "NA";

    private static final String TOMATOMETER_STATE = "state";
    private static final String TOMATOMETER_VALUE = "value";

    private MovieResponse response;

    public MovieConverter(MovieResponse response) {
        this.response = response;
    }

    public Movie convert() {
        Movie movie = new Movie();

        movie.setTitle(response.getTitle());

        // Set ID
        final Integer rtId = response.getRtId();
        if (rtId != null) {
            movie.setId(rtId.toString());
        }

        // Set RT Info
        final RtInfoResponse rtInfo = response.getRtInfo();
        if (rtInfo != null) {
            movie.setYear(rtInfo.getReleaseYear());
            movie.setVanity(rtInfo.getVanityUrl());
        }

        // Set TomatometerResponse
        final TomatometerResponse tomatometer = response.getTomatometer();
        if (tomatometer != null && tomatometer.getTomatometer() != null
                && tomatometer.getNumReviews() != null && tomatometer.getNumReviews() >= 5) {
            movie.set(TOMATOMETER_STATE, getTomatometerState(tomatometer));
            movie.set(TOMATOMETER_VALUE, tomatometer.getTomatometer().toString());
        } else {
            movie.set(TOMATOMETER_STATE, TOMATOMETER_STATE_NA);
            movie.set(TOMATOMETER_VALUE, "0");
        }

        // Set MPAA
        final MpaaRatingResponse mpaaRating = response.getMpaaRating();
        movie.setMpaaRating(com.rottentomatoes.movieapi.enums.MpaaRating.NR);
        if (mpaaRating != null) {
            if (mpaaRating.getCode() != null) {
                movie.setMpaaRating(com.rottentomatoes.movieapi.enums.MpaaRating
                        .getMpaaRating(mpaaRating.getCode()));
            }
            movie.setAdvisory(mpaaRating.getWarning());
        }

        // Set Box OFfice data
        final MovieBoxOfficeInfo movieBoxOfficeInfo = new MovieBoxOfficeInfo();
        final Integer weekendGross = response.getWeekendGross();
        final Integer screens = response.getScreens();
        if (rtId != null) {
            movieBoxOfficeInfo.setId(rtId.toString());
        }
        movieBoxOfficeInfo.setRank(response.getRank());
        movieBoxOfficeInfo.setTotalGross(response.getTotalGross());
        movieBoxOfficeInfo.setWeekendGross(weekendGross);
        movieBoxOfficeInfo.setScreens(screens);
        movieBoxOfficeInfo.setWeekNumber(response.getWeekNumber());
        movieBoxOfficeInfo.setOpeningDays(response.getOpeningDays());
        movieBoxOfficeInfo.setLastRank(response.getLastRank());
        if (weekendGross != null && screens != null && screens > 0) {
            movieBoxOfficeInfo.setTheaterAverage(weekendGross / screens);
        }
        movie.setMovieBoxOfficeInfo(movieBoxOfficeInfo);

        return movie;
    }

    private static String getTomatometerState(TomatometerResponse tomatometer) {
        if (tomatometer.certifiedFresh) {
            return TOMATOMETER_STATE_CERTIFIED_FRESH;
        } else if (tomatometer.tomatometer >= 60) {
            return TOMATOMETER_STATE_FRESH;
        } else {
            return TOMATOMETER_STATE_ROTTEN;
        }
    }
}

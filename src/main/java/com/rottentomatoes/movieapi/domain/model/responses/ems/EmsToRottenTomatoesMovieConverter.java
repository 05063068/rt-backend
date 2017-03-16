/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.responses.ems;

import java.util.ArrayList;
import java.util.List;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieBoxOfficeInfo;

/**
 * This class is responsible for converting a list of {@link EmsMovie} to a list  Rotten Tomatoes {@link Movie}
 *
 * @author harry
 */
public class EmsToRottenTomatoesMovieConverter {
    private static final String TOMATOMETER_STATE_CERTIFIED_FRESH = "certified_fresh";
    private static final String TOMATOMETER_STATE_FRESH = "fresh";
    private static final String TOMATOMETER_STATE_ROTTEN = "rotten";
    private static final String TOMATOMETER_STATE_NA = "NA";

    private static final String TOMATOMETER_STATE = "state";
    private static final String TOMATOMETER_VALUE = "value";

    public static List<Movie> prepareMovies(List<EmsMovie> emsMovies) {
        final List<Movie> movies = new ArrayList<>();

        for (final EmsMovie emsMovie : emsMovies) {
            Movie movie = new Movie();

            movie.setTitle(emsMovie.getTitle());

            // Set ID
            final Integer rtId = emsMovie.getRtId();
            if (rtId != null) {
                movie.setId(rtId.toString());
            }

            // Set RT Info
            final RtInfo rtInfo = emsMovie.getRtInfo();
            if (rtInfo != null) {
                movie.setYear(rtInfo.getReleaseYear());
                movie.setVanity(rtInfo.getVanityUrl());
            }

            // Set Tomatometer
            final Tomatometer tomatometer = emsMovie.getTomatometer();
            if (tomatometer != null && tomatometer.getTomatometer() != null
                    && tomatometer.getNumReviews() != null && tomatometer.getNumReviews() >= 5) {
                movie.set(TOMATOMETER_STATE, getTomatometerState(tomatometer));
                movie.set(TOMATOMETER_VALUE, tomatometer.getTomatometer().toString());
            } else {
                movie.set(TOMATOMETER_STATE, TOMATOMETER_STATE_NA);
                movie.set(TOMATOMETER_VALUE, "0");
            }

            // Set MPAA
            final MpaaRating mpaaRating = emsMovie.getMpaaRating();
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
            final Integer weekendGross = emsMovie.getWeekendGross();
            final Integer screens = emsMovie.getScreens();
            if (rtId != null) {
                movieBoxOfficeInfo.setId(rtId.toString());
            }
            movieBoxOfficeInfo.setRank(emsMovie.getRank());
            movieBoxOfficeInfo.setTotalGross(emsMovie.getTotalGross());
            movieBoxOfficeInfo.setWeekendGross(weekendGross);
            movieBoxOfficeInfo.setScreens(screens);
            movieBoxOfficeInfo.setWeekNumber(emsMovie.getWeekNumber());
            movieBoxOfficeInfo.setOpeningDays(emsMovie.getOpeningDays());
            movieBoxOfficeInfo.setLastRank(emsMovie.getLastRank());
            if (weekendGross != null && screens != null && screens > 0) {
                movieBoxOfficeInfo.setTheaterAverage(weekendGross / screens);
            }
            movie.setMovieBoxOfficeInfo(movieBoxOfficeInfo);

            movies.add(movie);
        }

        return movies;
    }

    private static String getTomatometerState(Tomatometer tomatometer) {
        if (tomatometer.certifiedFresh) {
            return TOMATOMETER_STATE_CERTIFIED_FRESH;
        } else if (tomatometer.tomatometer >= 60) {
            return TOMATOMETER_STATE_FRESH;
        } else {
            return TOMATOMETER_STATE_ROTTEN;
        }
    }
}

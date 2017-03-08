/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.apicalldelegators.ems;

import java.util.List;

import org.springframework.core.env.Environment;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.model.clients.Client;
import com.rottentomatoes.movieapi.domain.model.requests.ems.EmsBoxOfficeRequest;
import com.rottentomatoes.movieapi.domain.model.responses.ems.EmsBoxOfficeResponse;
import com.rottentomatoes.movieapi.domain.model.responses.ems.EmsToRottenTomatoesMovieConverter;

import io.katharsis.queryParams.RequestParams;

/**
 * API implementation for the {@link com.rottentomatoes.movieapi.domain.repository.movie.MovieListToMovieRepository}
 * 'all-box-office' endpoint
 * 
 * @author harry
 */
public class MovieListToMovieAllBoxOfficeApiCall extends AbstractApiCall {

    public MovieListToMovieAllBoxOfficeApiCall(final Environment environment,
            final String fieldName, final RequestParams requestParams) {
        super(environment, fieldName, requestParams);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movie> process() {
        EmsBoxOfficeRequest request = new EmsBoxOfficeRequest(environment, fieldName, requestParams);
        EmsBoxOfficeResponse emsBoxOfficeResponse = new EmsBoxOfficeResponse(
                Client.makeApiCall(request));
        return EmsToRottenTomatoesMovieConverter.prepareMovies(emsBoxOfficeResponse.getEmsMovies());
    }
}

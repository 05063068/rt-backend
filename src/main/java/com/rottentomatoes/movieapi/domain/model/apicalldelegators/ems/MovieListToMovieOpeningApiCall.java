/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.apicalldelegators.ems;

import java.util.List;

import org.springframework.core.env.Environment;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.model.clients.Client;
import com.rottentomatoes.movieapi.domain.model.requests.ems.EmsOpeningMoviesRequest;
import com.rottentomatoes.movieapi.domain.model.responses.ems.EmsMovieListResponse;
import com.rottentomatoes.movieapi.domain.model.responses.ems.EmsToRottenTomatoesMovieConverter;

import io.katharsis.queryParams.RequestParams;

/**
 * API implementation for the {@link com.rottentomatoes.movieapi.domain.repository.movie.MovieListToMovieRepository}
 * 'opening' endpoint
 * 
 * @author harry
 */
public class MovieListToMovieOpeningApiCall extends AbstractApiCall {

    public MovieListToMovieOpeningApiCall(final Environment environment, final String fieldName,
            final RequestParams requestParams) {
        super(environment, fieldName, requestParams);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movie> process() {
        EmsOpeningMoviesRequest request = new EmsOpeningMoviesRequest(environment, fieldName,
                requestParams);
        EmsMovieListResponse response = new EmsMovieListResponse(Client.makeApiCall(request));
        return EmsToRottenTomatoesMovieConverter.prepareMovies(response.getEmsMovies());
    }

}

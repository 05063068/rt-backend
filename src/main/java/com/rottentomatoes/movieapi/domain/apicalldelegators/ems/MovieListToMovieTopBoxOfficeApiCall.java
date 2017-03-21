/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.apicalldelegators.ems;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.converters.ListConverter;
import com.rottentomatoes.movieapi.domain.converters.ems.MovieResponseToMovieConverter;
import com.rottentomatoes.movieapi.domain.responses.ems.MovieResponse;
import com.rottentomatoes.movieapi.utils.JsonUtilities;
import org.springframework.core.env.Environment;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.requests.ems.EmsBoxOfficeRequest;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieListToMovieRepository;

import io.katharsis.queryParams.RequestParams;

/**
 * API implementation for the {@link MovieListToMovieRepository} 'top-box-office' endpoint
 * 
 * @author harry
 */
public class MovieListToMovieTopBoxOfficeApiCall extends AbstractApiCall {

    public MovieListToMovieTopBoxOfficeApiCall(final Environment environment, final String fieldName, final RequestParams requestParams) {
        super(environment, fieldName, requestParams);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movie> process() {
        EmsBoxOfficeRequest request = new EmsBoxOfficeRequest(environment, fieldName, requestParams);
        List<MovieResponse> response = JsonUtilities.deserialize(Client.makeApiCall(request), new TypeReference<List<MovieResponse>>() {});
        return ListConverter.convert(response, new MovieResponseToMovieConverter());
    }
}

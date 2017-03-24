/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.apicalldelegators.review;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.converters.review.AudienceReviewListConverter;
import com.rottentomatoes.movieapi.domain.responses.urating.UserRatingResponse;
import com.rottentomatoes.movieapi.utils.JsonUtilities;
import org.springframework.core.env.Environment;

import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.requests.urating.UserRatingsTopRatingsRequest;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieToAudienceReviewRepository;

import io.katharsis.queryParams.RequestParams;

/**
 * API implementation for the {@link MovieToAudienceReviewRepository} endpoint
 * 
 * @author harry
 */
public class MovieToAudienceReviewApiCall extends AbstractApiCall {

    private String movieId;

    public MovieToAudienceReviewApiCall(final Environment environment, final String fieldName,
            final RequestParams requestParams, final String movieId) {
        super(environment, fieldName, requestParams);
        this.movieId = movieId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AudienceReview> process() {
        UserRatingsTopRatingsRequest request = new UserRatingsTopRatingsRequest(environment, movieId);
        List<UserRatingResponse> response = JsonUtilities.deserialize(Client.makeApiCall(request),
                new TypeReference<List<UserRatingResponse>>() {});
        AudienceReviewListConverter converter =
                new AudienceReviewListConverter(response);
        return converter.convert();
    }

}

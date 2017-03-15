/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.apicalldelegators;

import java.util.List;

import org.springframework.core.env.Environment;

import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.model.clients.Client;
import com.rottentomatoes.movieapi.domain.model.requests.urating.UserRatingsTopRatingsRequest;
import com.rottentomatoes.movieapi.domain.model.responses.urating.UserRatingToRottenTomatoesAudienceReviewConverter;
import com.rottentomatoes.movieapi.domain.model.responses.urating.UserRatingsListResponse;
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
        UserRatingsTopRatingsRequest request = new UserRatingsTopRatingsRequest(environment,
                movieId);
        UserRatingsListResponse userRatingsListResponse = new UserRatingsListResponse(
                Client.makeApiCall(request));
        return UserRatingToRottenTomatoesAudienceReviewConverter
                .prepareAudienceReviews(userRatingsListResponse.getUserRatings());
    }

}

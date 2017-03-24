/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.apicalldelegators.review;

import java.util.List;

import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.converters.review.TvAudienceReviewListConverter;
import com.rottentomatoes.movieapi.domain.model.TvAudienceReview;
import com.rottentomatoes.movieapi.domain.repository.tvseason.TvSeasonToAudienceReviewsRepository;
import com.rottentomatoes.movieapi.domain.requests.urating.TvSeasonTopRatingsRequest;
import com.rottentomatoes.movieapi.domain.responses.urating.TvSeasonUserRatingResponse;
import com.rottentomatoes.movieapi.utils.JsonUtilities;

import io.katharsis.queryParams.RequestParams;

/**
 * API implementation for the {@link TvSeasonToAudienceReviewsRepository} endpoint
 * 
 * @author harry
 */
public class TvSeasonToTvAudienceReviewApiCall extends AbstractApiCall {

    private String tvSeasonId;

    public TvSeasonToTvAudienceReviewApiCall(final Environment environment, final String fieldName,
            final RequestParams requestParams, final String tvSeasonId) {
        super(environment, fieldName, requestParams);
        this.tvSeasonId = tvSeasonId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TvAudienceReview> process() {
        TvSeasonTopRatingsRequest request = new TvSeasonTopRatingsRequest(environment,
                requestParams, tvSeasonId);
        List<TvSeasonUserRatingResponse> response = JsonUtilities.deserialize(
                Client.makeApiCall(request),
                new TypeReference<List<TvSeasonUserRatingResponse>>() {});
        TvAudienceReviewListConverter converter = new TvAudienceReviewListConverter(response);
        return converter.convert();
    }

}

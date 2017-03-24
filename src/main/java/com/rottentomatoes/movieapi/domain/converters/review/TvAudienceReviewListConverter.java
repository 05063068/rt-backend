/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.converters.review;

import java.util.ArrayList;
import java.util.List;

import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.TvAudienceReview;
import com.rottentomatoes.movieapi.domain.responses.urating.TvSeasonUserRatingResponse;

public class TvAudienceReviewListConverter implements AbstractConverter<List<TvAudienceReview>> {

    List<TvSeasonUserRatingResponse> tvSeasonUserRatingResponseList;

    public TvAudienceReviewListConverter(
            List<TvSeasonUserRatingResponse> tvSeasonUserRatingResponseList) {
        this.tvSeasonUserRatingResponseList = tvSeasonUserRatingResponseList;
    }

    @Override
    public List<TvAudienceReview> convert() {
        final List<TvAudienceReview> audienceReviews = new ArrayList<>();

        for (final TvSeasonUserRatingResponse tvSeasonUserRatingResponse : tvSeasonUserRatingResponseList) {
            audienceReviews
                    .add(new TvAudienceReviewConverter(tvSeasonUserRatingResponse).convert());
        }

        return audienceReviews;
    }
}

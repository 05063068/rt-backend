/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.converters.review;

import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.TvAudienceReview;
import com.rottentomatoes.movieapi.domain.responses.urating.TvSeasonUserRatingResponse;

public class TvAudienceReviewConverter implements AbstractConverter<TvAudienceReview> {
    
    TvSeasonUserRatingResponse tvSeasonUserRatingResponse;
    
    public TvAudienceReviewConverter(TvSeasonUserRatingResponse tvSeasonUserRatingResponse) {
        this.tvSeasonUserRatingResponse = tvSeasonUserRatingResponse;
    }

    @Override
    public TvAudienceReview convert() {
        TvAudienceReview audienceReview = new TvAudienceReview();

        audienceReview.setMediaId((int) tvSeasonUserRatingResponse.getSeasonId().longValue());
        audienceReview.setMediaType("SE");
        audienceReview.setUserId(tvSeasonUserRatingResponse.getUserId());
        audienceReview.setComment(tvSeasonUserRatingResponse.getComment());
        audienceReview.setCommentLength(prepareCommentLength(tvSeasonUserRatingResponse.getComment()));
        audienceReview.setScore(tvSeasonUserRatingResponse.getRating());
        audienceReview.setElite(tvSeasonUserRatingResponse.getIsSuperReviewer());
        audienceReview.setRatingDate(tvSeasonUserRatingResponse.getLastRatingDate());

        // TODO RTBE-773 Set dummy values for now until we get user data
        audienceReview.setFirstName("John");
        audienceReview.setLastName("Doe");
        audienceReview.setThumbnailUrl(null);

        return audienceReview;
    }

    private static Integer prepareCommentLength(String comment) {
        return comment == null ? 0 : comment.length();
    }
}

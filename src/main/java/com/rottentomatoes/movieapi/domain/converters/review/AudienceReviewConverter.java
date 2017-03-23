package com.rottentomatoes.movieapi.domain.converters.review;

import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.responses.urating.UserRatingResponse;

public class AudienceReviewConverter implements AbstractConverter<AudienceReview> {

    UserRatingResponse response;

    public AudienceReviewConverter(UserRatingResponse response) {
        this.response = response;
    }

    public AudienceReview convert() {
        AudienceReview audienceReview = new AudienceReview();
        audienceReview.setMovieId(response.getMovieId());
        audienceReview.setUserId(response.getUserId());
        audienceReview.setComment(response.getComment());
        audienceReview.setScore(response.getRating());
        audienceReview.setSuperReviewer(response.getIsSuperReviewer());
        audienceReview.setRatingDate(response.getLastRatingDate());

        // TODO RTBE-773 Set dummy values for now until we get user data
        audienceReview.setUserName("John Doe");
        audienceReview.setUserImage(null);

        return audienceReview;
    }
}

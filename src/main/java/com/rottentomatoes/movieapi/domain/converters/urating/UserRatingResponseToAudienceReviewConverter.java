package com.rottentomatoes.movieapi.domain.converters.urating;

import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.responses.urating.UserRatingResponse;

public class UserRatingResponseToAudienceReviewConverter extends AbstractConverter {

    public AudienceReview convert(UserRatingResponse response) {
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

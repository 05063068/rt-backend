/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.responses.urating;

import java.util.ArrayList;
import java.util.List;

import com.rottentomatoes.movieapi.domain.model.AudienceReview;

/**
 * This class is responsible for converting Ems {@link UserRatingsListResponse} to a List
 * of normalized Rotten Tomatoes {@link AudienceReview}
 *
 * @author harry
 */
public class UserRatingToRottenTomatoesAudienceReviewConverter {

    public static List<AudienceReview> prepareAudienceReviews(List<UserRating> userRatings) {
        final List<AudienceReview> audienceReviews = new ArrayList<>();

        for (final UserRating userRating : userRatings) {
            audienceReviews.add(prepareAudienceReview(userRating));
        }

        return audienceReviews;
    }

    public static AudienceReview prepareAudienceReview(UserRating userRating) {
        AudienceReview audienceReview = new AudienceReview();

        audienceReview.setMovieId(userRating.getMovieId());
        audienceReview.setUserId(userRating.getUserId());
        audienceReview.setComment(userRating.getComment());
        audienceReview.setScore(userRating.getRating());
        audienceReview.setSuperReviewer(userRating.getIsSuperReviewer());
        audienceReview.setRatingDate(userRating.getLastRatingDate());

        // TODO RTBE-773 Set dummy values for now until we get user data
        audienceReview.setUserName("John Doe");
        audienceReview.setUserImage(null);

        return audienceReview;
    }
}

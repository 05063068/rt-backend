package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.model.Review;

import java.util.List;

public class StringSanitizationUtils {

    public static void sanitizeAudienceReviews(List<AudienceReview> list) {
        if (list != null && list.size() > 0) {
            for (AudienceReview r : list) {
                AudienceReview.sanitizeCommentHtml(r);
            }
        }
    }
}

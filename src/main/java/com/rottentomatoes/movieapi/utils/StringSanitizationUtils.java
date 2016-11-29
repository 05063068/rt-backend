package com.rottentomatoes.movieapi.utils;

import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.model.TvAudienceReview;

import java.util.List;

public class StringSanitizationUtils {

    public static void sanitizeAudienceReviews(List<AudienceReview> list) {
        if (list != null && list.size() > 0) {
            for (AudienceReview r : list) {
                AudienceReview.sanitizeCommentHtml(r);
            }
        }
    }

    public static void sanitizeTvAudienceReviews(List<TvAudienceReview> list) {
        if (list != null && list.size() > 0) {
            for (TvAudienceReview r : list) {
                TvAudienceReview.sanitizeCommentHtml(r);
            }
        }
    }
}

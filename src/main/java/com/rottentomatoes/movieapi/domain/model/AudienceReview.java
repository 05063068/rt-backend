package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.time.ZonedDateTime;
import java.util.Map;

@JsonApiResource(type = "audienceReview")
@Getter
@Setter
public class AudienceReview extends AbstractModel {

    protected Long movieId;
    protected Long userId;
    protected String userName;
    protected Boolean superReviewer; // Should always be true.
    protected Double score;        // WTS's not supported for featured audience reviews. Normalize to 0.5-5.0 and remove faux-score craziness
    protected String ratingSource;
    protected String comment;   
    protected ZonedDateTime ratingDate;
    protected Map<String, Object> userImage;

    public static void sanitizeCommentHtml(AudienceReview r) {
        if (r != null && r.comment != null) {
            // remove all HTML tags
            r.comment = Jsoup.clean(r.comment, Whitelist.none());
        }
    }
}

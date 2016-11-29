package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonApiResource(type = "tvAudienceReview")
@Getter
@Setter
public class TvAudienceReview extends AbstractModel {
    
    public Long userId;
    public String firstName;
    public String lastName;
    public String thumbnailUrl;
    public Boolean elite;
    public String mediaType;
    public Integer mediaId;
    public Double score;
    public String comment;
    public Integer commentLength;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    public ZonedDateTime ratingDate;

    public static void sanitizeCommentHtml(TvAudienceReview r) {
        if (r != null && r.comment != null) {
            // remove all HTML tags
            r.comment = Jsoup.clean(r.comment, Whitelist.none());
        }
    }
}

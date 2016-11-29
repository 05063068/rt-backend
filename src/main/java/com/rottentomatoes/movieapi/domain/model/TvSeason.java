package com.rottentomatoes.movieapi.domain.model;

import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.katharsis.resource.annotations.*;
import io.katharsis.response.MetaDataEnabledList;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "tvSeason")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TvSeason extends AbstractModel {
    protected String title;
    protected String vanityUrl;

    protected String seasonNumber;
    protected String tvSeriesId;
    protected String network;
    protected String genre;
    protected String description;

    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime startDate;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime endDate;
    protected int startYear;
    protected int endYear;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;
    protected Map<String, Object> posterImage;
    protected Map<String, Object> heroImage;
    protected Map<String, Object> mainTrailer;
    protected Map<String, Object> tvRatingSummary;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvEpisode> tvEpisodes;
    
    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvAudienceReview> audienceReviews;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected TvSeries tvSeries;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Franchise franchise;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected ReviewInfo reviewInfo;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Personnel tvPersonnel;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Image> images;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MetaDataEnabledList<VideoClip> videoClips;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected TopTomatometer topTomatometer;
}

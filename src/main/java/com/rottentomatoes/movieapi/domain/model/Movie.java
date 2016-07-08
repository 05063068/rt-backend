package com.rottentomatoes.movieapi.domain.model;

import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rottentomatoes.movieapi.enums.MpaaRating;

import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;

import io.katharsis.response.MetaDataEnabledList;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movie")
@Getter
@Setter
public class Movie extends AbstractModel {

	// Atomic attributes
    protected String title;
    protected Integer year;

    protected MpaaRating mpaaRating;

    protected ZonedDateTime creationDate;
    protected ZonedDateTime lastModifiedDate;
    protected String advisory;
    protected String vanity;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;

    // relationships
    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MovieSupplementaryInfo movieSupplementaryInfo;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected CriticSummary criticSummary;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Franchise franchise;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Affiliate> affiliates;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected AudienceSummary audienceSummary;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Genre> genres;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<MovieCast> movieCast;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Review> reviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MetaDataEnabledList<AudienceReview> audienceReviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MetaDataEnabledList<VideoClip> videoClips;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Image> images;

}

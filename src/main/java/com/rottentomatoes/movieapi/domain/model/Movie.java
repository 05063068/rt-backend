package com.rottentomatoes.movieapi.domain.model;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime creationDate;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime lastModifiedDate;
    protected String advisory;
    protected String vanity;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;
    
    @JsonAnySetter
    public void set(String name, String value) {
        if ("state".equals(name) || "value".equals(name)) {
            if (tomatometer == null) {
                tomatometer = new HashMap<String, Object>();
            }
            if ("value".equals(name)) {
                tomatometer.put(name, Integer.parseInt(value));
            } else {
                tomatometer.put(name, value);
            }
        }
    }
    
    MovieBoxOfficeInfo movieBoxOfficeInfo;

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

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Personnel moviePersonnel;

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
    protected Iterable<Review> reviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<AudienceReview> audienceReviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<VideoClip> videoClips;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Image> images;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Quote> quotes;

}

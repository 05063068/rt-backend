package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.katharsis.resource.annotations.*;
import io.katharsis.response.MetaDataEnabledList;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

@JsonApiResource(type = "tvSeries")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TvSeries extends AbstractModel {
    protected String title;
    protected String vanityUrl;

    protected String startYear;
    protected String franchiseId;
    protected String network;
    protected String genre;
    protected String description;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime premiereDate;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;
    protected Map<String, Object> posterImage;
    protected Map<String, Object> heroImage;
    protected Map<String, Object> mainTrailer;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Franchise franchise;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvSeason> tvSeasons;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected TvEpisode firstEpisode;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected TvEpisode lastEpisode;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected TvEpisode nextEpisode;

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


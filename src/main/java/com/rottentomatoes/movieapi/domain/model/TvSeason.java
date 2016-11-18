package com.rottentomatoes.movieapi.domain.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    protected String startDate;
    protected String endDate;
    protected int startYear;
    protected int endYear;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;
    protected Map<String, Object> posterImage;
    protected Map<String, Object> heroImage;
    protected Map<String, Object> mainTrailer;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvEpisode> tvEpisodes;

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
}

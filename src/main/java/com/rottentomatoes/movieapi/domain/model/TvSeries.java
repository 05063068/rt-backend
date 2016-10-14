package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

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

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;
    protected Map<String, Object> posterImage;
    protected Map<String, Object> heroImage;
    protected Map<String, Object> mainTrailer;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvSeason> tvSeasons;
}

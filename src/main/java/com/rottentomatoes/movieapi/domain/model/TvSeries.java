package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonApiResource(type = "tvSeries")
@Getter
@Setter
public class TvSeries extends AbstractModel {
    protected String title;
    protected String description;
    protected String vanity;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvSeason> tvSeason;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    private Image image;

}

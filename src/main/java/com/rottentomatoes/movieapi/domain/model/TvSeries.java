package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonApiResource(type = "tvSeries")
@Getter
@Setter
public class TvSeries extends AbstractModel {
    protected String title;
    protected String description;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvSeason> tvSeason;

}

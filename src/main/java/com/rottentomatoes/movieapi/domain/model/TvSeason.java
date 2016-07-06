package com.rottentomatoes.movieapi.domain.model;

import java.util.Date;

import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "tvSeason")
@Getter
@Setter
public class TvSeason extends AbstractModel {
    private String name;
    private String seasonNumber;
    private String description;
    private String tvSeriesId;
    private Date startDate;
    private Date endDate;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    private Iterable<TvEpisode> tvEpisode;
}

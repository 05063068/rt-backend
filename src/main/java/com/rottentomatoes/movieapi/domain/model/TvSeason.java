package com.rottentomatoes.movieapi.domain.model;

import java.util.Date;
import java.util.Map;

import io.katharsis.resource.annotations.*;
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
    private String vanity;
    private String seriesTitle;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    private Iterable<TvEpisode> tvEpisode;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    private TvSeries tvSeries;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    private Image image;
}

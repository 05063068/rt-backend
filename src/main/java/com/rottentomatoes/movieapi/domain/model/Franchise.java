package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;
import io.katharsis.response.MetaDataEnabledList;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "franchise")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Franchise extends AbstractModel {

    protected String name;
    protected String vanity;
    protected String description;
    protected Image mainImage;
    protected Image bannerImage;
    protected Image heroImage;

    protected String topMovieId;
    protected String topTvSeriesId;

    protected TvSeries topTvSeries;
    protected Movie topMovie;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvSeries> tvSeries;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Movie> movies;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<VideoClip> videoClips;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Image> images;
}

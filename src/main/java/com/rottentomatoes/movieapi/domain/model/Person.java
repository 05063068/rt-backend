package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "person")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Person extends AbstractModel {
    protected String vanity;
    protected String name;
    protected Image mainImage;
    protected String bio;


    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected PersonSupplementaryInfo personSupplementaryInfo;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<MovieFilmographyItem> movieFilmography;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<MovieFilmographyItem> highestRated;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<MovieFilmographyItem> lowestRated;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<TvFilmographyItem> tvFilmography;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Image> images;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Quote> quotes;
}
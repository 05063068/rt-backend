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
    protected Iterable<TvFilmographyItem> tvFilmography;
}
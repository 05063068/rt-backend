package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

import com.rottentomatoes.movieapi.domain.model.Movie;

@JsonApiResource(type = "list")
@Getter
@Setter
public class MovieList extends AbstractModel {
    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Movie> movies;
}

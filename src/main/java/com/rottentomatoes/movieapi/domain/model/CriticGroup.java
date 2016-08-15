package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "criticGroup")
@Getter
@Setter
public class CriticGroup extends AbstractModel {

    protected String name;
    protected String url;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Critic> critics;
}
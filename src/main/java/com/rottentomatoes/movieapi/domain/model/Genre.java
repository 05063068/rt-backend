package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "genre")
@Getter
@Setter
public class Genre extends AbstractModel {
    protected String name;
    protected String tag;
}

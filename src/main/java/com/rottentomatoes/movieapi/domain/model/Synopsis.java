package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "synopsis")
@Getter
@Setter
public class Synopsis extends AbstractModel {

    protected String source;
    protected String text;
    protected String country;
}
